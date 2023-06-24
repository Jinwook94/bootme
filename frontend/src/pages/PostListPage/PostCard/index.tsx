import {
  BookmarkedIcon,
  BookmarkIcon,
  CommentIcon,
  DownvoteFilledIcon,
  DownvoteIcon,
  UpvoteFilledIcon,
  UpvoteIcon,
} from '../../../constants/icons';
import React, { useState } from 'react';
import {
  Content,
  TopicChip,
  ContentBody,
  ContentBottom,
  DesktopButtons,
  ContentHeader,
  ContentWrapper,
  VoteCount,
  VoteCountMobile,
  VoteWrapper,
  VoteWrapperMobile,
  WriterInfo,
  UpvoteButton,
  DownvoteButton,
  MobileUpvoteButton,
  MobileDownvoteButton,
  MobileButtons,
  CommentIconMobile,
  CommentCountMobile,
  CommentIconDesktop,
  CommentCountDesktop,
  BookmarkIconDesktop,
  BookmarkIconTextDesktop,
} from './style';
import 'react-quill/dist/quill.snow.css';
import 'react-quill/dist/quill.core.css';
import { Post } from '../../../types/post';
import { getTimeSince } from '../../../utils/timeUtils';
import { usePost, VotableType, VoteType } from '../../../hooks/usePost';
import { BOOKMARK_TYPE, VOTABLE_TYPE, VOTE_TYPE } from '../../../constants/others';
import {
  PostShareButtonInPostCardDesktop,
  PostShareButtonInPostCardMobile,
} from '../../PostDetailPage/PostShareDropdown';
import PATH from '../../../constants/path';
import DOMPurify from 'dompurify';
import { Link } from 'react-router-dom';
import { useBookmarks } from '../../../hooks/useBookmarks';
import { Flex } from '@mantine/core';
import { BookmarkDropdown } from '../BookmarkDropdown';

