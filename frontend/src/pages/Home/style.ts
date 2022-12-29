import styled from 'styled-components';
import Button from '../../components/@common/Button';

export const Wrapper = styled.div`
  max-width: 100%;
  padding-right: 16px;
  padding-left: 16px;
  margin-right: auto;
  margin-left: auto;
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

export const CourseListHeader = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  padding: 1rem 0;
  vertical-align: middle;

  @media (max-width: 575px) {
    align-items: center;
    padding: 1.5rem 0;
  }
`;

export const HeaderLeft = styled.div``;

export const CourseCount = styled.h6`
  display: block;
  margin-bottom: 0.25rem;
  font-weight: 700;
  vertical-align: top;
  line-height: 1.5;
  margin-top: 0;

  @media (max-width: 767px) {
    font-size: 13px;
  }
`;

export const HeaderRight = styled.div``;

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
