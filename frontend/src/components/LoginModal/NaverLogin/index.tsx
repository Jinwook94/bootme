import { useEffect, useRef } from 'react';
import LoginButton from '../LoginButton';
import * as jose from 'jose';
import { useLogin } from '../../../hooks/useLogin';
import { useSecret } from '../../../hooks/useSecret';
import { NAVER } from '../../../constants/others';

const { naver } = window;

export const NaverLoginButton = () => {
  const { secrets } = useSecret();
  const CLIENT_ID = secrets['naver-client-id'];
  const REDIRECT_URL = process.env.CLIENT_URL + 'oauth/naver';
  const naverLogin = new naver.LoginWithNaverId({
    clientId: CLIENT_ID,
    callbackUrl: REDIRECT_URL,
    isPopup: false,
    loginButton: { color: 'green', type: 3, height: 40 },
    callbackHandle: true,
  });

  const naverRef = useRef<HTMLDivElement>(null);
  const handleNaverLogin = () => {
    (naverRef.current?.children[0] as HTMLButtonElement).click();
  };

  useEffect(() => {
    naverLogin.init();
  }, []);

  return (
    <>
      <div ref={naverRef} id="naverIdLogin" style={{ display: 'none' }} />
      <div onClick={handleNaverLogin}>
        <LoginButton client={NAVER} />
      </div>
    </>
  );
};

export const NaverLoginRedirect = () => {
  const { sendIdTokenToServer, handleLoginSuccess } = useLogin();
  const { secrets } = useSecret();
  const CLIENT_ID = secrets['naver-client-id'];
  const REDIRECT_URL = process.env.CLIENT_URL + 'oauth/naver';

  const naverLogin = new naver.LoginWithNaverId({
    clientId: CLIENT_ID,
    callbackUrl: REDIRECT_URL,
    isPopup: false,
    callbackHandle: true,
  });

  const generateIdToken = async (naverLogin: naverTokenTypes) => {
    const ISSUER = secrets['naver-issuer'];
    const AUDIENCE = secrets['naver-audience'];
    const SIGNING_KEY = secrets['naver-signing-key'];
    const signingKey = new TextEncoder().encode(SIGNING_KEY);

    const alg = 'HS256';
    const typ = 'JWT';
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

  /**
   * <네이버 로그인 처리 과정>
   *
   *  [NaverLoginButton]
   *    1. 사용자가 id="naverIdLogin"인 div element(네이버 로그인버튼) 클릭 -> 네이버 로그인창으로 이동
   *    2. 사용자가 '아이디'와 '비밀번호'를 입력하고 '로그인' 버튼 클릭 -> 개인정보 제공 동의창으로 이동
   *    3. 사용자가 '필수 제공 항목'과 '추가 제공 항목'을 선택하고 '동의하기' 버튼 클릭 -> NaverLoginRedirect 페이지로 리다이렉트
   *
   *  [NaverLoginRedirect]
   *    1. naverLogin.init() - 네아로 로그인 정보를 초기화
   *    2. naverLogin.getLoginStatus() - 로그인한 사용자 정보를 받아옴
   *    3. generateIdToken() - 사용자 정보를 담은 JWT 생성
   *    4. sendIdTokenToServer() - 생성한 JWT 토큰을 서버로 전송
   *    5. handleLoginSuccess() - 서버 로그인 처리 완료 이후 사용자를 Home 페이지로 리다이렉트
   * */
  useEffect(() => {
    naverLogin.init();
    naverLogin.getLoginStatus(function (status: never) {
      if (status) {
        generateIdToken(naverLogin).then(idToken => {
          sendIdTokenToServer(idToken).then(() => {
            handleLoginSuccess(NAVER);
          });
        });
      }
    });
  }, [secrets]);

  return <></>;
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
