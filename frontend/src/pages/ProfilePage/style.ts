import styled from 'styled-components';

export const Wrapper = styled.div`
  background: #f9fafb;
  min-height: 92.9vh;
`;

export const ProfileLayout = styled.div`
  max-width: 100%;
  width: 100%;
  margin-right: auto;
  margin-left: auto;

  @media (max-width: 992px) {
    padding: 1rem;
  }

  @media (min-width: 992px) {
    padding: 1.5rem;
  }

  @media (min-width: 1200px) {
    max-width: 720px !important;
    padding: 1.5rem 3rem 0 3rem;
  }
`;

export const TitleWrapper = styled.div`
  border-bottom: solid 1px rgb(229, 231, 235);
  padding-bottom: 0.5rem;
`;

export const ProfileImage = styled.div`
  margin-top: 1.5rem;
  transition: opacity 0.3s ease-in-out;
  display: inline-block;
`;

export const AvatarWrapper = styled.div`
  position: relative;
  display: inline-block;

  &:hover > div {
    opacity: 0.5;
  }

  &:hover > div {
    display: block;
  }
`;

export const IconWrapper = styled.div`
  position: absolute;
  top: 50%;
  left: 50%;
  transform: translate(-50%, -50%);
  cursor: pointer;
  display: none;
`;

export const ButtonWrapper = styled.div`
  display: flex;
  justify-content: flex-end;
  width: 100%;
`;

export const SaveButton = styled(ButtonWrapper)`
  margin-top: 2.5em;
`;
