import styled from 'styled-components';
import { Layout } from '../../components/@common/Layout';

export const BackgroundWrapper = styled.div`
  background-color: #f9fafb;
  height: 50vw;
`;

export const PromptLayout = styled(Layout)`
  padding: 1.5rem 0;
  display: flex;
  flex-direction: column;

  @media (max-width: 767px) {
    padding: 1rem 0 0 0 ;
  },
`;

export const PageHeader = styled.div`
  width: 90%;
  margin-top: 8px;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  flex-direction: column;
`;

export const Wrapper = styled.div`
  width: 90%;
  margin-top: 2rem;
  margin-left: auto;
  margin-right: auto;
  background-color: #ffffff;
  display: flex;
  flex-direction: row;
  justify-content: center;
  border: 1px solid #dae1e8;
  border-radius: 8px;
`;

export const InputWrapper = styled.div`
  display: flex;
  flex-direction: column;
  flex: 1;
  border-right: 1px solid #dae1e8;
`;

export const PromptWrapper = styled.div`
  flex: 1;
`;

export const Header = styled.div`
  padding: 16px 20px 16px 20px;
  border-bottom: 1px solid #dae1e8;
  font-size: 16px;
  font-weight: 600;
`;

export const Body = styled.div`
  display: flex;
  flex-direction: column;
  gap: 21px;
  padding: 16px 16px 16px 16px;
  color: #212529;
  font-size: 14px;
  font-weight: 500;
  word-break: break-word;
  height: 100%;
`;

export const Asterisk = styled.span`
  margin-left: 3px;
  color: rgb(250, 82, 82);
`;

export const Buttons = styled.div`
  width: 90%;
  margin-left: auto;
  margin-right: auto;
  display: flex;
  flex-direction: row;
`;

export const PromptGeneratorButton = styled.div`
  flex: 1;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  margin-top: 12px;
`;

export const PromptSubmissionButton = styled.div`
  flex: 1;
  display: flex;
  flex-direction: row;
  justify-content: flex-end;
  margin-top: 12px;
`;
