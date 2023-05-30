import styled from 'styled-components';
import { Layout } from '../../components/@common/Layout';

export const StyledLink = styled.a`
  color: inherit;
  text-decoration: none;
  &:hover {
    text-decoration: underline;
  }
`;

export const Wrapper1 = styled.div`
  background: #f9fafb;
`;
export const CourseDetailLayout = styled(Layout)``;

export const Wrapper = styled.div`
  min-height: 1500px;
  display: grid;
  grid-template-columns: 49.75rem auto;
  column-gap: 1rem;
  max-width: 75rem;
  padding: 2rem 1.25rem;
  margin: 0 auto;

  @media (max-width: 1199px) {
    grid-template-columns: auto;
  }

  @media (max-width: 767px) {
    padding: 0;
  }
`;

export const Content = styled.div`
  padding: 2.625rem 2.5rem;
  border: 1px solid #f2f2f2;
  border-radius: 1rem;
  background: #fff;
  @media (max-width: 767px) {
    padding: 1.5rem 1.25rem;
    border-radius: 0;
    border-left: none;
    border-right: none;
  }
`;

export const ContentHeader = styled.div`
  display: flex;
  justify-content: space-between;
`;

export const CompanyLogoWrapper = styled.div`
  width: 4rem;
  height: 4rem;
  min-width: 4rem;
  min-height: 4rem;
  border-radius: 0.25rem;
`;

export const CompanyLogo = styled.img``;

export const HeaderDescription = styled.div`
  box-sizing: border-box;
  padding: 0;
  border: 0;
  margin: 0;
  vertical-align: baseline;

  h2 {
    display: inline;
    font-size: 26px;
    letter-spacing: -0.009em;
    line-height: 1.4;
    font-weight: 700;
    word-break: keep-all;
    margin-top: 0;
    margin-bottom: 0;
    @media (max-width: 767px) {
      font-size: 18px;
    }
  }
  

  h4 {
    margin-top: 0.25rem;
    font-weight: 500;
    color: #98a8b9;
    font-size: 20px;
    letter-spacing: -0.009em;
    line-height: 1.6;
    margin-bottom: 0;
    @media (max-width: 767px) {
      font-size: 16px;
    }
  }
  a {
    color: #98a8b9;
    &:hover {
      text-decoration: underline;
    }
`;

export const Recommended = styled.div`
  position: relative;
  background-color: #eeebff;
  color: #3684ff;
  height: 25px;
  font-weight: 700;
  font-size: 14px;
  line-height: 1.5;
  display: inline-block;
  padding: 0.1875rem 0.5rem;
  margin: 0.25rem 0.25rem 0 0;
  border-radius: 0.25rem;
  vertical-align: top;

  &::after {
    content: '추천 코스';
  }

  @media (max-width: 767px) {
    &::after {
      content: '추천';
    }
  }
`;

export const MobileButtons = styled.div`
  display: none;
  margin-top: 1rem;
  @media (max-width: 1199px) {
    display: grid;
    margin-left: 5rem;
    grid-template-columns: minmax(0, 1fr) auto;
    column-gap: 0.5rem;
  }
  @media (max-width: 767px) {
    margin-left: 0;
  }
`;

export const ApplyButton = styled.div`
  cursor: pointer;
`;

export const ButtonWrapper = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;
  @media (max-width: 1199px) {
    display: flex;
    gap: 0.5rem;
  }
  button {
    width: 32px;
    height: 32px;
  }
`;

export const CourseInfoWrapper = styled.div`
  display: flex;
  border-radius: 0.75rem;
  flex-direction: column;
  row-gap: 1.5rem;
  margin-top: 1.5rem;
  padding: 1.5rem;
  word-break: keep-all;
  background-color: #f7f7fb;

  @media (max-width: 767px) {
    row-gap: 0.75rem;
    padding: 1.5rem 0;
    border-radius: 0;
    border-left: none;
    border-right: none;
    background-color: #fff;
  }
`;

export const CourseInfo = styled.div`
  display: grid;
  word-break: keep-all;
  row-gap: 1.5rem;
  grid-template-columns: 1fr 1fr;
  column-gap: 1.5rem;

  @media (max-width: 767px) {
    row-gap: 0.75rem;
  }
`;

export const CourseInfoItem = styled.div`
  display: grid;
  align-items: center;
  column-gap: 1rem;
  grid-template-columns: 5.5rem auto;
  @media (max-width: 767px) {
    row-gap: 0.25rem;
    align-items: flex-start;
    grid-template-columns: auto;
    grid-template-rows: max-content;
  }
`;

export const ItemName = styled.div`
  font-weight: 400;
  font-size: 0.875rem;
  color: #7d8893;

  @media (max-width: 767px) {
    font-size: 0.75rem;
  }
`;

export const ItemContent = styled.div`
  font-weight: 700;
  font-size: 0.875rem;
  color: #44576c;

  @media (max-width: 767px) {
    font-size: 0.9375rem;
  }
`;

export const CourseTags = styled.div`
  margin: 14px 0 16px 0;
`;

export const TagItem = styled.li`
  font-size: 14px;
  line-height: 1.5;
  display: inline-block;
  padding: 0.1875rem 0.5rem;
  margin: 0.25rem 0.25rem 0 0;
  border-radius: 0.25rem;
  font-weight: 700;
  vertical-align: top;
`;

export const TagItemSide = styled.li`
  font-size: 12px;
  line-height: 1.5;
  display: inline-block;
  padding: 0.1875rem 0.5rem;
  margin: 0.25rem 0.25rem 0 0;
  border-radius: 0.25rem;
  font-weight: 500;
  vertical-align: top;
`;

export const CourseDetailImg = styled.div`
  margin-top: 2.5rem;

  & img {
    max-width: 100% !important;
  }
`;

export const Side = styled.div`
  position: sticky;
  top: 2rem;
  height: fit-content;
  padding: 1.5rem;
  border: 1px solid #f2f2f2;
  border-radius: 1rem;
  background: #fff;

  @media (max-width: 1200px) {
    display: none;
  }
`;

export const SideContent = styled.section`
  display: block;
  box-sizing: border-box;
  padding: 0;
  border: 0;
  margin: 0;
  vertical-align: baseline;
`;

export const SideTop = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
`;
export const SideDescription = styled.div`
  margin-top: 0.75rem;

  h4 {
    word-break: keep-all;
    font-weight: 700;
    font-size: 1.125rem;
    line-height: 150%;
    color: #263747;
    letter-spacing: -0.009em;
  }
  h5 {
    margin-top: 0.125rem;
    font-size: 16px;
    letter-spacing: -0.009em;
    line-height: 1.6;
    margin-bottom: 0;
  }
  a {
    font-weight: 400;
    font-size: 1rem;
    line-height: 150%;
    color: #98a8b9;
    &:hover {
      text-decoration: underline;
    }
  }
`;
