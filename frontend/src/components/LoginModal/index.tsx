import ReactModal from 'react-modal';
import { useLogin } from '../../hooks/useLogin';
import './style.css';
import React, { useState } from 'react';
import { CloseIconBlack, CloseIconGray } from '../../constants/icons';
import { GoogleLoginButton } from './GoogleLogin';
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

  return (
    <ReactModal
      id={'LoginModal'}
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
                  <GoogleLoginButton />
                  <NaverLogin />
                  <KakaoLogin />
                </LoginOptions>
                <TermsOfService>약관</TermsOfService>
              </Wrapper5>
            </Wrapper4>
            <CloseButtonWrapper>
              <CloseButton
                onClick={handleLoginModal}
                onMouseOver={() => setIsHovered(true)}
                onMouseOut={() => setIsHovered(false)}
              >
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
