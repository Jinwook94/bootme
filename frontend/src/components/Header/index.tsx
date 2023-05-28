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
  Wrapper1,
} from './style';

import GitHubIcon from '../../assets/github.svg';
import { Layout } from '../@common/Layout';
import React, { useEffect, useState } from 'react';
import { useLogin } from '../../hooks/useLogin';
import LoginModal from '../LoginModal';
import { GoogleLoginOneTap } from '../LoginModal/GoogleLogin';
import { DotIcon } from '../../constants/icons';
import Hamburger from 'hamburger-react';
import UserDropDown from './UserDropdown';
import MenuModal from './MenuModal';
import { Link, useLocation } from 'react-router-dom';
import PATH from '../../constants/path';
import NotificationDropdown from './NotificationDropdown';
import { useSnackbar } from '../../hooks/useSnackbar';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../constants/snackbar';

const Header = () => {
  const { showSnackbar } = useSnackbar();
  const { isLogin, handleLoginModal } = useLogin();
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [profileImage, setProfileImage] = useState(localStorage.getItem('ProfileImage'));
  const [nickName, setNickName] = useState(localStorage.getItem('NickName'));
  const location = useLocation();

  useEffect(() => {
    setNickName(localStorage.getItem('NickName'));
  }, [localStorage.getItem('NickName')]);

  useEffect(() => {
    setProfileImage(localStorage.getItem('ProfileImage'));
  }, [localStorage.getItem('ProfileImage')]);

  useEffect(() => {
    setIsMenuOpen(false);
  }, [location]);

  return (
    <>
      {!isLogin && <GoogleLoginOneTap />}{' '}
      <Wrapper1>
        <Layout>
          <Wrapper>
            <HeaderLeft>
              <Link to={PATH.HOME}>
                <Logo>
                  <GitHubIcon />
                  <ServiceName>BootMe</ServiceName>
                </Logo>
              </Link>
              <Link to={PATH.HOME}>
                <HeaderItem> 부트캠프 </HeaderItem>
              </Link>
              <HeaderItem onClick={() => showSnackbar(SNACKBAR_MESSAGE.WORK_IN_PROGRESS, EXCLAMATION)}>회사</HeaderItem>
              <HeaderItem onClick={() => showSnackbar(SNACKBAR_MESSAGE.WORK_IN_PROGRESS, EXCLAMATION)}>
                커뮤니티{' '}
              </HeaderItem>
            </HeaderLeft>
            <HeaderRight>
              {isLogin ? (
                <>
                  <UserDropDown profileImage={profileImage} nickName={nickName} />
                  <NotiButton>
                    <NotificationDropdown />
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
      </Wrapper1>
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
