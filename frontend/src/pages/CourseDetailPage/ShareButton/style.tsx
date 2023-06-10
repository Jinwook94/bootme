import styled from 'styled-components';

export const ShareButtonWrapper = styled.button`
  padding: 0;
  width: 2.5rem;
  height: 2.5rem;
  border-radius: 0.25rem;
  line-height: 1.5rem;
  font-size: 1rem;
  font-weight: 500;
  background-color: #e9ecf3;
  color: #263747;
  fill: #263747;
  display: inline-flex;
  justify-content: center;
  align-items: center;
  svg {
    width: 1.5rem;
    height: 1.5rem;
    transition-delay: initial;
    transition-duration: 0.08s;
    transition-property: all;
    transition-timing-function: ease-in-out;
    vertical-align: top;
    overflow: hidden;
  }

  &:hover {
    background-color: #d7e2eb;
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

export const CommentIconMobile = styled.div`
  display: flex;
  align-items: center;
  gap: 4px;
  cursor: pointer;
  box-sizing: border-box;
  flex-direction: row;
  margin-right: 0.5rem;
  border-radius: 999px;
  border: 1px solid rgb(234, 237, 239);
  padding: 1px 8px;
  svg {
    fill: #878a8c;
    width: 16px;
    height: 16px;
  }

  &:hover {
    background: rgb(234, 237, 239);
  }
`;
