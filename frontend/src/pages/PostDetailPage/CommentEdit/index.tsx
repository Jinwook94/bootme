import React, { useRef, useState } from 'react';
import CommentEditRichText from '../../../components/RichTextEditor/CommentEditRichText';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import { usePost } from '../../../hooks/usePost';
import { Buttons, CancelButton, UploadButton } from '../PostEdit/style';
import { Button } from 'antd';

const CommentEdit = ({ commentId, content, setEditMode }: CommentEditProps) => {
  const editQuill = useRef<ReactQuill & ReactQuillProps>(null);
  const { editComment } = usePost();
  const [editedComment, setEditedComment] = useState(content || '');

  const handleQuillChange = (content: React.SetStateAction<string>) => {
    setEditedComment(content);
  };

  const handleSaveClick = () => {
    if (editQuill.current) {
      const editor = editQuill.current.getEditor();
      const unprivilegedEditor = editQuill.current.makeUnprivilegedEditor(editor);
      setEditedComment(unprivilegedEditor.getHTML());
      editComment(commentId, editedComment)
        .then(() => setEditMode(false))
        .catch(e => console.log(e));
    }
  };

  return (
    <>
      <CommentEditRichText quill={editQuill} value={editedComment} onChange={handleQuillChange} />
      <Buttons>
        <CancelButton
          onClick={() => {
            if (window.confirm('댓글 수정을 취소하시겠습니까?')) {
              setEditMode(false);
            }
          }}
        >
          <Button style={{ fontSize: '14px', fontWeight: '700', lineHeight: 'normal', width: '100px' }}>취소</Button>
        </CancelButton>
        <UploadButton onClick={handleSaveClick}>
          <Button type="primary" style={{ fontSize: '14px', fontWeight: '700', lineHeight: 'normal', width: '100px' }}>
            작성 완료
          </Button>
        </UploadButton>
      </Buttons>
    </>
  );
};

export default CommentEdit;

interface CommentEditProps {
  postId: number;
  commentId: number;
  content: string;
  setEditMode: (value: boolean) => void;
}
