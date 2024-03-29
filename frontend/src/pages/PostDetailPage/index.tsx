import {
  PageLayout,
  ContentWrapper,
  ContentMainWrapper,
  TopicText,
  WriterInfo,
  NickName,
  ContentHeader,
  ContentInfo,
  Title,
  ContentBody,
  ContentTop,
  ProfilePic,
  ButtonWrapper,
  VoteWrapper,
  VoteCount,
  Wrapper,
  Writer,
  CommentNickname,
  TextEditor,
  CommentEnterButton,
  CommentTopWrapper,
  ButtonIconWrapper,
  CommentCount,
  CommentListWrapper,
  GuestCommentButton,
  GuestCommentButtonText,
  GuestCommentButtonWrapper,
  MobileOnlyButtons,
  MobileButtonWrapper,
  MobileVoteButton,
  MobileVoteCount,
  UpvoteButton,
  DownvoteButton,
  MobileBookmarkIcon,
  TopicWrapper,
  TopicLine,
  Community,
  Topic,
} from './style';
import {
  AddIcon,
  BookmarkedIcon,
  BookmarkedIcon3,
  BookmarkIcon,
  DownvoteFilledIcon,
  DownvoteIcon,
  UpvoteFilledIcon,
  UpvoteIcon,
} from '../../constants/icons';

import ReactQuill, { ReactQuillProps } from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import 'react-quill/dist/quill.core.css';
import { Link, useParams } from 'react-router-dom';
import { usePost } from '../../hooks/usePost';
import React, { useEffect, useRef, useState } from 'react';
import { getTimeSince } from '../../utils/timeUtils';
import { Button } from 'antd';
import { PostComment } from '../../types/post';
import PostCommentItem from './PostCommentItem';
import { useLogin } from '../../hooks/useLogin';
import { useSnackbar } from '../../hooks/useSnackbar';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../constants/snackbar';
import CommentRichText from '../../components/RichTextEditor/CommentRichText';
import DOMPurify from 'dompurify';
import { BOOKMARK_TYPE, VOTABLE_TYPE, VOTE_TYPE } from '../../constants/others';
import { PostShareButtonInPostDetailPageDeskTop, PostShareButtonInPostDetailPageMobile } from './PostShareDropdown';
import PATH from '../../constants/path';
import { PostThreeDotsDropdown } from './ThreeDotsDropdown';
import PostEdit from './PostEdit';
import { Popover } from '@mantine/core';
import ProfileCard from '../../components/ProfileCard';
import { useBookmarks } from '../../hooks/useBookmarks';

