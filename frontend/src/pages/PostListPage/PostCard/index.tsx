import {
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
import { usePost } from '../../../hooks/usePost';
import { VOTABLE_TYPE, VOTE_TYPE } from '../../../constants/others';
import {
  PostShareButtonInPostCardDesktop,
  PostShareButtonInPostCardMobile,
} from '../../PostDetailPage/PostShareDropdown';
import { useNavigation } from '../../../hooks/useNavigation';
import PATH from '../../../constants/path';
import DOMPurify from 'dompurify';

const PostCard = ({
  id,
  memberId,
  memberNickname,
  memberProfileImage,
  topic,
  title,
  contentExcerpt,
  likes,
  createdAt,
  commentCount,
  voted,
}: PostCardProps) => {
  const { goToPage } = useNavigation();
  const { handleVote } = usePost();
  const [votedState, setVotedState] = useState(voted);
  const [likesState, setLikesState] = useState(likes);
  const shouldApplyMask = !/<img\s[^>]*?src\s*=\s*['\"]([^'\"]*?)['\"][^>]*?>/i.test(contentExcerpt);
  const postContent = DOMPurify.sanitize(contentExcerpt);

  const handleVoteAndUpdateIcon = async (
    votableType: string,
    votableId: number | undefined,
    voteType: string,
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
        <UpvoteButton onClick={() => handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, id, VOTE_TYPE.UPVOTE, id, memberId)}>
          {votedState === VOTE_TYPE.UPVOTE ? <UpvoteFilledIcon /> : <UpvoteIcon />}
        </UpvoteButton>
        <VoteCount>{likesState}</VoteCount>
        <DownvoteButton
          onClick={() => handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, id, VOTE_TYPE.DOWNVOTE, id, memberId)}
        >
          {votedState === VOTE_TYPE.DOWNVOTE ? <DownvoteFilledIcon /> : <DownvoteIcon />}
        </DownvoteButton>
      </VoteWrapper>
      <ContentWrapper onClick={() => goToPage(`${PATH.POST.DETAIL}/${id}`)}>
        <div>
          <WriterInfo>
            <img
              width="18"
              height="18"
              src={memberProfileImage}
              alt="profile"
              style={{ borderRadius: '18px', objectFit: 'cover', marginRight: '8px' }}
            />
            <span>
              {memberNickname} · {createdAt && getTimeSince(createdAt)}
            </span>
          </WriterInfo>
          <ContentHeader>
            {title}
            <TopicChip>{topic}</TopicChip>
          </ContentHeader>
          <ContentBody dangerouslySetInnerHTML={{ __html: postContent }} shouldApplyMask={shouldApplyMask} />
        </div>
        <ContentBottom>
          <MobileButtons>
            <VoteWrapperMobile onClick={event => event.stopPropagation()}>
              <MobileUpvoteButton
                onClick={() => handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, id, VOTE_TYPE.UPVOTE, id, memberId)}
              >
                {votedState === VOTE_TYPE.UPVOTE ? <UpvoteFilledIcon /> : <UpvoteIcon />}
              </MobileUpvoteButton>
              <VoteCountMobile>{likesState}</VoteCountMobile>
              <MobileDownvoteButton
                onClick={() => handleVoteAndUpdateIcon(VOTABLE_TYPE.POST, id, VOTE_TYPE.DOWNVOTE, id, memberId)}
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
            <PostShareButtonInPostCardMobile postId={id} postTitle={title} />
          </MobileButtons>
          <DesktopButtons>
            <CommentIconDesktop>
              <div style={{ marginTop: '4px' }}>
                <CommentIcon />
              </div>
              <CommentCountDesktop>{commentCount}개 댓글</CommentCountDesktop>
            </CommentIconDesktop>
            <PostShareButtonInPostCardDesktop postId={id} postTitle={title} />
            <BookmarkIconDesktop onClick={event => event.stopPropagation()}>
              <BookmarkIcon />
              <BookmarkIconTextDesktop>북마크</BookmarkIconTextDesktop>
            </BookmarkIconDesktop>
          </DesktopButtons>
        </ContentBottom>
      </ContentWrapper>
    </Content>
  );
};

export default PostCard;

type PostCardProps = Omit<Post, 'views' | 'bookmarks' | 'status' | 'modifiedAt'>;
