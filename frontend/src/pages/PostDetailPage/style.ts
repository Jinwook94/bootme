import styled from 'styled-components';

export const Wrapper = styled.div`
  padding-top: 1rem;
  padding-bottom: 2rem;
  background: #f9fafb;
`;

export const PageLayout = styled.div`
  max-width: 100%;
  padding-right: 16px;
  padding-left: 16px;
  margin-right: auto;
  margin-left: auto;
  width: 100%;

  @media (min-width: 576px) {
    max-width: 540px;
  }

  @media (min-width: 768px) {
    max-width: 720px;
  }

  @media (min-width: 1200px) {
    max-width: 860px !important;
  }
`;

export const ContentWrapper = styled.div`
  padding: 1rem;
  width: 100%;
  overflow: hidden;
  background-color: #ffffff;
  border-radius: 0;
  border: 1px solid #f2f2f2;
  display: flex;
  flex-direction: row;
`;

export const VoteWrapper = styled.div`
  margin-top: 5.5rem;
  width: 40px;
  border-left: 4px solid transparent;
  align-items: center;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  padding: 8px 4px 8px 0;
  background: #ffffff;

  @media (max-width: 640px) {
    display: none;
  }
`;

export const VoteButton = styled.div`
  height: 26px;
  width: 26px;
  padding: 2px;
  background: transparent;
  border: none;
  color: inherit;
  cursor: pointer;
  fill: #878a8c;

  &:hover {
    border-radius: 2px;
    outline: none;
    background-color: rgba(26, 26, 27, 0.1);
  }
`;

export const UpvoteButton = styled(VoteButton)`
  &:hover {
    svg {
      fill: #d93a00;
    }
`;

export const DownvoteButton = styled(VoteButton)`
  &:hover {
    svg {
      fill: #6a5cff
    }
`;

export const VoteCount = styled.div`
  color: rgb(26, 26, 27);
  font-size: 12px;
  font-weight: 700;
  line-height: 16px;
  pointer-events: none;
  word-break: normal;
  padding: 3px 0;
`;

export const ContentMainWrapper = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  flex-grow: 1;
  height: 100%;
  padding: 0.5rem;
  min-width: 0;
  margin-bottom: 0.5rem;
`;

export const ContentHeader = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;

export const Title = styled.div`
  color: #1a1a1b;
  font-size: 20px;
  line-height: 24px;
  font-weight: 500;
  margin: 8px 0;
`;

export const ContentBody = styled.div<{ isEmptyContent: boolean }>`
  font-size: 16px;
  line-height: 24px;
  font-weight: 400;
  word-break: break-word;
  padding-top: ${props => (props.isEmptyContent ? '0' : '1.5rem')};
  padding-bottom: ${props => (props.isEmptyContent ? '0' : '2rem')};
  margin-bottom: ${props => (props.isEmptyContent ? '0' : '1.5rem')};
  min-height: ${props => (props.isEmptyContent ? '0' : '150px')};
  border-bottom: ${props => (props.isEmptyContent ? 'none' : '1px solid rgba(106, 120, 127, 0.3)')};

  img {
    cursor: auto;
    max-width: 100%;
    height: auto;
  }

  iframe {
    width: 100%;
    height: auto;
    aspect-ratio: 16/9;
  }

  @media (max-width: 640px) {
    font-size: 14px;
    line-height: 21px;
    border-bottom: 0;
    margin-bottom: 0;
  }
`;

export const MobileOnlyButtons = styled.div`
  display: none;
  @media (max-width: 640px) {
    display: flex;
    flex-direction: row;
    padding-bottom: 1rem;
    border-bottom: 1px solid rgba(106, 120, 127, 0.3);
    margin-bottom: 1rem;
  }
`;

export const MobileButtonWrapper = styled.div`
  cursor: pointer;
  display: flex;
  flex-direction: row;
  padding: 8px;
  align-items: center;
  background: #eaedef;
  border-radius: 999px;
  border: 0 solid transparent;
  margin-right: 8px;
  &:hover {
    background: #e2e7e9;
  }
`;

export const MobileVoteButton = styled.div`
  height: 16px;
  width: 16px;
  display: flex;
  align-items: center;
  background: transparent;
  border: none;
  color: inherit;
  cursor: pointer;
`;

export const MobileVoteCount = styled.div`
  color: #0f1a1c;
  font-size: 0.75rem;
  font-weight: 600;
  line-height: 1rem;
  pointer-events: none;
  word-break: normal;
  padding: 0 8px;
