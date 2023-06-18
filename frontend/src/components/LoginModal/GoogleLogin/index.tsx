import { GoogleLogin } from '@react-oauth/google';
import { useGoogleOneTapLogin } from '@react-oauth/google';
import { useLogin } from '../../../hooks/useLogin';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../../constants/snackbar';
import { useSnackbar } from '../../../hooks/useSnackbar';

export const GoogleLoginButton = () => {
  const { showSnackbar } = useSnackbar();
  const { sendIdTokenToServer, handleLoginSuccess } = useLogin();
  return (
    <GoogleLogin
      onSuccess={credentialResponse => {
        sendIdTokenToServer(credentialResponse.credential).then(() => {
          handleLoginSuccess();
        });
      }}
      onError={() => {
        showSnackbar(SNACKBAR_MESSAGE.FAIL_LOGIN, EXCLAMATION);
      }}
      text={'signin_with'}
      shape={'rectangular'}
      width={'300px'}
      auto_select
    />
  );
};

export const GoogleLoginOneTap = () => {
  const { showSnackbar } = useSnackbar();
  const { sendIdTokenToServer, handleLoginSuccess } = useLogin();

  useGoogleOneTapLogin({
    onSuccess: credentialResponse => {
      sendIdTokenToServer(credentialResponse.credential).then(() => {
        handleLoginSuccess();
      });
    },
    onError: () => {
      showSnackbar(SNACKBAR_MESSAGE.FAIL_LOGIN, EXCLAMATION);
    },
  });
  return null;
};
