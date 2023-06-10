import { RefObject, useEffect, useState } from 'react';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import './style.css';
import Quill from 'quill';

import 'react-quill/dist/quill.snow.css';
import ImageResize from 'quill-image-resize';
import { useSnackbar } from '../../../hooks/useSnackbar';
import useImage from '../../../hooks/useImage';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../../constants/snackbar';

Quill.register('modules/imageResize', ImageResize);

const PostWriteRichText = ({ quill, onTextLengthChange }: PostWriteRichTextProps) => {
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
        ['link', 'image'],
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
        await uploadImage(editor, file);
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
        className="post-react-quill"
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
        placeholder={'내용을 입력하세요 (선택사항)'}
      />
    </>
  );
};

export default PostWriteRichText;

interface PostWriteRichTextProps {
  quill: RefObject<ReactQuill & ReactQuillProps>;
  onTextLengthChange: (length: number | undefined) => void;
}

const quillStyles = {
  width: '100%',
};