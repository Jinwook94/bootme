import styled from 'styled-components';
import { CommentVoteButton } from '../style';

export const PostCommentWrapper = styled.div``;

interface CommentWrapperProps {
  padding?: number;
}

export const CommentWrapper = styled.div<CommentWrapperProps>`
  padding-left: ${props => props.padding || '0'}px;
  box-sizing: border-box;
  overflow: visible;
  position: relative;
  transition: background 1s;
  width: 100%;
`;

export const ThreadLine1 = styled.div`
  bottom: 0;
  left: -20px;
  position: absolute;
  top: 0;
  z-index: 2;
`;

export const ThreadLine2 = styled.div`
  box-sizing: border-box;
  display: inline-block;
  height: 100%;
  vertical-align: top;
`;

export const ThreadLine3 = styled.div`
  bottom: 0;
  position: absolute;
  height: calc(100% - 50px);
  box-sizing: border-box;
  cursor: pointer;
  display: inline-block;
  margin-left: 5px;
  vertical-align: top;
  width: 16px;
`;

export const ThreadLine = styled.i`
  border-right: 2px solid #edeff1;
  display: block;
  height: 100%;
  width: 50%;
`;

export const ThreadLineIncrement = styled.div`
  box-sizing: border-box;
  cursor: pointer;
  display: inline-block;
  height: 100%;
  margin-left: 5px;
  vertical-align: top;
  width: 16px;
`;

export const CommentContent = styled.div`
  padding-top: 1rem;
  align-items: center;
  display: flex;
  margin-left: -23px;
`;

export const ProfilePicture = styled.div`
  align-self: flex-start;
  display: inline-block;
  flex: 0 0 auto;
`;

export const CommentProfilePic = styled.img`
  width: 30px;
  height: 30px;
  border-radius: 15px;
  object-fit: cover;
  margin-right: 0.5rem;
`;

export const CommentBody = styled.div`
  border-radius: 4px;
  border: 1px solid transparent;
  box-sizing: border-box;
  max-width: 800px;
  width: calc(100% - 56px);
  padding: 0;
  align-self: flex-start;
`;

export const CommentTop = styled.div`
  margin-left: 0;
  min-height: 18px;
  margin-bottom: 6px;
  display: flex;
  flex-direction: row;
  align-items: flex-start;
`;

export const UserNickname = styled.div`
  color: #1a1a1b;
  font-size: 12px;
  line-height: 16px;
  font-weight: 500;
  align-self: center;
`;

export const WriteTime = styled.div`
  color: #7c7c7c;
  font-size: 12px;
  line-height: 18px;
  font-weight: 400;
  align-self: center;
`;

export const CommentMiddle = styled.div`
  padding: 2px 0;
  width: 100%;
`;

export const Commentary = styled.div`
  font-size: 14px;
  line-height: 21px;
  img {
    max-width: 100%;
    height: auto;
  }
`;

export const CommentBottom = styled.div`
  align-items: center;
  display: flex;
  flex-flow: row nowrap;
`;

export const CommentVoteWrapper = styled.div`
  display: flex;
  align-items: center;
  box-sizing: border-box;
  flex-direction: row;
  padding-right: 0.5rem;
`;

export const CommentUpvoteButton = styled(CommentVoteButton)`
  &:hover {
    svg {
      fill: #d93a00;
    }
`;

export const CommentDownvoteButton = styled(CommentVoteButton)`
  &:hover {
    svg {
      fill: #6a5cff
    }
`;

export const CommentVoteCount = styled.div`
  color: #1a1a1b;
  font-size: 12px;
  font-weight: 700;
  line-height: 15px;
  min-width: 12px;
  margin: 4px;
  width: auto;
  text-align: center;
  pointer-events: none;
  word-break: normal;
`;

export const ReplyCommentWrapper = styled.div`
  cursor: pointer;
  display: flex;
  flex-direction: row;
  @media (max-width: 767px) {
    padding-right: 0;
  }
`;

export const ReplyComment = styled.div`
  display: flex;
  flex-direction: row;
  border-radius: 2px;
  padding: 8px;
  text-transform: capitalize;
  white-space: nowrap;
  width: auto;
  word-break: normal;
  word-wrap: normal;
  height: 100%;
  align-items: center;
`;

export const CommentIconWrapper = styled.div`
  width: 16px;
  height: 16px;
  margin-bottom: 4px;
  svg {
    fill: #878a8c;
  }
`;

export const CommentText = styled.div`
  white-space: nowrap;
  color: #878a8c;
  font-size: 0.75rem;
  font-weight: 500;
  line-height: 1rem;
  margin-left: 5px;
  align-self: center;

  @media (max-width: 767px) {
    font-size: 12px;
  }
`;

export const ReplyCommentTextEditor = styled.div`
  margin: 16px 0 16px 22px;
  position: relative;

  &:before {
    border-left: 2px solid #edeff1;
    bottom: 0;
    content: ' ';
    left: -22px;
    position: absolute;
    top: 1px;
  }
`;

export const CurrentLength = styled.div`
  color: #878a8c;
  font-size: 12px;
  font-weight: 500;
  line-height: 16px;
`;

export const ReplyCommentButtons = styled.div`
  display: flex;
  flex-direction: row;
`;

export const ReplyCancelButton = styled.div``;
export const ReplyEnterButton = styled.div``;
