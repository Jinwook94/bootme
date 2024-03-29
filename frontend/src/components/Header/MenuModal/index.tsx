import React, { useState } from 'react';
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
import PATH from '../../../constants/path';
import { Link } from 'react-router-dom';
const MenuModal = ({ isLogin, isMenuOpen, setIsMenuOpen, nickName, profileImage, job }: MenuModalProps) => {
  const clientUrl = process.env.CLIENT_URL;
  const [isHovered, setIsHovered] = useState(false);
  const { handleLogOut, handleLoginModal } = useLogin();

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
            <a href={'https://bootme.co.kr'}>
              <GitHubIcon />
              <ServiceName>BootMe</ServiceName>
            </a>
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
          <a href={`${clientUrl}course/list`}>
            <Item>부트캠프</Item>
          </a>
          <a href={`${clientUrl}prompt/generator`}>
            <Item>프롬프트</Item>
          </a>
          <a href={`${clientUrl}post/list`}>
            <Item>커뮤니티</Item>
          </a>
        </Items>
        {isLogin ? (
          <UserInfo>
            <div style={{ borderTop: '1px solid rgb(235, 235, 235)', height: '0.7rem' }}></div>
            <Figure>
              <img
                width="42"
                height="42"
                src={profileImage ?? ''}
                alt="profile"
                style={{ borderRadius: '8px', objectFit: 'cover', marginRight: '15px' }}
              />
              <Figcaption>
                <NickName> {nickName} </NickName>
                <Occupation>{job && job !== 'null' && job !== 'undefined' ? job : '직업을 입력하세요 :)'}</Occupation>
              </Figcaption>
            </Figure>
            <Link to={PATH.PROFILE}>
              <Item>프로필 관리</Item>
            </Link>
            <Link to={PATH.BOOKMARK.COURSE}>
              <Item>북마크</Item>
            </Link>
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
  isLogin: boolean;
  isMenuOpen: boolean;
  setIsMenuOpen: React.Dispatch<React.SetStateAction<boolean>>;
  profileImage: string | null;
  nickName: string | null;
  job: string | null;
}
