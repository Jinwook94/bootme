import ReactModal from 'react-modal';
import { useLogin } from '../../hooks/useLogin';
import './style.css';
import React, { useState } from 'react';
import { CloseIconBlack, CloseIconGray } from '../../constants/icons';
import { GoogleLogin } from '@react-oauth/google';
import NaverLogin from './NaverLogin';
import KakaoLogin from './KakaoLogin';
import {
  CloseButton,
  CloseButtonWrapper,
  LoginOptions,
  TermsOfService,
  WelcomeText,
  Wrapper1,
  Wrapper2,
  Wrapper3,
  Wrapper4,
  Wrapper5,
} from './style';

const LoginModal = () => {
  ReactModal.setAppElement('#root');

  const { isLoginModal, handleLoginModal } = useLogin();
  const preventClose = (event: { stopPropagation: () => void }) => {
    event.stopPropagation();
  };
  const [isHovered, setIsHovered] = useState(false);
  const handleMouseEnter = () => setIsHovered(true);
  const handleMouseLeave = () => setIsHovered(false);

  return (
    <ReactModal
      className={'LoginModal'}
      isOpen={isLoginModal}
      onRequestClose={handleLoginModal}
      shouldFocusAfterRender
      shouldCloseOnOverlayClick
      shouldCloseOnEsc
      preventScroll
      closeTimeoutMS={0}
      style={{
        overlay: {
          position: 'fixed',
          top: 100,
          left: 100,
          right: 100,
          bottom: 100,
        },
        content: {
          position: 'absolute',
          top: '0',
          left: '0',
          right: '0',
          bottom: '0',
          border: '1px solid #ccc',
          background: '#fff',
          overflow: 'auto',
          WebkitOverflowScrolling: 'touch',
          borderRadius: '4px',
          outline: 'none',
          padding: '0',
        },
      }}
    >
      <Wrapper1 onClick={handleLoginModal}>
        <Wrapper2>
          <Wrapper3>
            <Wrapper4>
              <Wrapper5 onClick={preventClose}>
                <WelcomeText>부트미에 오신것을 환영합니다. </WelcomeText>
                <LoginOptions>
                  <GoogleLogin
                    onSuccess={credentialResponse => {
                      console.log(
                        '< Google Login > \n\nclientId: ' +
                          credentialResponse.clientId +
                          '\n\ncredential: ' +
                          credentialResponse.credential
                      );
                      console.log('---- 구글 로그인 응답 ----');
                      console.log(credentialResponse);
                      console.log('----------------------');
                    }}
                    onError={() => {
                      alert('구글 로그인 실패');
                    }}
                    text={'signin_with'}
                    shape={'rectangular'}
                    width={'300px'}
                    auto_select
                  />
                  <NaverLogin />
                  <KakaoLogin />
                </LoginOptions>
                <TermsOfService>약관</TermsOfService>
              </Wrapper5>
            </Wrapper4>
            <CloseButtonWrapper>
              <CloseButton onClick={handleLoginModal} onMouseEnter={handleMouseEnter} onMouseLeave={handleMouseLeave}>
                {isHovered ? <CloseIconBlack /> : <CloseIconGray />}
              </CloseButton>
            </CloseButtonWrapper>
          </Wrapper3>
        </Wrapper2>
      </Wrapper1>
    </ReactModal>
  );
};

export default LoginModal;
