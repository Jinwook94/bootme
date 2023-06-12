import { RefObject } from 'react';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import './style.css';
import 'react-quill/dist/quill.snow.css';
import RichTextEditor from '../index';
import { IMAGE_TYPE } from '../../../constants/others';

const PostEditRichText = ({ quill, value, onChange }: PostEditRichTextProps) => {
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

  return (
    <>
      <RichTextEditor
        className="post-react-quill"
        quill={quill}
        imageType={IMAGE_TYPE.POST}
        modules={modules}
        formats={formats}
        placeholder={'내용을 입력하세요 (선택사항)'}
        value={value}
        onChange={onChange}
      />
    </>
  );
};

export default PostEditRichText;

interface PostEditRichTextProps {
  quill: RefObject<ReactQuill & ReactQuillProps>;
  value: string;
  onChange: (content: React.SetStateAction<string>) => void;
}
