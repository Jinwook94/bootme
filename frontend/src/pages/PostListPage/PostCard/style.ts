import styled from 'styled-components';

export const Content = styled.div`
  width: 100%;
  overflow: hidden;
  cursor: pointer;
  background-color: #ffffff;
  border: 1px solid #f2f2f2;
  display: flex;
  flex-direction: row;
  border-radius: 4px;
  margin-top: 12px;

  @media (max-width: 640px) {
    width: 100vw;
    border-radius: 0;
    border: 2px solid #f2f2f2;
    margin-top: 0;
    margin-bottom: 0;
  }

  &:hover {
    border-color: #898989;
  }
`;

export const VoteWrapper = styled.div`
  width: 40px;
  border-left: 4px solid transparent;
  align-items: center;
  box-sizing: border-box;
  display: flex;
  flex-direction: column;
  padding: 8px 4px 8px 0;
  cursor: pointer;
  background-color: #f8f9fa;

  @media (max-width: 640px) {
    display: none;
  }
`;

export const VoteCount = styled.span`
  color: rgb(26, 26, 27);
  font-size: 12px;
  font-weight: 700;
  line-height: 16px;
  pointer-events: none;
  word-break: normal;
  padding: 3px 0;
`;

export const ContentWrapper = styled.div`
  cursor: pointer;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  height: 100%;
  padding: 0.5rem 1rem 0.1rem 1rem;
  width: 100%;
`;

export const WriterInfo = styled.div`
  color: #787c7e;
  font-size: 12px;
  display: flex;
  align-items: flex-start;
  width: 100%;
`;

export const ContentHeader = styled.div`
  margin-top: 8px;
  font-size: 18px;
  color: #222222ff;
  line-height: 22px;
  font-weight: 500;
  width: 100%;
`;

export const TopicChip = styled.div`
  font-size: 12px;
  font-weight: 500;
  line-height: 16px;
  display: inline-block;
  background-color: rgb(237, 239, 241);
  color: rgb(26, 26, 27);
  vertical-align: middle;
  opacity: 0.85;
  border-radius: 20px;
  padding: 2px 8px;
  margin-left: 8px;
`;

const maskImageStyle = `
  mask-image: linear-gradient(to bottom, #000 60%, transparent 100%);
`;

interface ContentBodyProps {
  shouldApplyMask: boolean;
}

export const ContentBody = styled.div<ContentBodyProps>`
  width: 100%;
  margin-top: 8px;
  font-size: 14px;
  line-height: 21px;
  font-weight: 400;
  word-break: break-word;
  padding-bottom: 1px;
  margin-bottom: -1px;
  overflow: hidden;
  ${props => (props.shouldApplyMask ? maskImageStyle : '')}

  iframe {
    width: 100%;
    height: auto;
    aspect-ratio: 16/9;
  }

  img {
    cursor: pointer;
    width: auto;
    height: auto;
    max-width: 100%;
    max-height: 500px;
  }
`;

export const ContentBottom = styled.div`
  padding-top: 0.3rem;
  display: flex;
`;

export const MobileButtons = styled.div`
  font-size: 14px;
  font-weight: 700;
  line-height: 15px;
  display: flex;
  flex-direction: row;
  gap: 25px;
  div {
    padding: 3px 3px;
  }
  @media (min-width: 640px) {
    display: none;
  }
`;

export const VoteWrapperMobile = styled.div`
  display: flex;
  flex-direction: row;
  gap: 10px;
  align-items: center;
  box-sizing: border-box;
  padding: 1px 5px;

  &:hover {
    background: rgb(234, 237, 239);
  }
`;

export const VoteButton = styled.button`
  width: 24px;
  height: 24px;
  background: transparent;
  border: none;
  color: inherit;
  cursor: pointer;
  fill: #878a8c;
  svg {
    width: 20px;
    height: 20px;
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

export const MobileVoteButton = styled.div`
  padding-top: 2px;
  display: flex;
  flex-direction: column;
  align-items: center;
  background: transparent;
  border: none;
  color: inherit;
  cursor: pointer;
  fill: #878a8c;
  svg {
    width: 21px;
    height: 21px;
  }
`;

export const MobileUpvoteButton = styled(MobileVoteButton)`
  &:hover {
    svg {
      fill: #d93a00;
    }
`;

export const MobileDownvoteButton = styled(MobileVoteButton)`
  &:hover {
    svg {
      fill: #6a5cff
    }
`;

export const VoteCountMobile = styled.div`
  color: #878a8c;
  margin: 0 1px 3px 1px;
  padding: 0 8px;
  text-align: center;
  min-width: 1rem;
  pointer-events: none;
  word-break: normal;
`;

export const CommentIconMobile = styled.div`
  display: flex;
  align-items: center;
  gap: 4px;
  cursor: pointer;
  box-sizing: border-box;
  flex-direction: row;
  padding: 1px 8px;
  svg {
    fill: #878a8c;
    width: 20px;
    height: 20px;
  }

  &:hover {
    background: rgb(234, 237, 239);
  }
`;

export const CommentCountMobile = styled(VoteCountMobile)``;

export const ShareIconMobile = styled(CommentIconMobile)``;
export const ShareIconTextMobile = styled(VoteCountMobile)``;

export const DesktopButtons = styled.div`
  font-size: 12px;
  font-weight: 700;
  display: flex;
  flex-direction: row;
  padding-right: 0.5rem;
  @media (max-width: 640px) {
    display: none;
  }
`;

export const CommentIconDesktop = styled(CommentIconMobile)`
  border-radius: 0;
  border: 0;
  gap: 6px;
  svg {
    width: 20px;
    height: 20px;
  }
`;
export const CommentCountDesktop = styled(CommentCountMobile)``;

export const ShareIconDesktop = styled(ShareIconMobile)`
  border-radius: 0;
  border: 0;
  gap: 6px;
  svg {
    width: 20px;
    height: 20px;
  }
`;
export const ShareIconTextDesktop = styled(ShareIconTextMobile)``;

export const BookmarkIconDesktop = styled.div`
  display: flex;
  align-items: center;
  gap: 4px;
  cursor: pointer;
  box-sizing: border-box;
  flex-direction: row;
  margin-right: 0.5rem;
  border-radius: 0;
  border: 0;
  padding: 1px 8px;
  svg {
    width: 20px;
    height: 20px;
  }
  &:hover {
    background: rgb(234, 237, 239);
  }
`;

export const BookmarkIconTextDesktop = styled(ShareIconTextDesktop)``;
