import { fetcher } from '../api/fetcher';
import { useSnackbar } from './useSnackbar';
import React, { createContext, useContext, useState } from 'react';
import SNACKBAR_MESSAGE, { CHECK, EXCLAMATION } from '../constants/snackbar';
import { Post, PostComment, PostDetail, PostListResponse } from '../types/post';
import { SORT_OPTION, VOTABLE_TYPE } from '../constants/others';
import { usePostFilters } from './useFilters';
import { POST_FILTERS } from '../constants/filters';
import PATH from '../constants/path';
import { useNavigate } from 'react-router-dom';

const PostContext = createContext<PostContextProps>({
  sortOption: '',
  setSortOption: async () => undefined,
  page: 1,
  setPage: async () => undefined,
  isLastPosts: false,
  hasError: false,
  setLastPosts: () => undefined,
  postList: undefined,
  post: undefined,
  postCount: 0,
  fetchPostList: async () => undefined,
  fetchPost: async () => undefined,
  uploadPost: async () => undefined,
  editPost: async () => undefined,
  deletePost: async () => undefined,
  comments: [],
  fetchComments: async () => undefined,
  uploadComment: async () => undefined,
  editComment: async () => undefined,
  deleteComment: async () => undefined,
  handleVote: async () => undefined,
  onSearch: async () => undefined,
  isLoadingPost: false,
  setIsLoadingPost: () => undefined,
});

export const PostProvider = ({ children }: { children: React.ReactNode }) => {
  const navigate = useNavigate();
  const { showSnackbar } = useSnackbar();
  const { selectedFilters, clearAndAddFilter } = usePostFilters();
  const [sortOption, setSortOption] = useState<string>(SORT_OPTION.LIKES);
  const [page, setPage] = useState(1);
  const [size] = useState<number>(25);
  const [postList, setPostList] = useState<Post[]>([]);
  const [post, setPost] = useState<PostDetail>();
  const [postCount, setPostCount] = useState<number>();
  const [comments, setComments] = useState<PostComment[]>([]);
  const [, setFirstPosts] = useState(false);
  const [isLastPosts, setLastPosts] = useState(false);
  const [isLoadingPost, setIsLoadingPost] = useState(false);
  const [hasError, setHasError] = useState(false);

  const fetchPostList = async (sort: string, page: number) => {
    setIsLoadingPost(true);
    setHasError(false);
    const filterParams = Object.entries(selectedFilters).flatMap(([key, value]) => {
      if (value && value.length) {
        return value.map(option => `${key}=${encodeURIComponent(option)}`);
      }
      return [];
    });

    const filterString = filterParams.join('&');

    try {
      const response = await fetcher.get<PostListResponse>(`/posts?${filterString}`, {
        params: {
          sort: sort,
          page: page,
          size: size,
        },
      });
      const fetchedPosts = response.data;
      setPostCount(fetchedPosts.totalElements);
      if (fetchedPosts.first) {
        setPostList(fetchedPosts.content);
      } else {
        setPostList(prevPosts => [...prevPosts, ...fetchedPosts.content]);
      }
      {
        fetchedPosts.first ? setFirstPosts(true) : setFirstPosts(false);
      }
      {
        fetchedPosts.last ? setLastPosts(true) : setLastPosts(false);
      }
    } catch (e: any) {
      setHasError(true);
      showSnackbar(SNACKBAR_MESSAGE.FAIL_POST_FETCH + ': ' + e.response.data.message, EXCLAMATION);
    } finally {
      setIsLoadingPost(false);
    }
  };

  const fetchPost = async (id: number | undefined) => {
    try {
      const response = await fetcher.get<PostDetail>(`/posts/${id}`);
      setPost(response.data);
      return response.data;
    } catch (e: any) {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_POST_FETCH + ': ' + e.response.data.message, EXCLAMATION);
      setTimeout(() => {
        navigate(PATH.POST.LIST);
      }, 1500);
    }
  };

  const uploadPost = async (topic: string, title: string | undefined, content: string) => {
    try {
      const data = {
        topic: topic,
        title: title,
        content: content,
      };
      const response = await fetcher.post(`/posts`, data);
      const savedLocation = response.headers['location'];
      const postId = savedLocation?.split('/').pop();
      if (postId) {
        setTimeout(() => {
          navigate(`${PATH.POST.DETAIL}/${postId}`);
        }, 300);
      }
    } catch (e: any) {
      if (e.response.data) {
        showSnackbar(SNACKBAR_MESSAGE.FAIL_POST_UPLOAD + ': ' + e.response.data.message, EXCLAMATION);
      }
    }
  };

  const editPost = async (
    postId: number | undefined,
    editedTopic: string,
    editedTitle: string,
    editedContent: string
  ) => {
    try {
      const data = {
        topic: editedTopic,
        title: editedTitle,
        content: editedContent,
      };
      await fetcher.put(`/posts/${postId}`, data);
      navigate(`${PATH.POST.DETAIL}/${postId}`);
    } catch (e: any) {
      if (e.response.data) {
        showSnackbar(SNACKBAR_MESSAGE.FAIL_POST_EDIT + ': ' + e.response.data.message, EXCLAMATION);
      }
    }
  };

  const deletePost = async (id: number | undefined) => {
    try {
      await fetcher.delete(`/posts/${id}`);
      showSnackbar(SNACKBAR_MESSAGE.SUCCESS_POST_DELETE, CHECK);
      setTimeout(() => {
        navigate(PATH.POST.LIST);
      }, 1000);
    } catch (e: any) {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_POST_DELETE + ': ' + e.response.data.message, EXCLAMATION);
    }
  };

  const fetchComments = async (postId: number | undefined) => {
    try {
      const response = await fetcher.get(`/posts/${postId}/comments`);
      setComments(response.data);
      return response.data;
    } catch (e: any) {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_COMMENTS_FETCH + ': ' + e.response.data.message, EXCLAMATION);
    }
  };

  const uploadComment = async (
    postId: number | undefined,
    parentId: number | undefined | null,
    content: string
  ): Promise<void> => {
    try {
      await fetcher.post(`/posts/${postId}/comments`, {
        parentId,
        content,
      });
      await fetchComments(postId);
      await fetchPost(postId);
      showSnackbar(SNACKBAR_MESSAGE.SUCCESS_WRITE_COMMENT, CHECK);
    } catch (e: any) {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_UPLOAD + ': ' + e.response.data.message, EXCLAMATION);
      throw e;
    }
  };

  const editComment = async (commentId: number, editedComment: string) => {
    try {
      const data = {
        content: editedComment,
      };
      await fetcher.put(`/comments/${commentId}`, data);
      setTimeout(() => {
        window.location.reload();
      }, 100);
    } catch (e: any) {
      if (e.response.data) {
        showSnackbar(SNACKBAR_MESSAGE.FAIL_COMMENT_EDIT + ': ' + e.response.data.message, EXCLAMATION);
      }
    }
  };

  const deleteComment = async (id: number | undefined) => {
    try {
      await fetcher.delete(`/comments/${id}`);
      showSnackbar(SNACKBAR_MESSAGE.SUCCESS_COMMENT_DELETE, CHECK);
      setTimeout(() => {
        window.location.reload();
      }, 1000);
    } catch (e: any) {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_COMMENT_DELETE + ': ' + e.response.data.message, EXCLAMATION);
    }
  };

  const handleVote = async (
    votableType: VotableType,
    votableId: number | undefined,
    voteType: VoteType,
    postId: number | undefined,
    memberId: number
  ) => {
    try {
      const response = await fetcher.post(`/vote`, {
        votableType,
        votableId,
        voteType,
        memberId,
      });
      if (votableType === VOTABLE_TYPE.POST_COMMENT) {
        setComments(prevState =>
          prevState.map(comment => (comment.id === votableId ? { ...comment, likes: response.data.likes } : comment))
        );
      } else {
        setPost(response.data);
      }
    } catch (e: any) {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_VOTE + ': ' + e.response.data.message, EXCLAMATION);
      throw e;
    }
  };

  const onSearch = async (value: string) => {
    clearAndAddFilter(POST_FILTERS.SEARCH.filterName, value);
  };

  return (
    <PostContext.Provider
      value={{
        sortOption,
        setSortOption,
        page,
        setPage,
        isLastPosts,
        hasError,
        setLastPosts,
        postList,
        post,
        postCount,
        fetchPostList,
        fetchPost,
        uploadPost,
        editPost,
        deletePost,
        comments,
        fetchComments,
        uploadComment,
        editComment,
        deleteComment,
        handleVote,
        onSearch,
        isLoadingPost,
        setIsLoadingPost,
      }}
    >
      {children}
    </PostContext.Provider>
  );
};

