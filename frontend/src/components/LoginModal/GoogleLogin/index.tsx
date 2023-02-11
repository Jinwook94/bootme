import { GoogleLogin } from '@react-oauth/google';
import { useGoogleOneTapLogin } from '@react-oauth/google';
import { useLogin } from '../../../hooks/useLogin';

export const GoogleLoginButton = () => {
  const { sendIdTokenToServer } = useLogin();
  return (
    <GoogleLogin
      onSuccess={credentialResponse => {
        console.log(
          '< Google Login > \n\nclientId: ' +
            credentialResponse.clientId +
            '\n\ncredential: ' +
            credentialResponse.credential
        );
        console.log('---- 구글 로그인 응답 ----');
        console.log(credentialResponse);
        console.log('----------------------');
        const idToken = credentialResponse.credential;
        sendIdTokenToServer(idToken);
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
  const { sendIdTokenToServer } = useLogin();

  useGoogleOneTapLogin({
    onSuccess: credentialResponse => {
      console.log(
        '< Google One Tap Login > \n\nclientId: ' +
          credentialResponse.clientId +
          '\n\ncredential: ' +
          credentialResponse.credential
      );
      const idToken = credentialResponse.credential;
      sendIdTokenToServer(idToken);
    },
    onError: () => {
      alert('구글 로그인 실패');
    },
  });
  return null;
};
