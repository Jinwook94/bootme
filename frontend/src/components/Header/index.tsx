import {
  HeaderLeft,
  HeaderRight,
  Wrapper,
  LogIn,
  SignIn,
  HeaderItem,
  Logo,
  ServiceName,
  NotiButton,
  DotWrapper,
  NickNameButton,
  MenuButton,
} from './style';

import GitHubIcon from '../../assets/github.svg';
import { Layout } from '../@common/Layout';
import React, { useState } from 'react';
import { useLogin } from '../../hooks/useLogin';
import LoginModal from '../LoginModal';
import { GoogleLoginOneTap } from '../LoginModal/GoogleLogin';
import { DotIcon, NotificationIcon } from '../../constants/icons';
import Hamburger from 'hamburger-react';

const Header = () => {
  const { handleLoginModal } = useLogin();
  const isLogin: boolean = JSON.parse(localStorage.getItem('Login') || 'false');
  const [isMenuOpen, setIsMenuOpen] = useState(false);

  return (
    <>
      {!isLogin && <GoogleLoginOneTap />}{' '}
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
            {isLogin ? (
              <>
                <NickNameButton>{localStorage.getItem('HeaderNickName')}님</NickNameButton>
                <NotiButton>
                  <NotificationIcon />
                </NotiButton>
                <MenuButton>
                  <Hamburger
                    toggled={isMenuOpen}
                    toggle={setIsMenuOpen}
                    size={20}
                    direction="left"
                    color={'#9CA3AF'}
                    rounded
                  />
                </MenuButton>
              </>
            ) : (
              <>
                <LogIn onClick={handleLoginModal}>로그인</LogIn>
                <DotWrapper>
                  <DotIcon />
                </DotWrapper>
                <SignIn onClick={handleLoginModal}>회원가입</SignIn>
                <MenuButton>
                  <Hamburger
                    toggled={isMenuOpen}
                    toggle={setIsMenuOpen}
                    size={20}
                    direction="left"
                    color={'#9CA3AF'}
                    rounded
                  />
                </MenuButton>
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
