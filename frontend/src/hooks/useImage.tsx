import Quill from 'quill';
import { fetcher } from '../api/fetcher';
import { IMAGE_TYPE } from '../constants/others';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../constants/snackbar';
import { useSnackbar } from './useSnackbar';

const useImage = () => {
  const { showSnackbar } = useSnackbar();

  const uploadImage = async (editor: Quill, file: Blob, itemId?: number) => {
    const formData = new FormData();
    formData.append('image', file);

    await fetcher
      .post(`/images?itemType=${IMAGE_TYPE.POST}&itemId=${itemId}`, formData)
      .then(r => {
        const range = editor.getSelection(true);
        editor.insertEmbed(range.index, 'image', r.data);
      })
      .catch(() => showSnackbar(SNACKBAR_MESSAGE.FAIL_UPLOAD_IMAGE, EXCLAMATION));
  };

  return { uploadImage };
};

export default useImage;
