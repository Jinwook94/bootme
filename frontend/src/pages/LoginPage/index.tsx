import {
  LoginOptions,
  TermsOfService,
  WelcomeText,
  Wrapper1,
  Wrapper2,
  Wrapper3,
  Wrapper4,
  Wrapper5,
} from '../../components/LoginModal/style';
import { NaverLoginButton } from '../../components/LoginModal/NaverLogin';
import { KakaoLoginButton } from '../../components/LoginModal/KakaoLogin';
import React, { useEffect, useRef } from 'react';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../constants/snackbar';
import { useSnackbar } from '../../hooks/useSnackbar';
import { useSecret } from '../../hooks/useSecret';
import { GOOGLE } from '../../constants/others';
import { useLogin } from '../../hooks/useLogin';

const LoginPage = () => {
  const { showSnackbar } = useSnackbar();
  const googleLoginButtonRef = useRef(null);
  const { secrets } = useSecret();
  const { sendIdTokenToServer, handleLoginSuccess } = useLogin();
  const googleClientId = secrets['google-client-id'];

  window.onSuccess = async (credentialResponse: { credential: string }) => {
    sendIdTokenToServer(credentialResponse.credential).then(() => {
      handleLoginSuccess(GOOGLE);
    });
  };

  useEffect(() => {
    //... your logic
    if (googleClientId) {
      google.accounts.id.initialize({
        client_id: googleClientId,
        callback: window.onSuccess,
      });

      const buttonElement = googleLoginButtonRef.current;

      google.accounts.id.renderButton(buttonElement as unknown as HTMLElement, {
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
    }
  }, [googleClientId]);

  useEffect(() => {
    showSnackbar(SNACKBAR_MESSAGE.NEED_LOGIN, EXCLAMATION);
  }, []);

  return (
    // todo: LoginModal 컴포넌트와 동일한 코드이므로 아래 코드를 따로 컴포넌트 분리 필요
    <Wrapper1>
      <Wrapper2>
        <Wrapper3>
          <Wrapper4>
            <Wrapper5>
              <WelcomeText>부트미에 오신것을 환영합니다. </WelcomeText>
              <LoginOptions>
                <div ref={googleLoginButtonRef} />
                <NaverLoginButton />
                <KakaoLoginButton />
              </LoginOptions>
              <TermsOfService>약관</TermsOfService>
            </Wrapper5>
          </Wrapper4>
        </Wrapper3>
      </Wrapper2>
    </Wrapper1>
  );
};

export default LoginPage;
