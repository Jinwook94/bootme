import {
  Commentary,
  CommentBody,
  CommentBottom,
  CommentContent,
  CommentList,
  CommentMiddle,
  CommentTop,
  CommentVoteCount,
  CommentVoteWrapper,
  CommentWrapper,
  ProfilePicture,
  ThreadLine1,
  ThreadLine2,
  ThreadLine3,
  ThreadLine,
  UserNickname,
  WriteTime,
  ThreadLineIncrement,
  CommentProfilePic,
  CommentUpvoteButton,
  CommentDownvoteButton,
  CommentText,
  CommentIconWrapper,
  ReplyCommentTextEditor,
  ReplyComment,
  ReplyCommentWrapper,
  ReplyCommentButtons,
  ReplyEnterButton,
  ReplyCancelButton,
  CurrentLength,
} from '../style';
import { CommentIcon, DownvoteFilledIcon, DownvoteIcon, UpvoteFilledIcon, UpvoteIcon } from '../../../constants/icons';
import { getTimeSince } from '../../../utils/timeUtils';
import { PostComment } from '../../../types/post';
import DOMPurify from 'dompurify';
import { VOTABLE_TYPE, VOTE_TYPE } from '../../../constants/others';
import { usePost } from '../../../hooks/usePost';
import React, { useEffect, useRef, useState } from 'react';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import { Button } from 'antd';
import { useLogin } from '../../../hooks/useLogin';
import { useSnackbar } from '../../../hooks/useSnackbar';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../../constants/snackbar';
import CommentReplyRichText from '../CommentReplyRichText';

const PostCommentList = ({
  id,
  postId,
  memberId,
  memberNickname,
  memberProfileImage,
  content,
  levelNum,
  likes,
  voted,
  createdAt,
}: PostCommentProps) => {
  const quill = useRef<ReactQuill & ReactQuillProps>(null);
  const timeSinceCreated = getTimeSince(createdAt);
  const commentContent = DOMPurify.sanitize(content || '');
  const { isLogin } = useLogin();
  const { showSnackbar } = useSnackbar();
  const { handleVote, uploadComment } = usePost();
  const [isReplyOpen, setIsReplyOpen] = useState(false);
  const [votedState, setVotedState] = useState(voted);
  const [textLength, setTextLength] = useState(0);

  const handleReplyToggle = () => {
    setIsReplyOpen(!isReplyOpen);
  };

  const handleUploadComment = (e: React.FormEvent) => {
    e.preventDefault();
    if (quill.current) {
      const editor = quill.current.getEditor();
      const unprivilegedEditor = quill.current.makeUnprivilegedEditor(editor);
      const content = unprivilegedEditor.getHTML();
      uploadComment(postId, id, content)
        .then(() => {
          editor.setText('');
          handleReplyToggle();
        })
        .catch(e => console.log(e));
    }
  };

  const handleVoteAndUpdateIcon = async (
    votableType: string,
    votableId: number | undefined,
    voteType: string,
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
    if (isReplyOpen && quill.current) {
      quill.current.focus();
    }
  }, [isReplyOpen]);

  return (
    <CommentList>
      <CommentWrapper padding={levelNum * 21}>
        <ThreadLine1>
          {Array.from({ length: levelNum }).map((_, index) => (
            <ThreadLineIncrement key={index}>
              <ThreadLine></ThreadLine>
            </ThreadLineIncrement>
          ))}
          <ThreadLine2>
            <ThreadLine3>
              <ThreadLine></ThreadLine>
            </ThreadLine3>
          </ThreadLine2>
        </ThreadLine1>
        <CommentContent>
          <ProfilePicture>
            <CommentProfilePic src={memberProfileImage ?? ''} alt={'profile'} />
          </ProfilePicture>
          <CommentBody>
            <CommentTop>
              <UserNickname>{memberNickname}</UserNickname>
              <span style={{ margin: '0 4px', flex: '0 0 auto', alignSelf: 'baseline' }}> · </span>
              <WriteTime>{timeSinceCreated}</WriteTime>
            </CommentTop>
            <CommentMiddle>
              <Commentary dangerouslySetInnerHTML={{ __html: commentContent }} />
            </CommentMiddle>
            <CommentBottom>
              <CommentVoteWrapper>
                <CommentUpvoteButton
                  onClick={() =>
                    handleVoteAndUpdateIcon(VOTABLE_TYPE.POST_COMMENT, id, VOTE_TYPE.UPVOTE, postId, memberId)
                  }
                >
                  {votedState === VOTE_TYPE.UPVOTE ? <UpvoteFilledIcon /> : <UpvoteIcon />}
                </CommentUpvoteButton>
                <CommentVoteCount>{likes}</CommentVoteCount>
                <CommentDownvoteButton
                  onClick={() =>
                    handleVoteAndUpdateIcon(VOTABLE_TYPE.POST_COMMENT, id, VOTE_TYPE.DOWNVOTE, postId, memberId)
                  }
                >
                  {votedState === VOTE_TYPE.DOWNVOTE ? <DownvoteFilledIcon /> : <DownvoteIcon />}
                </CommentDownvoteButton>
              </CommentVoteWrapper>
              <ReplyCommentWrapper>
                <ReplyComment
                  onClick={() => {
                    if (isLogin) {
                      handleReplyToggle();
                    } else {
                      showSnackbar(SNACKBAR_MESSAGE.NEED_LOGIN, EXCLAMATION);
                    }
                  }}
                >
                  <CommentIconWrapper>
                    <CommentIcon />
                  </CommentIconWrapper>
                  <CommentText>댓글 쓰기</CommentText>
                </ReplyComment>
              </ReplyCommentWrapper>
            </CommentBottom>
            {isReplyOpen && (
              <div style={{ display: 'flex', flexDirection: 'column' }}>
                <ReplyCommentTextEditor>
                  <CommentReplyRichText
                    quill={quill}
                    postId={postId}
                    onTextLengthChange={length => length && setTextLength(length)}
                  />
                  <div
                    style={{ display: 'flex', flexDirection: 'row', justifyContent: 'space-between', marginTop: '8px' }}
                  >
                    <CurrentLength>{textLength}/2000</CurrentLength>
                    <ReplyCommentButtons>
                      <ReplyCancelButton onClick={handleReplyToggle}>
                        <Button
                          style={{
                            fontSize: '14px',
                            fontWeight: '500',
                            lineHeight: 'normal',
                            width: '80px',
                            marginRight: '0.5rem',
                          }}
                        >
                          취소
                        </Button>
                      </ReplyCancelButton>
                      <ReplyEnterButton onClick={handleUploadComment}>
                        <Button
                          type="primary"
                          style={{ fontSize: '14px', fontWeight: '500', lineHeight: 'normal', width: '100px' }}
                        >
                          댓글 작성
                        </Button>
                      </ReplyEnterButton>
                    </ReplyCommentButtons>
                  </div>
                </ReplyCommentTextEditor>
              </div>
            )}
          </CommentBody>
        </CommentContent>
      </CommentWrapper>
    </CommentList>
  );
};

export default PostCommentList;

export type PostCommentProps = Omit<PostComment, 'parentId' | 'groupNum' | 'orderNum' | 'modifiedAt'>;
