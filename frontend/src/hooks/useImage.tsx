import Quill from 'quill';
import { fetcher } from '../api/fetcher';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../constants/snackbar';
import { useSnackbar } from './useSnackbar';

const useImage = () => {
  const { showSnackbar } = useSnackbar();

  const uploadImage = async (editor: Quill, imageType: string, file: Blob) => {
    const formData = new FormData();
    formData.append('image', file);

    try {
      const response = await fetcher.post(`/images?imageType=${imageType}`, formData);
      const imageUrl = response.data;

      const range = editor.getSelection(true);
      editor.insertEmbed(range.index, 'image', imageUrl);
    } catch (error) {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_UPLOAD_IMAGE, EXCLAMATION);
    }
  };

  return { uploadImage };
};

export default useImage;
