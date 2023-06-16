import styled from 'styled-components';

export const Wrapper1 = styled.div`
  scroll-behavior: smooth;
  z-index: 800;
  overflow-x: hidden;
  background-color: rgba(255, 255, 255, 0.95);
  position: fixed;
  top: 0;
  bottom: 0;
  right: 0;
  left: 0;
  overflow-y: auto;
  justify-content: center;
  display: flex;
  align-items: center;
`;
export const Wrapper2 = styled.div`
  transform-origin: center bottom;
  margin-top: auto;
  margin-bottom: auto;
  padding: 0;
`;
export const Wrapper3 = styled.div`
  width: 678px;
  box-shadow: rgb(0 0 0 / 15%) 0 2px 10px;
  background: rgba(255, 255, 255, 1);
  position: relative;
  border-radius: 4px;

  @media (max-width: 728px) {
    width: 100vw !important;
    height: 100vh !important;
    border-radius: 0;
  }
  @media (max-width: 904px) {
    width: 600px;
    height: auto;
  }
`;
export const Wrapper4 = styled.div`
  min-height: 695px;
  width: 100%;
  height: 100%;
  justify-content: space-between;
  flex-direction: row;
  border-radius: 4px;
  display: flex;

  @media (max-width: 904px) {
    justify-content: center;
    display: flex;
    height: 100vh;
  }
`;
export const Wrapper5 = styled.div`
  width: 360px;
  padding: 44px 56px;
  justify-content: center;
  display: flex;
  flex: 1 0 auto;
  flex-direction: column;
  align-items: center;
  text-align: center;

  @media (max-width: 728px) {
    padding-top: 120px;
  }
  @media (max-width: 904px) {
    width: 100%;
    display: flex;
  }
`;

export const WelcomeText = styled.span`
  line-height: 32px;
  font-size: 28px;
  font-weight: 400;
`;

export const LoginOptions = styled.div`
  margin-top: 50px;
  display: block;
`;

export const TermsOfService = styled.div`
  margin-top: 32px;
  margin-bottom: 32px;
  width: 450px;
  display: block;
`;

export const CloseButtonWrapper = styled.div`
  position: absolute;
  display: block;
  top: 12px;
  right: 12px;
`;

export const CloseButton = styled.button`
  margin: 0;
  padding: 0;
  background: transparent;
  overflow: visible;
  -webkit-tap-highlight-color: transparent;
`;