const PostCard = ({
  id,
  writerId,
  writerNickname,
  writerProfileImage,
  topic,
  title,
  contentExcerpt,
  likes,
  createdAt,
  commentCount,
  voted,
  bookmarked,
}: PostCardProps) => {
  const { handleBookmarkClick } = useBookmarks();
  const { handleVote } = usePost();
  const [votedState, setVotedState] = useState(voted);
  const [likesState, setLikesState] = useState(likes);
  const [bookmarkedState, setBookmarkedState] = useState(bookmarked);

  const shouldApplyMask =
    !/(<img\s[^>]*?src\s*=\s*['\"]([^'\"]*?)['\"][^>]*?>)|(<iframe\s[^>]*?src\s*=\s*['\"]([^'\"]*?)['\"][^>]*?>)|(<video\s[^>]*?src\s*=\s*['\"]([^'\"]*?)['\"][^>]*?>)/i.test(
      contentExcerpt
    );
  const postContent = DOMPurify.sanitize(contentExcerpt, { ADD_TAGS: ['iframe', 'video'] });

  const handleVoteAndUpdateIcon = async (
    votableType: VotableType,
    votableId: number | undefined,
    voteType: VoteType,
    postId: number | undefined,
    memberId: number
  ) => {
    if (votedState === voteType) {
      handleVote(votableType, votableId, voteType, postId, memberId)
        .then(() => {
          setVotedState(VOTE_TYPE.NONE);
          voteType === VOTE_TYPE.UPVOTE ? setLikesState(likesState - 1) : setLikesState(likesState + 1);
        })
        .catch(e => console.log(e));
    } else {
      handleVote(votableType, votableId, voteType, postId, memberId)
        .then(() => {
          setVotedState(voteType);
          if (votedState !== VOTE_TYPE.NONE) {
            voteType === VOTE_TYPE.UPVOTE ? setLikesState(likesState + 2) : setLikesState(likesState - 2);
          } else {
            voteType === VOTE_TYPE.UPVOTE ? setLikesState(likesState + 1) : setLikesState(likesState - 1);
          }
        })
        .catch(e => console.log(e));
    }
  };

  return (
    <Content>
      <VoteWrapper>
        <UpvoteButton onClick={() => handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, id, VOTE_TYPE.UPVOTE, id, writerId)}>
          {votedState === VOTE_TYPE.UPVOTE ? <UpvoteFilledIcon /> : <UpvoteIcon />}
        </UpvoteButton>
        <VoteCount>{likesState}</VoteCount>
        <DownvoteButton
          onClick={() => handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, id, VOTE_TYPE.DOWNVOTE, id, writerId)}
        >
          {votedState === VOTE_TYPE.DOWNVOTE ? <DownvoteFilledIcon /> : <DownvoteIcon />}
        </DownvoteButton>
      </VoteWrapper>
      <Link to={`${PATH.POST.DETAIL}/${id}`} style={{ width: '100%' }}>
        <ContentWrapper>
          <div>
            <Flex direction={'row'} justify={'space-between'}>
              <WriterInfo>
                <img
                  width="18"
                  height="18"
                  src={writerProfileImage}
                  alt="profile"
                  style={{ borderRadius: '18px', objectFit: 'cover', marginRight: '8px' }}
                />
                <span>
                  {writerNickname} · {createdAt && getTimeSince(createdAt)}
                </span>
              </WriterInfo>
              <BookmarkDropdown postId={id} bookmarkedState={bookmarkedState} setBookmarkedState={setBookmarkedState} />
            </Flex>
            <ContentHeader>
              {title}
              <TopicChip>{topic}</TopicChip>
            </ContentHeader>
            <ContentBody dangerouslySetInnerHTML={{ __html: postContent }} shouldApplyMask={shouldApplyMask} />
          </div>
          <ContentBottom>
            <MobileButtons>
              <VoteWrapperMobile
                onClick={event => {
                  event.stopPropagation();
                  event.preventDefault();
                }}
              >
                <MobileUpvoteButton
                  onClick={() => handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, id, VOTE_TYPE.UPVOTE, id, writerId)}
                >
                  {votedState === VOTE_TYPE.UPVOTE ? <UpvoteFilledIcon /> : <UpvoteIcon />}
                </MobileUpvoteButton>
                <VoteCountMobile>{likesState}</VoteCountMobile>
                <MobileDownvoteButton
                  onClick={() => handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, id, VOTE_TYPE.DOWNVOTE, id, writerId)}
                >
                  {votedState === VOTE_TYPE.DOWNVOTE ? <DownvoteFilledIcon /> : <DownvoteIcon />}
                </MobileDownvoteButton>
              </VoteWrapperMobile>
              <CommentIconMobile>
                <div style={{ marginTop: '2px' }}>
                  <CommentIcon />
                </div>
                <CommentCountMobile>{commentCount}</CommentCountMobile>
              </CommentIconMobile>
              <PostShareButtonInPostCardMobile postId={id} postTitle={title} postContent={postContent} />
            </MobileButtons>
            <DesktopButtons>
              <CommentIconDesktop>
                <div>
                  <CommentIcon />
                </div>
                <CommentCountDesktop>{commentCount}개 댓글</CommentCountDesktop>
              </CommentIconDesktop>
              <PostShareButtonInPostCardDesktop postId={id} postTitle={title} postContent={postContent} />
              <BookmarkIconDesktop
                onClick={event => {
                  event.stopPropagation();
                  event.preventDefault();
                  handleBookmarkClick(id, BOOKMARK_TYPE.POST, bookmarkedState, setBookmarkedState);
                }}
              >
                {bookmarkedState ? <BookmarkedIcon /> : <BookmarkIcon />}
                <BookmarkIconTextDesktop>북마크</BookmarkIconTextDesktop>
              </BookmarkIconDesktop>
            </DesktopButtons>
          </ContentBottom>
        </ContentWrapper>
      </Link>
    </Content>
  );
};

export default PostCard;

type PostCardProps = Omit<Post, 'clicks' | 'bookmarks' | 'status' | 'modifiedAt'>;
