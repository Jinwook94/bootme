import styled from 'styled-components';
import Button from '../../components/@common/Button';
import { Layout } from '../../components/@common/Layout';

export const CourseListPageLayout = styled(Layout)``;

export const MobileHeader = styled.div`
  cursor: pointer;
  background: linear-gradient(to right, rgba(255, 255, 255, 0.9), rgba(200, 234, 255, 1));
  display: flex;
  flex-direction: column;
  padding: 0.5rem 1rem 1.2rem 1rem;
  @media (min-width: 640px) {
    display: none;
  }
`;

export const MobileHeaderTextLarge = styled.span`
  color: #21293c;
  font-size: 24px;
  font-weight: 600;
  line-height: 40px;
`;

export const MobileHeaderTextMedium = styled.span`
  color: #4b587c;
  font-size: 18px;
  font-weight: 300;
  line-height: 32px;
  margin-top: 6px;
`;

export const SlideWrapper = styled.div`
  max-width: 100%;
  padding-right: 16px;
  padding-left: 16px;
  margin-right: auto;
  margin-left: auto;
  margin-bottom: 0.5rem;
  width: 100%;

  @media (min-width: 576px) {
    max-width: 540px;
  }

  @media (min-width: 768px) {
    max-width: 720px;
  }

  @media (min-width: 992px) {
    max-width: 960px;
  }

  @media (min-width: 1200px) {
    max-width: 1200px !important;
  }
`;

export const BodyWrapper = styled.div`
  padding-top: 0.5rem;
  padding-bottom: 0;
  display: flex;
  flex-direction: column;

  @media (max-width: 991px) {
    padding-bottom: 0;
  }
`;

export const BodyWrapper2 = styled.div`
  display: flex;
  -webkit-box-pack: justify;
  justify-content: space-between;
  align-items: flex-start;
  max-width: 75rem;
`;

export const SideFilterWrapper = styled.section`
  flex-shrink: 0;
  width: 16.75rem;
  display: block;
  margin-right: 1rem;

  @media (max-width: 767px) {
    display: none;
  }

  @media (max-width: 1200px) {
    width: 13rem;
  }
`;

export const NoResultsMessage = styled.p`
  margin-top: 5rem;
  width: 884px;
  text-align: center;
  font-size: 1.2rem;

  @media (max-width: 575px) {
    width: 100%;
    font-size: 0.9375rem;
  }

  @media (min-width: 575px) and (max-width: 768px) {
    width: 508px;
    font-size: 0.9375rem;
  }

  @media (min-width: 768px) and (max-width: 991px) {
    width: 464px;
    font-size: 1rem;
  }

  @media (min-width: 991px) and (max-width: 1200px) {
    width: 704px;
  }
`;

export const CourseListWrapper = styled.section`
  flex: 1 1 auto;
  display: flex;
  flex-direction: column;

  @media (max-width: 575px) {
    grid-template-columns: repeat(1, minmax(0px, 1fr));
  }

  @media (max-width: 991px) {
    grid-template-columns: repeat(2, minmax(0px, 1fr));
    width: 100%;
  }
`;

export const CourseListMenu = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: space-between;
  vertical-align: middle;

  @media (max-width: 575px) {
    align-items: center;
  }
`;

export const MenuContainer = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
`;

export const SearchInput = styled.div`
  width: 100%;
  display: flex;
  justify-content: center;
  margin-top: 24px;
  margin-bottom: 14px;

  @media (min-width: 768px) {
    display: none;
  }
`;

export const MenuLeft = styled.div`
  height: 32px;
  display: flex;
  align-items: flex-end;
`;

export const CourseCount = styled.span`
  margin-left: 1rem;
  font-weight: 700;
  line-height: 1.5;
  font-size: 14px;

  @media (max-width: 575px) {
    font-size: 13px;
  }
`;

export const MenuRight = styled.div``;

export const FilterButton = styled(Button)`
  display: inline-flex;
  align-items: center;
  max-height: 1.9375rem;
  font-size: 14px;

  span {
    display: inline-block;
    vertical-align: middle;
  }

  @media (min-width: 768px) {
    display: none;
  }
`;

export const SortSelect = styled.div`
  display: inline-flex;
  margin-left: 4.5px;
  vertical-align: middle;
`;

export const PaginationWrapper = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: center;
  margin-top: 2rem;
  margin-bottom: 2rem;
`;

export const FooterWrapper = styled.div``;

export const Footer = styled.div``;
