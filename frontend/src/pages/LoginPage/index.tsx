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
import { GoogleLoginButton } from '../../components/LoginModal/GoogleLogin';
import { NaverLoginButton } from '../../components/LoginModal/NaverLogin';
import { KakaoLoginButton } from '../../components/LoginModal/KakaoLogin';
import React, { useEffect } from 'react';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../constants/snackbar';
import { useSnackbar } from '../../hooks/useSnackbar';

const LoginPage = () => {
  const { showSnackbar } = useSnackbar();

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
                <GoogleLoginButton />
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
