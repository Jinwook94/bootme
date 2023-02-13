import React, { useEffect, useState } from 'react';
import ReactModal from 'react-modal';
import { Button } from 'antd';
import GitHubIcon from '../../../assets/github.svg';
import {
  Figcaption,
  Figure,
  HeaderLeft,
  HeaderRight,
  Items,
  Item,
  Logo,
  MenuBody,
  MenuHeader,
  NickName,
  Occupation,
  ServiceName,
  UserInfo,
  LoginWrapper,
  ButtonWrapper,
} from './style';
import { CloseIconBlack, CloseIconGray } from '../../../constants/icons';
import { useLogin } from '../../../hooks/useLogin';
const MenuModal = ({ isMenuOpen, setIsMenuOpen, nickName }: MenuModalProps) => {
  const [isHovered, setIsHovered] = useState(false);
  const [isLogin, setIsLogin] = useState(JSON.parse(localStorage.getItem('Login') || 'false'));
  const { handleLogOut, handleLoginModal } = useLogin();

  useEffect(() => {
    setIsLogin(JSON.parse(localStorage.getItem('Login') || 'false'));
  }, [localStorage.getItem('Login')]);

  return (
    <ReactModal
      className={'MenuModal'}
      isOpen={isMenuOpen}
      onRequestClose={() => setIsMenuOpen(!isMenuOpen)}
      shouldFocusAfterRender
      shouldCloseOnOverlayClick
      shouldCloseOnEsc
      preventScroll
      closeTimeoutMS={0}
      style={{
        overlay: {
          position: 'fixed',
          top: 0,
          left: 0,
          right: 0,
          bottom: 0,
          backgroundColor: 'rgba(183,181,181,0.9)',
        },
        content: {
          position: 'absolute',
          top: '10px',
          left: '10px',
          right: '10px',
          bottom: isLogin ? '260px' : '470px',
          border: '1px solid #ccc',
          background: '#fff',
          overflow: 'auto',
          WebkitOverflowScrolling: 'touch',
          borderRadius: '6px',
          outline: 'none',
          padding: '0',
        },
      }}
    >
      <MenuHeader>
        <HeaderLeft>
          <Logo>
            <GitHubIcon />
            <ServiceName>BootMe</ServiceName>
          </Logo>
        </HeaderLeft>
        <HeaderRight
          onClick={() => setIsMenuOpen(!isMenuOpen)}
          onMouseOver={() => setIsHovered(true)}
          onMouseOut={() => setIsHovered(false)}
        >
          {isHovered ? <CloseIconBlack /> : <CloseIconGray />}
        </HeaderRight>
      </MenuHeader>
      <MenuBody>
        <Items>
          <Item>부트캠프</Item>
          <Item>회사</Item>
          <Item>커뮤니티</Item>
        </Items>
        {isLogin ? (
          <UserInfo>
            <div style={{ borderTop: '1px solid rgb(235, 235, 235)', height: '0.7rem' }}></div>
            <Figure>
              <img
                width="42"
                height="42"
                src="https://ssl.pstatic.net/static/pwe/address/img_profile.png"
                alt="profile"
                style={{ borderRadius: '8px', objectFit: 'cover', marginRight: '15px' }}
              />
              <Figcaption>
                <NickName> {nickName} </NickName>
                <Occupation> 취준생 / 백엔드 </Occupation>
              </Figcaption>
            </Figure>
            <Item>프로필 관리</Item>
            <Item>북마크</Item>
            <div style={{ borderTop: '1px solid rgb(235, 235, 235)', height: '0.5rem' }}></div>
            <Item onClick={handleLogOut}>로그아웃</Item>
          </UserInfo>
        ) : (
          <LoginWrapper>
            <ButtonWrapper
              onClick={() => {
                setIsMenuOpen(!isMenuOpen);
                handleLoginModal();
              }}
            >
              <Button>로그인</Button>
            </ButtonWrapper>
          </LoginWrapper>
        )}
      </MenuBody>
    </ReactModal>
  );
};

export default MenuModal;

interface MenuModalProps {
  isMenuOpen: boolean;
  setIsMenuOpen: React.Dispatch<React.SetStateAction<boolean>>;
  nickName: string | null;
}
