import { useLogin } from '../../../hooks/useLogin';
import { GOOGLE } from '../../../constants/others';
import { useEffect } from 'react';
import { useSecret } from '../../../hooks/useSecret';

export const GoogleLoginButton = () => {
  const { sendIdTokenToServer, handleLoginSuccess } = useLogin();
  const { secrets } = useSecret();
  const googleClientId = secrets['google-client-id'];

  window.onSuccess = async (credentialResponse: { credential: string }) => {
    console.log(credentialResponse);
    sendIdTokenToServer(credentialResponse.credential).then(() => {
      handleLoginSuccess(GOOGLE);
    });
  };

  useEffect(() => {
    google.accounts.id.initialize({
      client_id: googleClientId,
      callback: window.onSuccess,
    });

    const buttonElement = document.querySelector('.g_id_signin');

    google.accounts.id.renderButton(buttonElement as HTMLElement, {
      click_listener(): void {},
      locale: '',
      logo_alignment: undefined,
      shape: undefined,
      type: 'standard',
      width: '300',
      theme: 'outline',
      size: 'large',
      text: 'signin_with',
    });
  }, []);

  return (
    <>
      <div
        id="g_id_onload"
        data-client_id={googleClientId}
        data-context="signin"
        data-ux_mode="popup"
        data-callback="onSuccess"
        data-auto_prompt="false"
      ></div>
      <div className="g_id_signin"></div>
    </>
  );
};

export const GoogleLoginOneTap = () => {
  const { sendIdTokenToServer, handleLoginSuccess } = useLogin();
  const { secrets } = useSecret();
  const googleClientId = secrets['google-client-id'];

  window.onSuccess = async (credentialResponse: { credential: string }) => {
    sendIdTokenToServer(credentialResponse.credential).then(() => {
      handleLoginSuccess(GOOGLE);
    });
  };

  useEffect(() => {
    const script = document.createElement('script');
    script.src = 'https://accounts.google.com/gsi/client';
    script.async = true;
    document.body.appendChild(script);

    if (googleClientId) {
      google.accounts.id.initialize({
        client_id: googleClientId,
        callback: window.onSuccess,
      });
    }

    return () => {
      document.body.removeChild(script);
    };
  }, [googleClientId]);

  if (!googleClientId) {
    return null;
  }

  return (
    <div
      id="g_id_onload"
      data-client_id={googleClientId}
      data-context="signin"
      data-callback="onSuccess"
      data-auto_select="true"
      data-itp_support="true"
      data-auto_prompt="true"
    ></div>
  );
};

/* data-client_id="565238562293-rvt6dh5pl2g3ebl7i40e9on41j7krt3f.apps.googleusercontent.com" */
