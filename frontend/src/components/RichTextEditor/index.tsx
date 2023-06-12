import ReactQuill, { ReactQuillProps } from 'react-quill';
import React, { RefObject, useEffect } from 'react';
import { useSnackbar } from '../../hooks/useSnackbar';
import useImage from '../../hooks/useImage';
import Quill from 'quill';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../constants/snackbar';
import 'react-quill/dist/quill.snow.css';
import ImageResize from 'quill-image-resize';
import styled from 'styled-components';

const quillStyles = {
  width: '100%',
};

Quill.register('modules/imageResize', ImageResize);

const RichTextEditor = ({
  quill,
  value = '',
  onChange,
  placeholder,
  className,
  onTextLengthChange,
  imageType,
  modules,
  formats,
  theme = 'snow',
}: RichTextEditorProps) => {
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
        await uploadImage(editor, imageType, file);
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

  const handleChange = (val: string) => {
    if (onChange) {
      onChange(val);
    }
    if (onTextLengthChange) {
      const editor = quill.current?.getEditor();
      const textLength = editor?.getText().trim().length;
      onTextLengthChange(textLength);
    }
  };

  return (
    <Wrapper>
      <ReactQuill
        className={className}
        style={quillStyles}
        ref={quill}
        theme={theme}
        value={value}
        onChange={handleChange}
        modules={modules}
        formats={formats}
        placeholder={placeholder}
      />
    </Wrapper>
  );
};

export default RichTextEditor;

interface RichTextEditorProps {
  quill: RefObject<ReactQuill & ReactQuillProps>;
  imageType: string;
  modules: any;
  formats: string[];
  placeholder: string;
  value?: string;
  onChange?: (content: React.SetStateAction<string>) => void;
  className?: string;
  onTextLengthChange?: (length: number | undefined) => void;
  theme?: string;
}

const Wrapper = styled.div`
  iframe {
    width: 100%;
    height: auto;
    aspect-ratio: 16/9;
  }
`;
