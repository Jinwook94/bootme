import { useEffect, useRef } from 'react';
import LoginButton from '../LoginButton';
import * as jose from 'jose';
import { useLogin } from '../../../hooks/useLogin';

const { naver } = window;
const CLIENT_ID = process.env.REACT_APP_NAVER_CLIENT_ID;
const CLIENT_SECRET = process.env.REACT_APP_NAVER_CLIENT_SECRET;
const CALLBACK_URL = process.env.REACT_APP_REDIRECT_URI_NAVER;

const naverLogin = new naver.LoginWithNaverId({
  clientId: CLIENT_ID,
  callbackUrl: CALLBACK_URL,
  isPopup: false,
  loginButton: { color: 'green', type: 3, height: 40 },
  callbackHandle: true,
});

export const NaverLoginButton = () => {
  const { sendIdTokenToServer, handleLoginSuccess } = useLogin();
  const naverRef = useRef<HTMLDivElement>(null);
  const handleNaverLogin = () => {
    (naverRef.current?.children[0] as HTMLButtonElement).click();
  };

  useEffect(() => {
    naverLogin.init();
    // todo: getLoginStatus()를 리다이렉트된 페이지에서 호출해야 정상적인데, 그렇게 할 경우 에러 발생
    naverLogin.getLoginStatus(function (status: never) {
      if (status) {
        generateIdToken(naverLogin).then(idToken => {
          sendIdTokenToServer(idToken).then(() => {
            handleLoginSuccess('naver');
          });
        });
      }
    });
  }, []);

  return (
    <>
      <div ref={naverRef} id="naverIdLogin" style={{ display: 'none' }} />
      <div onClick={handleNaverLogin}>
        <LoginButton client={'naver'} />
      </div>
    </>
  );
};

const generateIdToken = async (naverLogin: naverTokenTypes) => {
  const alg = 'HS256';
  const typ = 'JWT';
  const secret = new TextEncoder().encode(CLIENT_SECRET);
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
