import { useEffect } from 'react';
import LoginButton from '../LoginButton';
import { useLogin } from '../../../hooks/useLogin';
import { useSecret } from '../../../hooks/useSecret';
import { NAVER } from '../../../constants/others';
import PATH from '../../../constants/path';

export const NaverLoginButton = () => {
  const { secrets } = useSecret();
  const CLIENT_ID = secrets?.['naverClientId'];
  const REDIRECT_URL = process.env.CLIENT_URL + PATH.OAUTH.NAVER;
  const STATE = 'bootme_state';
  const NAVER_AUTH_URL = `https://nid.naver.com/oauth2.0/authorize?response_type=code&client_id=${CLIENT_ID}&state=${STATE}&redirect_uri=${REDIRECT_URL}`;

  const handleNaverLogin = () => {
    window.location.href = NAVER_AUTH_URL;
  };

  return (
    <>
      <div onClick={handleNaverLogin}>
        <LoginButton client={NAVER} />
      </div>
    </>
  );
};

export const NaverLoginRedirect = () => {
  const { handleNaverLogin, handleLoginSuccess } = useLogin();
  const { secrets } = useSecret();
  const CLIENT_ID = secrets?.['naverClientId'];
  const CLIENT_SECRET = secrets?.['naverClientSecret'];
  const STATE = 'bootme_state';
  const urlParams = new URLSearchParams(window.location.search);
  const CODE = urlParams.get('code');
  const ACCESS_TOKEN_REQUEST_URL = `https://nid.naver.com/oauth2.0/token?grant_type=authorization_code&client_id=${CLIENT_ID}&client_secret=${CLIENT_SECRET}&code=${CODE}&state=${STATE}  
`;

  /**
   * <네이버 로그인 처리 과정>
   *
   *  [NaverLoginButton]
   *    1. 사용자가 로그인 버튼 클릭 -> 네이버 로그인창으로 이동
   *    2. 사용자가 '아이디'와 '비밀번호'를 입력하고 '로그인' 버튼 클릭 -> 개인정보 제공 동의창으로 이동
   *    3. 사용자가 '필수 제공 항목'과 '추가 제공 항목'을 선택하고 '동의하기' 버튼 클릭 -> NaverLoginRedirect 페이지로 리다이렉트
   *
   *  [NaverLoginRedirect]
   *    1. handleNaverLogin() - 엑세스 토큰 발급 요청 URL 을 서버로 전달
   *        -> 서버에서 로그인 처리 후 헤더에서 사용할 유저 정보 반환
   *        -> 서버에서 받은 유저 정보를 로컬 스토리지에 저장
   *    2. handleLoginSuccess() - 로그인 처리 완료 이후 사용자를 이전 페이지로 리다이렉트
   * */
  useEffect(() => {
    handleNaverLogin(ACCESS_TOKEN_REQUEST_URL).then(() => {
      handleLoginSuccess(NAVER);
    });
  }, []);

  return <></>;
};
