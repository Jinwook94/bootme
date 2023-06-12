import React, { RefObject } from 'react';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import './style.css';
import 'react-quill/dist/quill.snow.css';
import RichTextEditor from '../index';
import { IMAGE_TYPE } from '../../../constants/others';

const CommentEditRichText = ({ quill, value, onChange }: CommentEditRichTextProps) => {
  const modules = {
    toolbar: {
      container: [['bold', 'underline', { list: 'bullet' }, 'blockquote', 'code-block', 'link', 'image', 'video']],
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
        className="comment-edit-react-quill"
        quill={quill}
        imageType={IMAGE_TYPE.POST_COMMENT}
        modules={modules}
        formats={formats}
        placeholder={'댓글을 입력하세요.'}
        value={value || ''}
        onChange={onChange}
      />
    </>
  );
};

export default CommentEditRichText;

interface CommentEditRichTextProps {
  quill: RefObject<ReactQuill & ReactQuillProps>;
  value: string;
  onChange: (content: React.SetStateAction<string>) => void;
}
