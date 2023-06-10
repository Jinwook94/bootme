import React, { useRef, useState } from 'react';
import CourseRichText from './CourseRichText';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import useCourse from '../../hooks/useCourse';
import { IdInputWrapper, RichTextWrapper, Wrapper } from './style';

const PartnerPage = () => {
  const quill = useRef<ReactQuill & ReactQuillProps>(null);
  const { fetchCourse, sendCourseDetail } = useCourse();
  const [courseId, setCourseId] = useState<string>('');
  const [courseTitle, setCourseTitle] = useState<string | undefined>('');
  const [inputValue, setInputValue] = useState<string>('');
  const [isFormVisible, setFormVisible] = useState<boolean>(false);

  const handleIdSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    const fetchedCourse = await fetchCourse(inputValue);
    if (fetchedCourse) {
      setCourseId(inputValue);
      setCourseTitle(fetchedCourse.title);
      setFormVisible(true);
    }
  };

  const handleUploadSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (quill.current) {
      const editor = quill.current.getEditor();
      const unprivilegedEditor = quill.current.makeUnprivilegedEditor(editor);
      const detail = unprivilegedEditor.getHTML();
      sendCourseDetail(Number(courseId), detail).catch(e => console.log(e));
    }
  };

  return (
    <>
      <Wrapper>
        {!isFormVisible ? (
          <>
            <IdInputWrapper>
              <h5 style={{ marginTop: '1.5rem' }}> 수정할 코스의 ID를 입력하세요.</h5>
              <form onSubmit={handleIdSubmit}>
                <input
                  style={{ marginTop: '0.5rem', marginRight: '0.5rem', width: '4rem' }}
                  type="text"
                  placeholder="코스 ID"
                  value={inputValue}
                  onChange={e => setInputValue(e.target.value)}
                />
                <button type="submit">입력</button>
              </form>
            </IdInputWrapper>
          </>
        ) : (
          <RichTextWrapper>
            <h2 style={{ marginBottom: '0.5rem' }}> 코스 상세 정보 업로드 </h2>
            <h5 style={{ marginBottom: '0.5rem' }}>
              수정 코스 ID: {courseId}, 타이틀: {courseTitle}
            </h5>
            <CourseRichText quill={quill} courseId={courseId} />
            <form onSubmit={handleUploadSubmit}>
              <button type="submit" style={{ marginTop: '0.5rem' }}>
                업로드
              </button>
            </form>
          </RichTextWrapper>
        )}
      </Wrapper>
    </>
  );
};

export default PartnerPage;
