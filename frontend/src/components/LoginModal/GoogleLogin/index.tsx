import { GoogleLogin } from '@react-oauth/google';
import { useGoogleOneTapLogin } from '@react-oauth/google';
import { useLogin } from '../../../hooks/useLogin';
import { GOOGLE } from '../../../constants/others';

export const GoogleLoginButton = () => {
  const { sendIdTokenToServer, handleLoginSuccess } = useLogin();
  return (
    <GoogleLogin
      onSuccess={credentialResponse => {
        sendIdTokenToServer(credentialResponse.credential).then(() => {
          handleLoginSuccess(GOOGLE);
        });
      }}
      onError={() => {
        alert('구글 로그인 실패');
      }}
      text={'signin_with'}
      shape={'rectangular'}
      width={'300px'}
      auto_select
    />
  );
};

export const GoogleLoginOneTap = () => {
  const { sendIdTokenToServer, handleLoginSuccess } = useLogin();

  useGoogleOneTapLogin({
    onSuccess: credentialResponse => {
      sendIdTokenToServer(credentialResponse.credential).then(() => {
        handleLoginSuccess(GOOGLE);
      });
    },
    onError: () => {
      alert('구글 로그인 실패');
    },
  });
  return null;
};
