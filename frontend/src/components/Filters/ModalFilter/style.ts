import styled from 'styled-components';

export const ModalHeader = styled.div`
  -webkit-box-pack: justify !important;
  -webkit-box-align: center !important;
  display: flex !important;
  flex: 0 0 auto !important;
  align-items: center !important;
  justify-content: space-between !important;
  padding: 0 24px !important;
  min-height: 48px !important;
  border-bottom: 1px solid rgb(235, 235, 235) !important;
  color: rgb(34, 34, 34) !important;
  font-size: 16px !important;
  line-height: 20px !important;
  font-weight: 800 !important;
  box-sizing: border-box;
`;

export const IconWrapper = styled.div`
  position: absolute !important;
  display: flex !important;
  top: 16px !important;
  left: 24px !important;
  z-index: 1 !important;
  border-radius: 50%;
  background-color: transparent;
  cursor: pointer;
  padding: 0;
  margin: 0;
  border: none;
  transition: transform 0.25s ease;
`;

export const HeaderText = styled.div`
  -webkit-box-flex: 0 !important;
  overflow: hidden !important;
  flex: 0 1 auto !important;
  text-align: center !important;
  margin-left: 16px !important;
  margin-right: 16px !important;
  display: block;

  span {
    overflow: hidden !important;
    text-overflow: ellipsis !important;
    display: block;
    text-align: center !important;
    color: rgb(34, 34, 34) !important;
    font-size: 16px !important;
    line-height: 20px !important;
    font-weight: 600 !important;
  }
`;

export const ModalBody = styled.div`
  padding: 0 24px !important;
  flex: 1 1 auto !important;
  outline: none !important;
  overflow-y: scroll;
  max-height: calc(100% - 12px - 48px - 73px);
  z-index: -1;
`;

export const ModalFooter = styled.div`
  -webkit-box-pack: justify !important;
  -webkit-box-align: center !important;
  display: flex !important;
  flex: 0 0 auto !important;
  border-top: 1px solid rgb(235, 235, 235) !important;
  padding: 16px 24px !important;
  align-items: center !important;
  justify-content: space-between !important;
  font-size: 16px !important;
  line-height: 20px !important;
  position: fixed;
  top: auto;
  left: auto;
  right: auto;
  bottom: 0;
  max-height: 100%;
  width: 100%;
`;

export const ResetFilter = styled.div`
  cursor: pointer !important;
  display: inline-block !important;
  margin: 0px -10px !important;
  position: relative !important;
  text-align: center !important;
  width: auto !important;
  touch-action: manipulation !important;
  font-size: 1rem !important;
  line-height: 20px !important;
  font-weight: 500 !important;
  border-radius: 8px !important;
  outline: none !important;
  padding: 10px !important;
  transition: box-shadow 0.2s ease 0s, -ms-transform 0.1s ease 0s, -webkit-transform 0.1s ease 0s,
    transform 0.1s ease 0s !important;
  -webkit-tap-highlight-color: transparent !important;
  border: none !important;
  background: transparent !important;
  text-decoration: underline !important;
  contain: paint !important;
`;

export const ShowCourse = styled.button`
  cursor: pointer !important;
  display: inline-block !important;
  margin: 0 !important;
  position: relative !important;
  text-align: center !important;
  text-decoration: none !important;
  width: auto !important;
  touch-action: manipulation !important;
  font-size: 1rem !important;
  line-height: 20px !important;
  font-weight: 500 !important;
  border-radius: 8px !important;
  outline: none !important;
  padding: 14px 24px !important;
  transition: box-shadow 0.2s ease 0s, -ms-transform 0.1s ease 0s, -webkit-transform 0.1s ease 0s,
    transform 0.1s ease 0s !important;
  -webkit-tap-highlight-color: transparent !important;
  border: none !important;
  background: #222222 !important;
  color: #ffffff !important;
  contain: paint !important;
`;
