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
import { Button } from 'antd';
import { Link, useNavigate } from 'react-router-dom';
import PATH from '../../constants/path';

const LoginPage = () => {
  const { showSnackbar } = useSnackbar();
  const navigate = useNavigate();

  const handleGoBack = () => {
    navigate(-2);
  };

  useEffect(() => {
    showSnackbar(SNACKBAR_MESSAGE.NEED_LOGIN, EXCLAMATION);
  }, []);

  return (
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
              <div style={{ display: 'flex', flexDirection: 'row', alignItems: 'center' }}>
                <Link to={PATH.HOME}>
                  <Button type="primary" style={{ marginRight: '1rem' }}>
                    홈으로
                  </Button>
                </Link>
                <div onClick={handleGoBack}>
                  <Button>뒤로 가기</Button>
                </div>
              </div>
            </Wrapper5>
          </Wrapper4>
        </Wrapper3>
      </Wrapper2>
    </Wrapper1>
  );
};

export default LoginPage;
