import { useEffect, useRef } from 'react';
import LoginButton from '../LoginButton';
import * as jose from 'jose';
import axios from 'axios';

const NaverLogin = () => {
  const { naver } = window;
  const naverRef = useRef<HTMLDivElement>(null);
  const NAVER_CLIENT_ID = process.env.REACT_APP_NAVER_CLIENT_ID;
  const NAVER_CLIENT_SECRET = process.env.REACT_APP_NAVER_CLIENT_SECRET;
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
        generateIdToken(naverLogin).then(idToken => sendIdTokenToServer(idToken));
      }
    });
  };

  const generateIdToken = async (naverLogin: naverTokenTypes) => {
    // todo: 추후 보다 안전한 RS256 알고리즘으로 업데이트
    const alg = 'HS256';
    const typ = 'JWT';
    const secret = new TextEncoder().encode(NAVER_CLIENT_SECRET);
    const payload = {
      ageRange: naverLogin.user.age,
      birthDay: naverLogin.user.birthday,
      birthYear: naverLogin.user.birthyear,
      email: naverLogin.user.email,
      gender: naverLogin.user.gender,
      phoneNumber: naverLogin.user.mobile,
      name: naverLogin.user.name,
      nickname: naverLogin.user.nickname,
      picture: naverLogin.user.profile_image,
    };
    return await new jose.SignJWT(payload)
      .setProtectedHeader({ alg, typ })
      .setIssuedAt()
      .setExpirationTime(naverLogin.accessToken.expires)
      .setIssuer('bootMe.frontend.naverLogin')
      .setAudience('urn:example:audience')
      .setSubject('testSubject')
      .sign(secret);
  };

  const sendIdTokenToServer = (idToken: string) => {
    axios
      .post('http://localhost:8080/login', null, {
        headers: {
          Authorization: 'Bearer ' + idToken,
        },
      })
      .then(response => {
        console.log('==== Axios response ====');
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });
  };

  useEffect(() => {
    initializeNaverLogin();
    isAccessToken();
  }, []);

  const isAccessToken = () => {
    window.location.href.includes('access_token') && getToken();
  };

  const getToken = () => {
    const token = window.location.href.split('=')[1].split('&')[0];

    // 이후 로컬 스토리지 또는 state 에 저장하여 사용하자
    // localStorage.setItem('access_token', token)
    // setGetToken(token)
  };

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

interface naverTokenTypes {
  user: {
    age: string;
    birthday: string;
    birthyear: string;
    email: string;
    gender: string;
    mobile: string;
    name: string;
    nickname: string;
    profile_image: string;
  };
  accessToken: { expires: string };
}