`;

export const MobileIconWrapper = styled.div`
  padding: 0;
  width: 2rem;
  height: 1.25rem;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  svg {
    width: 1.2rem;
    height: 1.2rem;
    vertical-align: top;
    overflow: hidden;
  }
`;

export const MobileBookmarkIcon = styled(MobileIconWrapper)`
  svg {
    stroke: #333333;
  }
`;

export const MobileShareIcon = styled(MobileIconWrapper)`
  svg {
    fill: #333333;
  }
`;

export const CommentTopWrapper = styled.div`
  display: flex;
  flex-direction: column;
`;

export const ContentTop = styled.div`
  display: flex;
  flex-direction: column;
  padding-bottom: 1.5rem;
`;

export const TopicWrapper = styled.div`
  position: relative;
  height: 100%;
  margin-bottom: 1rem;
`;

export const TopicLine = styled.div`
  z-index: 1;
  position: absolute;
  top: 50%;
  left: 0;
  right: 0;

  div {
    border: 0 solid #e5e7eb;
    border-top-width: 1px;
    width: 100%;
    box-sizing: border-box;
    display: block;
  }
`;

export const TopicText = styled.div`
  position: relative;
  z-index: 2;
  color: rgb(156 163 175);
  display: inline-flex;
  flex-direction: row;
  margin-left: 24px;
  padding-left: 0.5rem;
  padding-right: 0.5rem;
  background-color: rgb(255 255 255);
  font-size: 12px;
`;

export const Community = styled.div`
  cursor: pointer;
  color: rgb(156 163 175);
`;

export const Topic = styled.div`
  cursor: pointer;
  color: rgb(0 144 249);
`;

export const WriterInfo = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
`;
export const ProfilePic = styled.img`
  width: 40px;
  height: 40px;
  border-radius: 20px;
  object-fit: cover;
  margin-right: 0.5rem;

  @media (max-width: 640px) {
    width: 25px;
    height: 25px;
    border-radius: 12.5px;
  }
`;
export const Writer = styled.div`
  display: flex;
  flex-direction: column;
`;
export const NickName = styled.div`
  color: #2b2a2a;
  font-size: 14px;
  line-height: 21px;
  font-weight: 500;

  @media (max-width: 640px) {
    font-size: 12px;
    font-weight: 500;
  }
`;

export const ContentInfo = styled.div`
  color: #818181;
  font-size: 12px;
  font-weight: 400;

  @media (max-width: 640px) {
    font-size: 12px;
    font-weight: 500;
  }
`;

export const ButtonWrapper = styled.div`
  display: flex;
  flex-direction: row;

  @media (max-width: 640px) {
    display: none;
  }
  @media (max-width: 767px) {
    padding-right: 0;
  }
`;

export const ButtonIconWrapper = styled.div`
  padding: 0;
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 0.25rem;
  line-height: 1.5rem;
  font-size: 1rem;
  font-weight: 500;
  color: #263747;
  fill: #263747;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  cursor: pointer;
  svg {
    width: 1.2rem;
    height: 1.2rem;
    vertical-align: top;
    overflow: hidden;
  }
  &:hover {
    background: #e2e7e9;
  }
`;

export const GuestCommentButton = styled.button`
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0 13px;
  height: 40px;
  margin-bottom: 1.5rem;
  border-radius: 999px;
  border: 1px solid #9a9a9a;
  box-sizing: border-box;
  background-color: transparent;

  @media (min-width: 640px) {
    width: fit-content;
  }

  &:hover {
    border-color: rgb(42, 60, 66);
  }
`;

export const GuestCommentButtonWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
`;

export const GuestCommentButtonText = styled.span`
  color: rgb(42, 60, 66);
  font-size: 14px;
  line-height: 38px;
  font-weight: 500;
`;

export const CommentNickname = styled.span`
  font-size: 14px;
  line-height: 18px;
  font-weight: 400;
  color: rgb(0, 132, 255);
`;

export const CommentListWrapper = styled.div`
  padding-left: 1rem;
`;

export const TextEditor = styled.div`
  display: flex;
  flex-direction: column;
  margin-top: 0.5rem;
  margin-bottom: 1rem;
`;

export const CommentCount = styled.span`
  font-size: 16px;
  margin-bottom: 1rem;
`;

export const CommentEnterButton = styled.div`
  padding-top: 0.5rem;
  align-self: flex-end;
`;

export const CommentVoteButton = styled.div`
  height: 16px;
  width: 16px;
  background: transparent;
  border: none;
  color: inherit;
  cursor: pointer;
  fill: #878a8c;
  &:hover {
    border-radius: 2px;
    outline: none;
    background-color: rgba(26, 26, 27, 0.1);
  }
`;
