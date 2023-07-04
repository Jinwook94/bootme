import styled from 'styled-components';

export const Wrapper = styled.div`
  width: 90%;
  margin-top: 5rem;
  margin-left: auto;
  margin-right: auto;
  background-color: #ffffff;
  display: flex;
  flex-direction: column;
  border: 1px solid #dae1e8;
  border-radius: 8px;
  @media (min-width: 1280px) {
    max-width: 48rem;
  }
`;

export const Header = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: center;
  align-items: center;
  gap: 0.25rem;
  padding: 0.75rem;
  color: rgb(142, 142, 160);
  background-color: rgba(247, 247, 248, 1);
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
  border-bottom: 1px solid rgba(0, 0, 0, 0.1);
`;

export const Conversation = styled.div`
  min-height: 35vw;
`;

export const ProfileImageWrapper = styled.div``;

export const MessageWrapper = styled.div``;

export const UserInput = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 96%;
  padding: 8px 0 8px 8px;
  margin: 0.6rem auto;
  border: 1px solid rgba(0, 0, 0, 0.1);
  border-radius: 0.75rem;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
`;

export const IconWrapper = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 32px;
  height: 32px;
  svg {
    width: 16px;
    height: 16px;
  }
`;
