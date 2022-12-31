import styled from 'styled-components';
import Button from '../../components/@common/Button';
import { Layout } from '../../components/@common/Layout';

export const HomeLayout = styled(Layout)``;

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
  padding-bottom: 12.875rem;
  display: flex;
  flex-direction: column;

  @media (max-width: 991px) {
    padding-bottom: 10.6875rem;
  }
`;

export const BodyWrapper2 = styled.div`
  display: flex;
  -webkit-box-pack: justify;
  justify-content: space-between;
  align-items: flex-start;
  max-width: 75rem;
  margin: 0 auto;
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
  align-items: center;
  justify-content: space-between;
  padding: 1rem 0;
  vertical-align: middle;

  @media (max-width: 575px) {
    align-items: center;
    padding: 0.5rem 0;
  }

  @media (min-width: 767px) {
    display: none;
  }
`;

export const MenuLeft = styled.div``;

export const CourseCount = styled.h6`
  display: block;
  margin-left: 1rem;
  margin-bottom: 0.25rem;
  font-size: 1rem;
  font-weight: 700;
  vertical-align: top;
  line-height: 1.5;
  margin-top: 0;

  @media (max-width: 767px) {
    font-size: 13px;
  }
`;

export const MenuRight = styled.div``;

export const FilterButton = styled(Button)`
  display: inline-flex;
  align-items: center;
  max-height: 1.9375rem;
  font-size: 15px;

  span {
    display: inline-block;
    vertical-align: middle;
  }

  @media (max-width: 767px) {
    font-size: 14px;
  }
`;

export const FilterSelect = styled.select`
  -webkit-appearance: none;
  -moz-appearance: none;
  background-image: url(https://career.programmers.co.kr/assets/toggle-black-3ebb19a240c1ef57dac0b24e19fd00eff32a7e32ff8f2b87cfa2eb399c193c3a.png);
  background-position: calc(100% - 0.5rem) 49%;
  background-repeat: no-repeat;
  background-size: 0.625rem 0.3125rem;
  border: 1px solid #d7e2eb;
  border-radius: 0.25rem;
  color: #263747;
  padding: 0.3125rem 1.5rem 0.3125rem 0.8125rem;
  font-size: 0.875rem;
  font-weight: 500;
  line-height: 1.25rem;
  background-color: transparent;
  display: inline-block;
  text-align: center;
  vertical-align: middle;
  user-select: none;
  word-wrap: normal;
  text-transform: none;
  margin-left: 4.5px;

  @media (max-width: 767px) {
    font-size: 0.8125rem;
    line-height: 1.125rem;
  }
`;

export const PaginationWrapper = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: center;
`;

export const FooterWrapper = styled.div``;

export const Footer = styled.div``;