const PostDetailPage = () => {
  const { id } = useParams();
  const { handleBookmarkClick } = useBookmarks();
  const commentQuill = useRef<ReactQuill & ReactQuillProps>(null);
  const { showSnackbar } = useSnackbar();
  const { post, fetchPost, comments, fetchComments, handleVote, uploadComment } = usePost();
  const memberId = Number(localStorage.getItem('memberId'));
  const memberNickname = localStorage.getItem('nickname') || '';
  const { isLogin } = useLogin();
  const postContent = DOMPurify.sanitize(post?.content || '', { ADD_TAGS: ['iframe', 'video'] });
  const [votedState, setVotedState] = useState(post?.voted);
  const [, setTextLength] = useState(0);
  const [isEmptyContent, setIsEmptyContent] = useState(false);
  const [editMode, setEditMode] = useState(false);
  const [bookmarkedState, setBookmarkedState] = useState(post?.bookmarked);

  const handleEditClick = () => {
    setEditMode(true);
  };

  const handleUploadComment = (e: React.FormEvent) => {
    e.preventDefault();
    if (commentQuill.current) {
      const editor = commentQuill.current.getEditor();
      const unprivilegedEditor = commentQuill.current.makeUnprivilegedEditor(editor);
      const content = unprivilegedEditor.getHTML();
      uploadComment(post?.id, null, content)
        .then(() => editor.setText(''))
        .catch(e => console.log(e));
    }
  };

  const handleVoteAndUpdateIcon = async (
    votableType: VotableType,
    votableId: number | undefined,
    voteType: VoteType,
    postId: number | undefined,
    memberId: number
  ) => {
    if (votedState === voteType) {
      handleVote(votableType, votableId, voteType, postId, memberId)
        .then(() => setVotedState(VOTE_TYPE.NONE))
        .catch(e => console.log(e));
    } else {
      handleVote(votableType, votableId, voteType, postId, memberId)
        .then(() => setVotedState(voteType))
        .catch(e => console.log(e));
    }
  };

  useEffect(() => {
    window.scrollTo({
      top: 0,
    });
    fetchPost(Number(id))
      .then(fetchedPost => {
        setVotedState(fetchedPost?.voted);
        setBookmarkedState(fetchedPost?.bookmarked);
      })
      .catch(e => console.error(e));
    fetchComments(Number(id)).catch(e => console.error(e));
  }, []);

  useEffect(() => {
    const parsedContent = new DOMParser().parseFromString(postContent, 'text/html');
    const bodyElements = parsedContent.querySelectorAll('body > *');
    if (bodyElements.length === 1 && bodyElements[0].nodeName === 'P' && bodyElements[0].innerHTML === '<br>') {
      setIsEmptyContent(true);
    } else {
      setIsEmptyContent(false);
    }
  }, [postContent]);

  return (
    <Wrapper>
      <PageLayout>
        <ContentWrapper>
          <VoteWrapper>
            <UpvoteButton
              onClick={() => handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, post?.id, VOTE_TYPE.UPVOTE, post?.id, memberId)}
            >
              {votedState === VOTE_TYPE.UPVOTE ? <UpvoteFilledIcon /> : <UpvoteIcon />}
            </UpvoteButton>
            <VoteCount>{post?.likes}</VoteCount>
            <DownvoteButton
              onClick={() =>
                handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, post?.id, VOTE_TYPE.DOWNVOTE, post?.id, memberId)
              }
            >
              {votedState === VOTE_TYPE.DOWNVOTE ? <DownvoteFilledIcon /> : <DownvoteIcon />}
            </DownvoteButton>
          </VoteWrapper>
          <ContentMainWrapper>
            <ContentTop>
              <TopicWrapper>
                <TopicLine>
                  <div></div>
                </TopicLine>
                <TopicText>
                  <Link to={PATH.POST.LIST}>
                    <Community>커뮤니티{'ㅤ'}</Community>
                  </Link>
                  /
                  <Link to={`${PATH.POST.LIST}?topic=${post?.topic}`}>
                    <Topic>
                      {'ㅤ'}
                      {post?.topic}
                    </Topic>
                  </Link>
                </TopicText>
              </TopicWrapper>
              <div style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between' }}>
                <WriterInfo>
                  <Popover position="bottom" withArrow shadow="md">
                    <Popover.Target>
                      <ProfilePic src={post?.writerProfileImage ?? ''} alt={'profile'} />
                    </Popover.Target>
                    <Popover.Dropdown p={0}>
                      <ProfileCard memberId={post?.writerId ?? ''} />
                    </Popover.Dropdown>
                  </Popover>
                  <Writer>
                    <Popover position="bottom" withArrow shadow="md">
                      <Popover.Target>
                        <NickName>{post?.writerNickname}</NickName>
                      </Popover.Target>
                      <Popover.Dropdown p={0}>
                        <ProfileCard memberId={post?.writerId ?? ''} />
                      </Popover.Dropdown>
                    </Popover>
                    <ContentInfo>
                      {post?.createdAt && getTimeSince(post.createdAt)} · 조회수 {post?.clicks}
                    </ContentInfo>
                  </Writer>
                </WriterInfo>
                <ButtonWrapper>
                  <ButtonIconWrapper
                    onClick={() =>
                      handleBookmarkClick(Number(id), BOOKMARK_TYPE.POST, bookmarkedState || false, setBookmarkedState)
                    }
                  >
                    {bookmarkedState ? <BookmarkedIcon3 /> : <BookmarkIcon />}
                  </ButtonIconWrapper>
                  <PostShareButtonInPostDetailPageDeskTop post={post} />
                  {post?.writerId === memberId ? (
                    <PostThreeDotsDropdown isMobile={false} handleEditClick={handleEditClick} postId={post.id} />
                  ) : null}
                </ButtonWrapper>
              </div>
            </ContentTop>
            {editMode ? (
              <>
                <PostEdit
                  postId={post?.id}
                  postTopic={post?.topic}
                  postTitle={post?.title}
                  postContent={post?.content}
                  setEditMode={setEditMode}
                />
              </>
            ) : (
              <>
                <ContentHeader>
                  <Title>{post?.title}</Title>
                </ContentHeader>
                <ContentBody
                  className="ql-editor"
                  dangerouslySetInnerHTML={{ __html: postContent }}
                  isEmptyContent={isEmptyContent}
                />
              </>
            )}
            <MobileOnlyButtons>
              <MobileButtonWrapper>
                <MobileVoteButton
                  onClick={() =>
                    handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, post?.id, VOTE_TYPE.UPVOTE, post?.id, memberId)
                  }
                >
                  {votedState === VOTE_TYPE.UPVOTE ? <UpvoteFilledIcon /> : <UpvoteIcon />}
                </MobileVoteButton>
                <MobileVoteCount>{post?.likes}</MobileVoteCount>
                <MobileVoteButton
                  onClick={() =>
                    handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, post?.id, VOTE_TYPE.DOWNVOTE, post?.id, memberId)
                  }
                >
                  {votedState === VOTE_TYPE.DOWNVOTE ? <DownvoteFilledIcon /> : <DownvoteIcon />}
                </MobileVoteButton>
              </MobileButtonWrapper>
              <MobileButtonWrapper>
                <MobileBookmarkIcon
                  onClick={() =>
                    handleBookmarkClick(Number(id), BOOKMARK_TYPE.POST, bookmarkedState || false, setBookmarkedState)
                  }
                >
                  {bookmarkedState ? <BookmarkedIcon /> : <BookmarkIcon />}
                </MobileBookmarkIcon>
              </MobileButtonWrapper>
              <PostShareButtonInPostDetailPageMobile post={post} />
              {post?.writerId === memberId ? (
                <PostThreeDotsDropdown isMobile={true} handleEditClick={handleEditClick} postId={post.id} />
              ) : null}
            </MobileOnlyButtons>
            <CommentTopWrapper>
              {isLogin ? (
                <>
                  <div>
                    <CommentNickname>{memberNickname}</CommentNickname>
                    <span style={{ fontSize: '14px' }}>으로 댓글 작성</span>
                  </div>
                  <TextEditor>
                    {post && (
                      <CommentRichText
                        quill={commentQuill}
                        onTextLengthChange={length => length && setTextLength(length)}
                      />
                    )}
                    <CommentEnterButton onClick={handleUploadComment}>
                      <Button
                        type="primary"
                        style={{ fontSize: '14px', fontWeight: '700', lineHeight: 'normal', width: '100px' }}
                      >
                        댓글 작성
                      </Button>
                    </CommentEnterButton>
                  </TextEditor>
                </>
              ) : (
                <GuestCommentButton onClick={() => showSnackbar(SNACKBAR_MESSAGE.NEED_LOGIN, EXCLAMATION)}>
                  <GuestCommentButtonWrapper>
                    <span style={{ display: 'flex', marginRight: '0.5rem' }}>
                      <AddIcon />
                    </span>
                    <GuestCommentButtonText>댓글 쓰기</GuestCommentButtonText>
                  </GuestCommentButtonWrapper>
                </GuestCommentButton>
              )}
              <CommentCount>{post?.commentCount}개의 댓글</CommentCount>
              <CommentListWrapper>
                {comments.map((comment: PostComment) => (
                  <PostCommentItem
                    key={comment.id}
                    id={comment.id}
                    postId={comment.postId}
                    writerId={comment.writerId}
                    writerNickname={comment.writerNickname}
                    writerProfileImage={comment.writerProfileImage}
                    content={comment.content}
                    levelNum={comment.levelNum}
                    likes={comment.likes}
                    voted={comment.voted}
                    status={comment.status}
                    createdAt={comment.createdAt}
                  />
                ))}
              </CommentListWrapper>
            </CommentTopWrapper>
          </ContentMainWrapper>
        </ContentWrapper>
      </PageLayout>
    </Wrapper>
  );
};

export default PostDetailPage;
