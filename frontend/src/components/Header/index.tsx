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
  MenuButton,
} from './style';

import GitHubIcon from '../../assets/github.svg';
import { Layout } from '../@common/Layout';
import React, { useEffect, useState } from 'react';
import { useLogin } from '../../hooks/useLogin';
import LoginModal from '../LoginModal';
import { GoogleLoginOneTap } from '../LoginModal/GoogleLogin';
import { DotIcon, NotificationIcon, NotificationActiveIcon } from '../../constants/icons';
import Hamburger from 'hamburger-react';
import UserDropDown from './UserDropDown';
import MenuModal from './MenuModal';
import { Link } from 'react-router-dom';
import PATH from '../../constants/path';

const Header = () => {
  const { isLogin, handleLoginModal } = useLogin();
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [profileImage, setProfileImage] = useState(localStorage.getItem('ProfileImage'));
  const [nickName, setNickName] = useState(localStorage.getItem('NickName'));

  useEffect(() => {
    setNickName(localStorage.getItem('NickName'));
  }, [localStorage.getItem('NickName')]);

  useEffect(() => {
    setProfileImage(localStorage.getItem('ProfileImage'));
  }, [localStorage.getItem('ProfileImage')]);

  return (
    <>
      {!isLogin && <GoogleLoginOneTap />}{' '}
      <Layout>
        <Wrapper>
          <HeaderLeft>
            <Link to={PATH.HOME}>
              {' '}
              <Logo>
                <GitHubIcon />
                <ServiceName>BootMe</ServiceName>
              </Logo>
            </Link>
            <HeaderItem> 부트캠프 </HeaderItem>
            <HeaderItem> 회사 </HeaderItem>
            <HeaderItem> 커뮤니티 </HeaderItem>
          </HeaderLeft>
          <HeaderRight>
            {isLogin ? (
              <>
                <UserDropDown profileImage={profileImage} nickName={nickName} />
                <NotiButton>
                  <NotificationIcon />
                  <NotificationActiveIcon />
                </NotiButton>
                <MenuButton onClick={() => setIsMenuOpen(!isMenuOpen)}>
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
                <MenuButton onClick={() => setIsMenuOpen(!isMenuOpen)}>
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
      <MenuModal
        isLogin={isLogin}
        isMenuOpen={isMenuOpen}
        setIsMenuOpen={setIsMenuOpen}
        profileImage={profileImage}
        nickName={nickName}
      />
    </>
  );
};

export default Header;
