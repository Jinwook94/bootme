import { Layout } from '../../components/@common/Layout';
import styled from 'styled-components';

export const Wrapper = styled.div`
  background-color: #f9fafb;
`;

export const BookmarkLayout = styled(Layout)`
  padding-top: 1.5rem;
  
  @media (max-width: 767px) {
    padding: 1rem 0 0 0 ;
  },
`;

export const NavBarItem = styled.div`
  cursor: pointer;
  display: flex;
  flex-direction: row;
  align-items: center;
  padding: 0.625rem 0.75rem;
  color: #000;
  font-size: 0.875rem;
  font-weight: 500;
  svg {
    width: 24px;
    height: 24px;
    margin-right: 0.75rem;
  }
`;

export const PostCardList = styled.div`
  min-height: 1000px;
  width: 100%;

  @media (max-width: 640px) {
    min-height: auto;
  }
`;
