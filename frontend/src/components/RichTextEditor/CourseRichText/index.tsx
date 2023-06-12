import { RefObject, useState } from 'react';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import RichTextEditor from '../index';
import { IMAGE_TYPE } from '../../../constants/others';

const CourseRichText = ({ quill }: RichTextProps) => {
  const modules = {
    toolbar: {
      container: [
        [{ header: 1 }, { header: 2 }],
        [{ size: ['small', false, 'large', 'huge'] }],
        ['bold', 'italic', 'underline', 'strike'],
        [{ list: 'ordered' }, { list: 'bullet' }],
        [{ align: [] }],
        ['blockquote', 'code-block'],
        [{ indent: '-1' }, { indent: '+1' }],
        ['link', 'image', 'video'],
        [{ color: [] }, { background: [] }],
        [{ font: [] }],
      ],
    },
    imageResize: true,
  };
  const formats = [
    //Inline
    'background',
    'bold',
    'color',
    'font',
    'code',
    'italic',
    'link',
    'size',
    'strike',
    'script',
    'underline',
    //Block
    'blockquote',
    'header',
    'indent',
    'list',
    'align',
    'direction',
    'code-block',
    // Embeds
    'formula',
    'image',
    'video',
  ];

  const [value, setValue] = useState('');
  return (
    <>
      <RichTextEditor
        quill={quill}
        imageType={IMAGE_TYPE.COURSE_DETAIL}
        modules={modules}
        formats={formats}
        placeholder={'입력하세요.'}
        value={value}
        onChange={setValue}
      />
    </>
  );
};

export default CourseRichText;

interface RichTextProps {
  quill: RefObject<ReactQuill & ReactQuillProps>;
}