export const usePost = () => useContext(PostContext);

interface PostContextProps {
  sortOption: string;
  setSortOption: (value: string) => void;
  page: number;
  setPage: (value: number) => void;
  isLastPosts: boolean;
  hasError: boolean;
  setLastPosts: (value: boolean) => void;
  postList?: Post[];
  post?: PostDetail;
  postCount?: number;
  fetchPostList: (sort: string, page: number) => Promise<PostListResponse | void>;
  fetchPost: (id: number | undefined) => Promise<PostDetail | void>;
  uploadPost: (topic: string, title: string | undefined, content: string) => Promise<void>;
  editPost: (
    postId: number | undefined,
    editedTopic: string,
    editedTitle: string,
    editedContent: string
  ) => Promise<void>;
  deletePost: (postId: number | undefined) => void;
  comments: PostComment[];
  fetchComments: (postId: number | undefined) => Promise<PostComment[] | void>;
  uploadComment: (postId: number | undefined, parentId: number | undefined | null, content: string) => Promise<void>;
  editComment: (commentId: number, editedComment: string) => Promise<void>;
  deleteComment: (postId: number) => void;
  handleVote: (
    votableType: VotableType,
    votableId: number | undefined,
    voteType: VoteType,
    postId: number | undefined,
    memberId: number
  ) => Promise<void>;
  onSearch: (value: string) => void;
  isLoadingPost: boolean;
  setIsLoadingPost: React.Dispatch<boolean>;
}
