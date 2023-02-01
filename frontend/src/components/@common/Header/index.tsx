import { HeaderLeft, HeaderRight, Wrapper, LogIn, SignIn, HeaderItem, Logo, Dot, ServiceName, Menu } from './style';

import GitHubIcon from '../../../assets/github.svg';
import { Layout } from '../Layout';
import React, { useState } from 'react';
import { useLogin } from '../../../hooks/useLogin';
import LoginModal from '../../LoginModal';
import { useGoogleOneTapLogin } from '@react-oauth/google';

const Header = () => {
  const isLogin = false;
  const username = false;

  const [displaySpan, setDisplaySpan] = useState(1);
  const handleClick = () => {
    setDisplaySpan(displaySpan === 1 ? 2 : 1);
  };
  const { handleLoginModal } = useLogin();
  useGoogleOneTapLogin({
    onSuccess: credentialResponse => {
      console.log(
        '< Google One Tap Login > \n\nclientId: ' +
          credentialResponse.clientId +
          '\n\ncredential: ' +
          credentialResponse.credential
      );
    },
    onError: () => {
      alert('구글 로그인 실패');
    },
  });

  return (
    <>
      <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@48,400,1,0"
      />

      <Layout>
        <Wrapper>
          <HeaderLeft>
            <Logo>
              <GitHubIcon />
              <ServiceName>BootMe</ServiceName>
            </Logo>
            <HeaderItem> 부트캠프 </HeaderItem>
            <HeaderItem> 회사 </HeaderItem>
            <HeaderItem> 커뮤니티 </HeaderItem>
          </HeaderLeft>
          <HeaderRight>
            {isLogin && username ? (
              <>로그인됨</>
            ) : (
              <>
                <LogIn onClick={handleLoginModal}>로그인</LogIn>
                <Dot
                  width="4"
                  height="4"
                  fill="none"
                  xmlns="http://www.w3.org/2000/svg"
                  className="G0E1OVA5O87wEsrH564S"
                  style={{ margin: '0 0.5rem' }}
                >
                  <circle r="2" transform="matrix(1 0 0 -1 2 2)" fill="#C4C4C4"></circle>
                </Dot>
                <SignIn>회원가입</SignIn>
                <Menu onClick={handleClick}>
                  {displaySpan === 1 ? (
                    <span
                      className="material-symbols-outlined"
                      style={{
                        fontSize: '1.5rem',
                        verticalAlign: 'sub',
                        marginLeft: '1rem',
                      }}
                    >
                      menu
                    </span>
                  ) : (
                    <span
                      className="material-symbols-outlined"
                      style={{
                        fontSize: '1.5rem',
                        verticalAlign: 'sub',
                        marginLeft: '1rem',
                      }}
                    >
                      close
                    </span>
                  )}
                </Menu>
              </>
            )}
          </HeaderRight>
        </Wrapper>
      </Layout>
      <LoginModal />
    </>
  );
};

export default Header;
