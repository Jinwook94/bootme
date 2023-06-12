import { RefObject, useEffect, useState } from 'react';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import './style.css';
import Quill from 'quill';

import 'react-quill/dist/quill.snow.css';
import ImageResize from 'quill-image-resize';
import { useSnackbar } from '../../../hooks/useSnackbar';
import useImage from '../../../hooks/useImage';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../../constants/snackbar';
import { IMAGE_TYPE } from '../../../constants/others';

Quill.register('modules/imageResize', ImageResize);

const CommentReplyRichText = ({ quill, postId, onTextLengthChange }: CommentRichTextProps) => {
  const modules = {
    toolbar: {
      container: [
        ['bold', 'underline', { list: 'ordered' }, { list: 'bullet' }, 'blockquote', 'code-block', 'link', 'image'],
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
  const { showSnackbar } = useSnackbar();
  const { uploadImage } = useImage();

  const selectLocalImage = (editor: Quill) => {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('accept', 'image/*');
    input.click();
    input.onchange = async () => {
      const file = input.files ? input.files[0] : null;
      if (file && /^image\//.test(file.type)) {
        await uploadImage(editor, file, IMAGE_TYPE.POST_COMMENT, postId);
      } else {
        showSnackbar(SNACKBAR_MESSAGE.FAIL_UPLOAD_IMAGE, EXCLAMATION);
      }
    };
  };

  useEffect(() => {
    if (quill && quill.current) {
      const editor = quill.current.getEditor();
      editor.getModule('toolbar').addHandler('image', () => {
        selectLocalImage(editor);
      });
    }
  }, [quill]);

  return (
    <>
      <ReactQuill
        className="comment-reply-react-quill"
        style={quillStyles}
        ref={quill}
        theme="snow"
        value={value}
        onChange={val => {
          setValue(val);
          const editor = quill.current?.getEditor();
          const textLength = editor?.getText().trim().length;
          onTextLengthChange(textLength);
        }}
        modules={modules}
        formats={formats}
        placeholder={'입력하세요.'}
      />
    </>
  );
};

export default CommentReplyRichText;

interface CommentRichTextProps {
  quill: RefObject<ReactQuill & ReactQuillProps>;
  postId: number | undefined;
  onTextLengthChange: (length: number | undefined) => void;
}

const quillStyles = {
  width: '100%',
};
