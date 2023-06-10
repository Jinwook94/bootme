import styled from 'styled-components';

export const Wrapper = styled.div`
  padding-top: 1rem;
  padding-bottom: 2rem;
  background: #dae0e6;
  height: 100%;
  min-height: 93vh;
`;

export const PageLayout = styled.div`
  max-width: 100%;
  padding: 1rem;
  margin-right: auto;
  margin-left: auto;
  width: 100%;
  border-radius: 10px;
  background-color: #ffffff;

  @media (min-width: 576px) {
    max-width: 540px;
  }

  @media (min-width: 768px) {
    max-width: 720px;
  }

  @media (min-width: 1200px) {
    max-width: 860px !important;
  }
`;

export const HeaderWrapper = styled.div`
  font-size: 24px;
  font-weight: 500;
`;

export const BodyWrapper = styled.div`
  margin-top: 1.5rem;
`;

export const TitleWrapper = styled.div`
  position: relative;
  display: block;
  margin: 0.5rem 0;
`;

export const TitleTextArea = styled.textarea`
  color: #1c1c1c;
  overflow-wrap: break-word;
  min-height: 39px;
  padding: 8px 68px 8px 16px;
  border: 1px solid #edeff1;
  border-radius: 4px;
  background-color: transparent;
  resize: none;
  box-sizing: border-box;
  overflow: hidden;
  display: block;
  width: 100%;
  outline: none;
  font-size: 14px;
  font-weight: 400;
  line-height: 21px;
  font-family: inherit;

  ::placeholder {
    color: #878a8c;
  }
`;

export const TitleLength = styled.div`
  font-size: 10px;
  font-weight: 700;
  letter-spacing: 0.5px;
  line-height: 12px;
  text-transform: uppercase;
  bottom: 12px;
  color: #878a8c;
  pointer-events: none;
  position: absolute;
  right: 12px;
`;

export const Buttons = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  margin-top: 1rem;
`;

export const CancelButton = styled.div`
  margin-right: 8px;
`;

export const UploadButton = styled.div``;
