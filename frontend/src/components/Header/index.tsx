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
import { DotIcon } from '../../constants/icons';
import Hamburger from 'hamburger-react';
import UserDropDown from './UserDropdown';
import MenuModal from './MenuModal';
import { useLocation } from 'react-router-dom';
import NotificationDropdown from './NotificationDropdown';
import { useSnackbar } from '../../hooks/useSnackbar';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../constants/snackbar';
import { GoogleLoginOneTap } from '../LoginModal/GoogleLogin';

const Header = () => {
  const { showSnackbar } = useSnackbar();
  const { isLogin, handleLoginModal } = useLogin();
  const [isMenuOpen, setIsMenuOpen] = useState(false);
  const [profileImage, setProfileImage] = useState(localStorage.getItem('profileImage'));
  const [nickName, setNickName] = useState(localStorage.getItem('nickName'));
  const location = useLocation();

  useEffect(() => {
    setNickName(localStorage.getItem('nickName'));
  }, [localStorage.getItem('nickName')]);

  useEffect(() => {
    setProfileImage(localStorage.getItem('profileImage'));
  }, [localStorage.getItem('profileImage')]);

  useEffect(() => {
    setIsMenuOpen(false);
  }, [location]);

  return (
    <>
      {!isLogin && <GoogleLoginOneTap />}
      <Wrapper1>
        <Layout>
          <Wrapper>
            <HeaderLeft>
              <a href={'https://bootme.co.kr/'}>
                <Logo>
                  <GitHubIcon />
                  <ServiceName>BootMe</ServiceName>
                </Logo>
              </a>
              <a href={'https://bootme.co.kr/course/list'}>
                <HeaderItem> 부트캠프 </HeaderItem>
              </a>
              <HeaderItem onClick={() => showSnackbar(SNACKBAR_MESSAGE.WORK_IN_PROGRESS, EXCLAMATION)}>회사</HeaderItem>
              <a href={'https://bootme.co.kr/post/list'}>
                <HeaderItem>커뮤니티 </HeaderItem>
              </a>
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
