import { useEffect, useRef } from 'react';
import LoginButton from '../LoginButton';
import * as jose from 'jose';
import { useLogin } from '../../../hooks/useLogin';
import { useSecret } from '../../../hooks/useSecret';

const { naver } = window;

export const NaverLoginButton = () => {
  const { secrets } = useSecret();
  const CLIENT_ID = secrets['naver-client-id'];
  const REDIRECT_URL = secrets['naver-redirect-uri'];

  const naverLogin = new naver.LoginWithNaverId({
    clientId: CLIENT_ID,
    callbackUrl: REDIRECT_URL,
    isPopup: false,
    loginButton: { color: 'green', type: 3, height: 40 },
    callbackHandle: true,
  });

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
  const { secrets } = useSecret();
  const ISSUER = secrets['naver-issuer'];
  const AUDIENCE = secrets['naver-audience'];
  const SIGNING_KEY = secrets['naver-signing-key'];
  const alg = 'HS256';
  const typ = 'JWT';
  const signingKey = new TextEncoder().encode(SIGNING_KEY);
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
    .setIssuer(ISSUER)
    .setAudience(AUDIENCE)
    .sign(signingKey);
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
