import { RefObject, useEffect, useState } from 'react';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import Quill from 'quill';

import 'react-quill/dist/quill.snow.css';
import ImageResize from 'quill-image-resize';
import { useSnackbar } from '../../../hooks/useSnackbar';
import useImage from '../../../hooks/useImage';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../../constants/snackbar';
import { IMAGE_TYPE } from '../../../constants/others';

Quill.register('modules/imageResize', ImageResize);

const CourseRichText = ({ quill, courseId }: RichTextProps) => {
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
        await uploadImage(editor, file, IMAGE_TYPE.COURSE_DETAIL, Number(courseId));
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
        ref={quill}
        theme="snow"
        value={value}
        onChange={setValue}
        modules={modules}
        formats={formats}
        placeholder={'입력하세요.'}
      />
    </>
  );
};

export default CourseRichText;

interface RichTextProps {
  quill: RefObject<ReactQuill & ReactQuillProps>;
  courseId: string;
}
