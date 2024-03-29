import { RefObject, useState } from 'react';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import './style.css';
import 'react-quill/dist/quill.snow.css';
import RichTextEditor from '../index';
import { IMAGE_TYPE } from '../../../constants/others';

const CommentRichText = ({ quill, onTextLengthChange }: CommentRichTextProps) => {
  const modules = {
    toolbar: {
      container: [
        [
          'bold',
          'italic',
          'underline',
          { list: 'ordered' },
          { list: 'bullet' },
          'blockquote',
          'code-block',
          'link',
          'image',
          'video',
        ],
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
        imageType={IMAGE_TYPE.POST_COMMENT}
        modules={modules}
        formats={formats}
        placeholder={'댓글을 입력하세요.'}
        value={value}
        onChange={val => {
          setValue(val);
          const editor = quill.current?.getEditor();
          const textLength = editor?.getText().trim().length;
          onTextLengthChange(textLength);
        }}
      />
    </>
  );
};

export default CommentRichText;

interface CommentRichTextProps {
  quill: RefObject<ReactQuill & ReactQuillProps>;
  onTextLengthChange: (length: number | undefined) => void;
}
