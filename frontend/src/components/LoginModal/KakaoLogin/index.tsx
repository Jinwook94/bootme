import { useEffect } from 'react';
import qs from 'qs';
import LoginButton from '../LoginButton';
import { useLogin } from '../../../hooks/useLogin';
import { noCredentialsFetcher } from '../../../api/fetcher';
import { useSecret } from '../../../hooks/useSecret';

export const KakaoLoginButton = () => {
  const { secrets } = useSecret();
  const REST_API_KEY = secrets['kakao-rest-api-key'];
  const REDIRECT_URI = secrets['kakao-redirect-uri'];
  const KAKAO_OAUTH_ACCESS_TOKEN_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code`;

  return (
    <div onClick={() => (window.location.href = KAKAO_OAUTH_ACCESS_TOKEN_URL)}>
      <LoginButton client={'kakao'} />
    </div>
  );
};

export const KakaoLoginRedirect = () => {
  const { secrets } = useSecret();
  const REST_API_KEY = secrets['kakao-rest-api-key'];
  const REDIRECT_URI = secrets['kakao-redirect-uri'];
  const KAKAO_OAUTH_ID_TOKEN_URL = 'https://kauth.kakao.com/oauth/token';
  const CLIENT_SECRET = secrets[''];

  const sendAccessTokenToKakao = async () => {
    const code = new URL(window.location.href).searchParams.get('code');
    const payload = qs.stringify({
      grant_type: 'authorization_code',
      client_id: REST_API_KEY,
      redirect_uri: REDIRECT_URI,
      code: code,
      client_secret: CLIENT_SECRET,
    });

    return noCredentialsFetcher
      .post(KAKAO_OAUTH_ID_TOKEN_URL, payload, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded;charset=utf-8',
        },
      })
      .then(r => {
        window.Kakao.init(REST_API_KEY);
        window.Kakao.Auth.setAccessToken(r.data.access_token);
        return r.data.id_token;
      })
      .catch(error => {
        console.log(error);
      });
  };
  const { sendIdTokenToServer, handleLoginSuccess } = useLogin();

  /**
   *  1. KakaoLoginButton: 카카오 인증 서버로 Access Token 받기를 요청하면, 카카오 인증 서버가 사용자에게 카카오계정 로그인을 통한 인증을 요청
   *  2. 사용자가 카카오 계정 로그인 완료하면 리디렉션된 URI 에서 Access Token 을 추출
   *  3. sendAccessTokenToKakao(): Access Token 등 인증 정보를 포함하여 kauth 서버로 POST 요청을 보내고 응답으로 ID Token 수신
   *  4. sendIdTokenToServer(): 수신한 ID Token 을 BootMe 서버로 전송
   *  5. 로컬스토리지 Login 값을 true 로 변경하고 이전 페이지로 리다이렉트
   * */
  useEffect(() => {
    sendAccessTokenToKakao().then(idToken =>
      sendIdTokenToServer(idToken).then(() => {
        handleLoginSuccess('kakao');
      })
    );
  }, []);

  return <></>;
};
