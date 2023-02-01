import { useEffect, useRef } from 'react';
import LoginButton from '../LoginButton';

const NaverLogin = () => {
  const { naver } = window;
  const naverRef = useRef<HTMLDivElement>(null);
  const NAVER_CLIENT_ID = process.env.REACT_APP_NAVER_CLIENT_ID;
  const NAVER_CALLBACK_URL = process.env.REACT_APP_REDIRECT_URI;

  const initializeNaverLogin = () => {
    const naverLogin = new naver.LoginWithNaverId({
      clientId: NAVER_CLIENT_ID,
      callbackUrl: NAVER_CALLBACK_URL,
      isPopup: false,
      loginButton: { color: 'green', type: 3, height: 40 },
      callbackHandle: true,
    });
    naverLogin.init();

    naverLogin.getLoginStatus(async function (status: never) {
      if (status) {
        const userid = naverLogin.user.getEmail();
        const username = naverLogin.user.getName();
        console.log(
          '< Naver Login > \n\nAccessToken: ' +
            naverLogin.accessToken.accessToken +
            '\n\nUser ID: ' +
            userid +
            '\n\nUser Name: ' +
            username
        );
        console.log('---- 네이버 로그인 응답 ----');
        console.log(naverLogin);
        console.log('-----------------------');
      }
    });
  };

  const isAccessToken = () => {
    window.location.href.includes('access_token') && getToken();
  };

  const getToken = () => {
    const token = window.location.href.split('=')[1].split('&')[0];

    // 이후 로컬 스토리지 또는 state 에 저장하여 사용하자
    // localStorage.setItem('access_token', token)
    // setGetToken(token)
  };

  useEffect(() => {
    initializeNaverLogin();
    isAccessToken();
  }, []);

  const handleNaverLogin = () => {
    (naverRef.current?.children[0] as HTMLButtonElement).click();
  };

  return (
    <>
      <div ref={naverRef} id="naverIdLogin" style={{ display: 'none' }} />
      <div onClick={handleNaverLogin}>
        <LoginButton client={'naver'} />
      </div>
    </>
  );
};

export default NaverLogin;

declare global {
  interface Window {
    naver: any;
  }
}
