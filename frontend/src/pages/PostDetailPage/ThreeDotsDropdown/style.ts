import styled from 'styled-components';
import { ButtonIconWrapper, MobileIconWrapper } from '../style';

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

export const LinkItem = styled.a`
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
  }
`;

export const IconWrapper = styled.div`
  width: 16px;
  transform: rotateY(0deg);
  margin-right: 14px;
  touch-action: none;

  svg {
    width: 100%;
    height: 100%;
  }
`;

export const MobileThreeDots = styled(MobileIconWrapper)`
  svg {
    fill: #878a8c;
  }
`;

export const ThreeDotsWrapper = styled(ButtonIconWrapper)`
  svg {
    fill: #878a8c;
  }
`;
