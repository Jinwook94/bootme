import { fetcher } from '../api/fetcher';
import { useSnackbar } from './useSnackbar';
import React, { createContext, useContext, useState } from 'react';
import SNACKBAR_MESSAGE, { CHECK, EXCLAMATION } from '../constants/snackbar';
import { Post, PostComment, PostDetail, PostListResponse } from '../types/post';
import { VOTABLE_TYPE } from '../constants/others';
import { usePostFilters } from './useFilters';
import { POST_FILTERS } from '../constants/filters';
import { useNavigation } from './useNavigation';
import PATH from '../constants/path';

const PostContext = createContext<PostContextProps>({
  size: 10,
  sortOption: '',
  setSortOption: async () => undefined,
  page: 1,
  setPage: async () => undefined,
  isEndOfPosts: false,
  setEndOfPosts: async () => undefined,
  postList: undefined,
  setPostList: async () => undefined,
  post: undefined,
  postCount: 0,
  fetchPostList: async () => undefined,
  fetchPost: async () => undefined,
  uploadPost: async () => undefined,
  editPost: async () => undefined,
  deletePost: async () => undefined,
  comments: [],
  fetchComments: async () => [],
  handleVote: async () => {},
  uploadComment: async () => {},
  onSearch: async () => {},
});

export const PostProvider = ({ children }: { children: React.ReactNode }) => {
  const { goToPage } = useNavigation();
  const { showSnackbar } = useSnackbar();
  const { selectedFilters, clearAndAddFilter } = usePostFilters();
  const [sortOption, setSortOption] = useState<string>('views');
  const [page, setPage] = useState(1);
  const [size] = useState<number>(10);
  const [postList, setPostList] = useState<Post[]>([]);
  const [post, setPost] = useState<PostDetail>();
  const [postCount, setPostCount] = useState<number>();
  const [comments, setComments] = useState<PostComment[]>([]);
  const [isEndOfPosts, setEndOfPosts] = useState(false);

  const fetchPostList = async (sort: string, page: number) => {
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
      setPostList(prevPosts => [...prevPosts, ...fetchedPosts.content]);
      setPostCount(fetchedPosts.totalElements);
      if (fetchedPosts && fetchedPosts.numberOfElements < size) {
        setEndOfPosts(true);
      }
      return response.data;
    } catch (e: any) {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_POST_FETCH + ': ' + e.response.data.message, EXCLAMATION);
    }
  };

  const fetchPost = async (id: number | undefined) => {
    try {
      const response = await fetcher.get<PostDetail>(`/posts/${id}`);
      setPost(response.data);
      return response.data;
    } catch (e: any) {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_POST_FETCH + ': ' + e.response.data.message, EXCLAMATION);
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
        goToPage(`${PATH.POST.DETAIL}/${postId}`);
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
      goToPage(`${PATH.POST.DETAIL}/${postId}`);
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
        goToPage(PATH.POST.LIST);
      }, 1500);
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

  const handleVote = async (
    votableType: string,
    votableId: number | undefined,
    voteType: string,
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

  const onSearch = async (value: string) => {
    clearAndAddFilter(POST_FILTERS.SEARCH.filterName, value);
  };

  return (
    <PostContext.Provider
      value={{
        size,
        sortOption,
        setSortOption,
        page,
        setPage,
        isEndOfPosts,
        setEndOfPosts,
        postList,
        setPostList,
        post,
        postCount,
        fetchPostList,
        fetchPost,
        uploadPost,
        editPost,
        deletePost,
        comments,
        fetchComments,
        handleVote,
        uploadComment,
        onSearch,
      }}
    >
      {children}
    </PostContext.Provider>
  );
};

export const usePost = () => useContext(PostContext);

interface PostContextProps {
  size: number;
  sortOption: string;
  setSortOption: (value: string) => void;
  page: number;
  setPage: (value: number) => void;
  isEndOfPosts: boolean;
  setEndOfPosts: (value: boolean) => void;
  postList?: Post[];
  setPostList: (postList: Post[]) => void;
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
  handleVote: (
    votableType: string,
    votableId: number | undefined,
    voteType: string,
    postId: number | undefined,
    memberId: number
  ) => Promise<void>;
  uploadComment: (postId: number | undefined, parentId: number | undefined | null, content: string) => Promise<void>;
  onSearch: (value: string) => void;
}