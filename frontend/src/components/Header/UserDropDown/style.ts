import styled from 'styled-components';

export const NickNameButton = styled.button`
  color: rgb(0, 132, 255);
  font-size: 14px;
  font-weight: 500;
  background: none;
  margin-right: 7px;

  @media (max-width: 768px) {
    display: none;
  }
`;

export const ContentWrapper = styled.div`
  width: 200px;
`;

export const Figure = styled.figure`
  display: flex;
  align-items: flex-start;
  padding: 20px 25px;
  border-radius: 8px;
  margin-bottom: 8px;
  background: rgb(251, 251, 251);
`;

export const Figcaption = styled.figcaption`
  display: flex;
  flex-direction: column;
`;

export const NickName = styled.span`
  font-size: 14px;
  margin-bottom: 2px;
  color: rgb(39, 43, 65);
  font-weight: 600;
  line-height: 22px;
`;

export const Occupation = styled.span`
  margin-bottom: 0;
  font-size: 13px;
  color: rgb(146, 153, 184);
`;

export const Items = styled.ul`
  list-style: outside none none;
  margin: 0;
  padding: 0;
  display: block;
  margin-inline-start: 0;
  margin-inline-end: 0;
`;

export const Item = styled.li`
  display: list-item;
  text-align: -webkit-match-parent;
`;

export const Link = styled.a`
  width: 100%;
  display: inline-flex;
  -webkit-box-align: center;
  align-items: center;
  padding: 10px 12px;
  font-size: 14px;
  transition: all 0.3s ease 0s;
  color: rgb(146, 153, 184);

  :hover {
    background: rgba(63, 120, 224, 0.02);
    color: rgb(63, 120, 224);
    padding-left: 22px;
  }
`;

export const IconWrapper = styled.div`
  width: 16px;
  transform: rotateY(0deg);
  margin-right: 14px;
  touch-action: none;
`;

export const LogoutButton = styled.a`
  display: inline-flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  font-size: 13px;
  font-weight: 500;
  text-align: center;
  position: relative;
  width: calc(100% + 24px);
  left: -12px;
  top: 12px;
  height: 100%;
  bottom: -15px;
  border-radius: 0 0 6px 6px;
  padding: 12px 0;
  background: rgb(248, 249, 251);
  color: #5a5f7d;
  outline: none;
  cursor: pointer;
  transition: color 0.3s;

  :hover {
    color: rgb(63, 120, 224);
  }
`;

export const TextWrapper = styled.div`
  margin-right: 9px;
`;
