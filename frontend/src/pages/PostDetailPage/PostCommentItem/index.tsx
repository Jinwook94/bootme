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
import {
  Commentary,
  CommentBody,
  CommentBottom,
  CommentContent,
  CommentDownvoteButton,
  CommentIconWrapper,
  CommentMiddle,
  CommentProfilePic,
  CommentText,
  CommentTop,
  CommentUpvoteButton,
  CommentVoteCount,
  CommentVoteWrapper,
  CommentWrapper,
  CurrentLength,
  DeletedComment,
  HiddenComment,
  PostCommentWrapper,
  ProfilePicture,
  ReplyCancelButton,
  ReplyComment,
  ReplyCommentButtons,
  ReplyCommentTextEditor,
  ReplyCommentWrapper,
  ReplyEnterButton,
  ThreadLine,
  ThreadLine1,
  ThreadLine2,
  ThreadLine3,
  ThreadLineIncrement,
  ThreeDotsWrapper,
  UserNickname,
  WriteTime,
} from './style';
import { CommentThreeDotsDropdown } from '../ThreeDotsDropdown';
import CommentEdit from '../CommentEdit';

const PostCommentItem = ({
  id,
  postId,
  writerId,
  writerNickname,
  writerProfileImage,
  content,
  levelNum,
  likes,
  voted,
  status,
  createdAt,
}: PostCommentProps) => {
  const replyQuill = useRef<ReactQuill & ReactQuillProps>(null);
  const timeSinceCreated = getTimeSince(createdAt);
  const commentContent = DOMPurify.sanitize(content || '');
  const { isLogin } = useLogin();
  const { showSnackbar } = useSnackbar();
  const { handleVote, uploadComment } = usePost();
  const [isReplyOpen, setIsReplyOpen] = useState(false);
  const [votedState, setVotedState] = useState(voted);
  const [textLength, setTextLength] = useState(0);
  const memberId = Number(localStorage.getItem('MemberId'));
  const [editMode, setEditMode] = useState(false);

  const handleEditClick = () => {
    setEditMode(true);
  };

  const handleReplyToggle = () => {
    setIsReplyOpen(!isReplyOpen);
  };

  const handleUploadComment = (e: React.FormEvent) => {
    e.preventDefault();
    if (replyQuill.current) {
      const editor = replyQuill.current.getEditor();
      const unprivilegedEditor = replyQuill.current.makeUnprivilegedEditor(editor);
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
    if (isReplyOpen && replyQuill.current) {
      replyQuill.current.focus();
    }
  }, [isReplyOpen]);

  return (
    <PostCommentWrapper>
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
            <CommentProfilePic src={writerProfileImage ?? ''} alt={'profile'} />
          </ProfilePicture>
          <CommentBody>
            <CommentTop>
              <UserNickname>{writerNickname}</UserNickname>
              <span style={{ margin: '0 4px', flex: '0 0 auto', alignSelf: 'baseline' }}> · </span>
              <WriteTime>{timeSinceCreated}</WriteTime>
            </CommentTop>
            <CommentMiddle>
              {(() => {
                switch (status) {
                  case 'DISPLAY':
                    return editMode ? (
                      <CommentEdit postId={postId} commentId={id} content={content} setEditMode={setEditMode} />
                    ) : (
                      <Commentary dangerouslySetInnerHTML={{ __html: commentContent }} />
                    );
                  case 'HIDDEN':
                    return <HiddenComment> 관리자에 의해 숨겨진 댓글입니다. </HiddenComment>;
                  case 'DELETED':
                    return <DeletedComment> 삭제된 댓글입니다. </DeletedComment>;
                  default:
                    return null;
                }
              })()}
            </CommentMiddle>
            {status === 'DISPLAY' ? (
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
                <ThreeDotsWrapper>
                  {writerId === memberId ? (
                    <CommentThreeDotsDropdown isMobile={false} handleEditClick={handleEditClick} commentId={id} />
                  ) : null}
                </ThreeDotsWrapper>
              </CommentBottom>
            ) : null}

            {isReplyOpen && (
              <div style={{ display: 'flex', flexDirection: 'column' }}>
                <ReplyCommentTextEditor>
                  <CommentReplyRichText
                    quill={replyQuill}
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
    </PostCommentWrapper>
  );
};

export default PostCommentItem;

export type PostCommentProps = Omit<PostComment, 'parentId' | 'groupNum' | 'orderNum' | 'modifiedAt'>;
