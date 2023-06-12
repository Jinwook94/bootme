import styled from 'styled-components';

export const LargeScreen = styled.div`
  @media (max-width: 640px) {
    display: none;
  }
`;

export const Mobile = styled.div`
  @media (min-width: 640px) {
    display: none;
  }
`;

export const BookmarkButton = styled.button`
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
