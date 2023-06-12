import PostEditRichText from '../../../components/RichTextEditor/PostEditRichText';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import 'react-quill/dist/quill.snow.css';
import 'react-quill/dist/quill.core.css';
import { Button } from 'antd';
import React, { useRef, useState } from 'react';
import { Buttons, CancelButton, TitleLength, TitleTextArea, TitleWrapper, UploadButton, Wrapper } from './style';
import TopicDropdown from '../../PostWritePage/TopicDropdown';
import { usePost } from '../../../hooks/usePost';

const PostEdit = ({ postId, postTopic, postTitle, postContent, setEditMode }: PostEditProps) => {
  const { editPost } = usePost();
  const quill = useRef<ReactQuill & ReactQuillProps>(null);
  const titleRef = useRef<HTMLTextAreaElement | null>(null);
  const [titleLength, setTitleLength] = useState(postTitle ? postTitle.length : 0);
  const [editedTopic, setEditedTopic] = useState(postTopic || '');
  const [editedContent, setEditedContent] = useState(postContent || '');

  const adjustHeightOfTitle = (element: HTMLTextAreaElement) => {
    element.style.height = 'auto';
    element.style.height = `${element.scrollHeight}px`;
  };

  const handleTitleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    adjustHeightOfTitle(event.target);
    setTitleLength(event.target.value.length);
  };

  const handleQuillChange = (content: React.SetStateAction<string>) => {
    setEditedContent(content);
  };

  const handleSaveClick = () => {
    if (quill.current) {
      const editedTitle = titleRef.current?.value || '';
      const editor = quill.current.getEditor();
      const unprivilegedEditor = quill.current.makeUnprivilegedEditor(editor);
      setEditedContent(unprivilegedEditor.getHTML());
      editPost(postId, editedTopic, editedTitle, editedContent)
        .then(() => setEditMode(false))
        .catch(e => console.log(e));
    }
  };

  return (
    <Wrapper>
      <TopicDropdown topic={editedTopic} setTopic={setEditedTopic} />
      <TitleWrapper>
        <TitleTextArea
          ref={titleRef}
          placeholder={'제목을 입력하세요'}
          rows={1}
          maxLength={300}
          defaultValue={postTitle}
          onChange={handleTitleChange}
        />
        <TitleLength>{titleLength}/300</TitleLength>
      </TitleWrapper>
      <PostEditRichText quill={quill} value={editedContent} onChange={handleQuillChange} />
      <Buttons>
        <CancelButton
          onClick={() => {
            if (window.confirm('게시글 수정을 취소하시겠습니까?')) {
              setEditMode(false);
            }
          }}
        >
          <Button style={{ fontSize: '14px', fontWeight: '700', lineHeight: 'normal', width: '100px' }}>취소</Button>
        </CancelButton>
        <UploadButton onClick={titleLength === 0 ? () => {} : handleSaveClick}>
          <Button
            type="primary"
            disabled={titleLength === 0}
            style={{ fontSize: '14px', fontWeight: '700', lineHeight: 'normal', width: '100px' }}
          >
            작성 완료
          </Button>
        </UploadButton>
      </Buttons>
    </Wrapper>
  );
};

export default PostEdit;

interface PostEditProps {
  postId: number | undefined;
  postTopic: string | undefined;
  postTitle: string | undefined;
  postContent: string | undefined;
  setEditMode: (value: boolean) => void;
}
