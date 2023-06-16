import {
  BodyWrapper,
  Buttons,
  CancelButton,
  HeaderWrapper,
  PageLayout,
  TitleLength,
  TitleTextArea,
  TitleWrapper,
  UploadButton,
  Wrapper,
} from './style';
import React, { useEffect, useRef, useState } from 'react';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import PostWriteRichText from '../../components/RichTextEditor/PostWriteRichText';
import { usePost } from '../../hooks/usePost';
import { Button } from 'antd';
import { 자유 } from '../../constants/filters';
import TopicDropdown from './TopicDropdown';
import { useNavigate } from 'react-router-dom';

const PostWritePage = () => {
  const quill = useRef<ReactQuill & ReactQuillProps>(null);
  const navigate = useNavigate();
  const { uploadPost } = usePost();
  const [topic, setTopic] = useState(자유);
  const titleRef = useRef<HTMLTextAreaElement | null>(null);
  const [titleLength, setTitleLength] = useState(0);
  const [, setContentLength] = useState(0);

  const adjustHeightOfTitle = (element: HTMLTextAreaElement) => {
    element.style.height = 'auto';
    element.style.height = `${element.scrollHeight}px`;
  };

  const handleTitleChange = (event: React.ChangeEvent<HTMLTextAreaElement>) => {
    adjustHeightOfTitle(event.target);
    setTitleLength(event.target.value.length);
  };

  const handleUploadPost = () => {
    window.onbeforeunload = null;
    if (quill.current) {
      const editor = quill.current.getEditor();
      const unprivilegedEditor = quill.current.makeUnprivilegedEditor(editor);
      const title = titleRef.current?.value;
      const content = unprivilegedEditor.getHTML();
      uploadPost(topic, title, content).catch(e => console.log(e));
    }
  };

  useEffect(() => {
    window.onbeforeunload = () => true;
    return () => {
      window.onbeforeunload = null;
    };
  }, []);

  return (
    <Wrapper>
      <PageLayout>
        <HeaderWrapper>게시글 쓰기</HeaderWrapper>
        <BodyWrapper>
          <TopicDropdown topic={topic} setTopic={setTopic} />
          <TitleWrapper>
            <TitleTextArea
              autoFocus={true}
              ref={titleRef}
              placeholder={'제목을 입력하세요'}
              rows={1}
              maxLength={300}
              onChange={handleTitleChange}
            />
            <TitleLength>{titleLength}/300</TitleLength>
          </TitleWrapper>
          <PostWriteRichText
            quill={quill}
            onTextLengthChange={contentLength => contentLength && setContentLength(contentLength)}
          />
          <Buttons>
            <CancelButton
              onClick={() => {
                if (window.confirm('글쓰기를 취소하시겠습니까?')) {
                  navigate(-1);
                }
              }}
            >
              <Button style={{ fontSize: '14px', fontWeight: '700', lineHeight: 'normal', width: '100px' }}>
                취소
              </Button>
            </CancelButton>
            <UploadButton onClick={titleLength === 0 ? () => {} : handleUploadPost}>
              <Button
                type="primary"
                disabled={titleLength === 0}
                style={{ fontSize: '14px', fontWeight: '700', lineHeight: 'normal', width: '100px' }}
              >
                작성 완료
              </Button>
            </UploadButton>
          </Buttons>
        </BodyWrapper>
      </PageLayout>
    </Wrapper>
  );
};

export default PostWritePage;
