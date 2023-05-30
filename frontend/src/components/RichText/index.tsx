import { RefObject, useEffect, useState } from 'react';
import ReactQuill, { ReactQuillProps } from 'react-quill';
import Quill from 'quill';

import 'react-quill/dist/quill.snow.css';
import ImageResize from 'quill-image-resize';
import { fetcher } from '../../api/fetcher';

Quill.register('modules/imageResize', ImageResize);

const RichText = ({ quill, courseId }: RichTextProps) => {
  const [value, setValue] = useState('');

  const modules = {
    toolbar: {
      container: [
        [{ header: 1 }, { header: 2 }, { header: 2 }],
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

  useEffect(() => {
    if (quill && quill.current) {
      const editor = quill.current.getEditor();
      editor.getModule('toolbar').addHandler('image', () => {
        selectLocalImage(editor);
      });
    }
  }, [quill]);

  const selectLocalImage = (editor: Quill) => {
    const input = document.createElement('input');
    input.setAttribute('type', 'file');
    input.setAttribute('accept', 'image/*');
    input.click();
    input.onchange = async () => {
      const file = input.files ? input.files[0] : null;
      if (file && /^image\//.test(file.type)) {
        await uploadImage(editor, file, courseId);
      } else {
        console.warn('You can only upload images.');
      }
    };
  };

  const uploadImage = async (editor: Quill, file: Blob, courseId: string) => {
    const formData = new FormData();
    formData.append('image', file);

    await fetcher
      .post(`/images?courseId=${courseId}`, formData)
      .then(r => {
        const range = editor.getSelection(true);
        editor.insertEmbed(range.index, 'image', r.data);
      })
      .catch(e => console.log(e));
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

export default RichText;

interface RichTextProps {
  quill: RefObject<ReactQuill & ReactQuillProps>;
  courseId: string;
}
