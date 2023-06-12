import { RefObject, useEffect } from 'react';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import './style.css';
import Quill from 'quill';

import 'react-quill/dist/quill.snow.css';
import ImageResize from 'quill-image-resize';
import useImage from '../../../hooks/useImage';
import { useSnackbar } from '../../../hooks/useSnackbar';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../../constants/snackbar';
import { IMAGE_TYPE } from '../../../constants/others';

Quill.register('modules/imageResize', ImageResize);

const CommentEditRichText = ({ quill, value, onChange, postId }: CommentEditRichTextProps) => {
  const modules = {
    toolbar: {
      container: [
        ['bold', 'italic', 'underline'],
        [{ list: 'ordered' }, { list: 'bullet' }],
        ['blockquote', 'code-block'],
        ['link', 'image'],
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
        className="comment-edit-react-quill"
        style={quillStyles}
        ref={quill}
        theme="snow"
        value={value || ''}
        onChange={onChange}
        modules={modules}
        formats={formats}
        placeholder={'내용을 입력하세요 (선택사항)'}
      />
    </>
  );
};

export default CommentEditRichText;

interface CommentEditRichTextProps {
  quill: RefObject<ReactQuill & ReactQuillProps>;
  value: string;
  onChange: (content: React.SetStateAction<string>) => void;
  postId: number;
}

const quillStyles = {
  width: '100%',
};
