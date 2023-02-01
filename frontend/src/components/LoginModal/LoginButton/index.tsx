import { IconWrapper, LoginText, Wrapper1, Wrapper2 } from './style';
import { KakaoLogo } from '../../../constants/icons';

const LoginButton = ({ client }: LoginButtonProps) => {
  return (
    <Wrapper1>
      <Wrapper2>
        {client === 'kakao' && (
          <>
            <IconWrapper>
              <KakaoLogo />
            </IconWrapper>
            <LoginText>카카오 아이디로 로그인</LoginText>
          </>
        )}
        {client === 'naver' && (
          <>
            <IconWrapper>
              <img style={{ width: '100%', height: '100%' }} src={'/naver_logo.svg'} alt="네이버 로그인" />
            </IconWrapper>
            <LoginText>네이버 아이디로 로그인</LoginText>
          </>
        )}
      </Wrapper2>
    </Wrapper1>
  );
};

export default LoginButton;

interface LoginButtonProps {
  client: string;
}
