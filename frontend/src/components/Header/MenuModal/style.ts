import styled from 'styled-components';

export const MenuHeader = styled.div`
  padding: 0.5rem 0.5rem 0.5rem 1rem;
  border-bottom: 1px solid rgb(235, 235, 235);
`;

export const HeaderLeft = styled.div`
  display: flex;
  height: 3rem;
  align-items: center;

  @media (min-width: 576px) and (max-width: 767px) {
    height: 3rem;
  }

  @media (min-width: 768px) and (max-width: 991px) {
    height: 3.375rem;
  }
`;

export const Logo = styled.a`
  display: flex;
  align-items: center;
  margin-right: 4.5rem;
`;

export const ServiceName = styled.span`
  font-size: 30px;
  font-weight: bold;
  line-height: 40px;
  margin-left: 0.15rem;
`;

export const HeaderRight = styled.div`
  margin: 0;
  padding: 0;
  background: transparent;
  overflow: visible;
  position: absolute;
  display: block;
  top: 16px;
  right: 20px;
  cursor: pointer;

  svg {
    max-width: none;
    max-height: none;
  }
`;

export const MenuBody = styled.div`
  padding: 0.5rem;
`;

export const Items = styled.ul`
  display: flex;
  flex-direction: column;
  padding-left: 0;
`;

export const Item = styled.li`
  padding: 0.5rem 0.75rem 0.5rem 0.75rem;
  border-radius: 6px;
  cursor: pointer;
  font-weight: 500;
  line-height: 24px;
  font-size: 16px;
  margin-bottom: 0.5rem;
  list-style: none;

  :hover {
    color: #0090f8;
    background: #f3f4f6;
  }
`;

export const UserInfo = styled.ul`
  display: flex;
  flex-direction: column;
  padding-left: 0;
`;

export const Figure = styled.figure`
  display: flex;
  align-items: flex-start;
  padding: 15px 25px;
  margin: 0 5px 4px 5px;
  background: rgb(248 248 248);
  border-radius: 8px;
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

export const LoginWrapper = styled.div`
  border-top: 1px solid rgb(235, 235, 235);
  padding: 12px 7px;
`;

export const ButtonWrapper = styled.div`
  display: inline-block;
`;
