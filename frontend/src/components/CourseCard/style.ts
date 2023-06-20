import styled from 'styled-components';
import FlexBox from '../@common/FlexBox';

export const Wrapper = styled.li`
  background: white;
  position: relative;
  display: flex;
  padding: 1.5rem;
  border-radius: 0.5rem;
  color: #263747;
  font-size: 0;

  @media (max-width: 575px) {
    padding: 1rem;
  }

  @media (max-width: 991px) {
    width: 100%;
    margin-left: 0;
  }

  @media (min-width: 991px) {
    &:nth-child(even) {
      margin-left: 1rem;
    }
  }
`;

export const ItemHeader = styled(FlexBox)`
  width: 4.5rem;
  height: 4.5rem;
  align-items: center;
  margin-right: 1.5rem;
  vertical-align: top;
  transition: 0s !important;

  @media (max-width: 575px) {
    width: 2.5rem;
    height: 2.5rem;
    margin-right: 1rem;
  }
`;

export const CompanyLogo = styled.img`
  width: 4.5rem;
  height: 4.5rem;
  margin: 0.375rem auto 0;
  border-radius: 0.25rem;
  vertical-align: middle;
  border-style: none;

  @media (max-width: 575px) {
    width: 2.5rem;
    height: 2.5rem;
  }
`;

export const ItemBody = styled.div`
  width: calc(100% - 6rem);
  display: inline-block;
  vertical-align: top;
  box-sizing: border-box;
  padding: 0;
  border: 0;
  margin: 0;
  font-size: 100%;

  @media (max-width: 767.98px) {
    width: 100%;
  }
`;

export const CourseTitleWrapper = styled(FlexBox)`
  align-items: center;
  width: 260px;
  font-size: 18px;

  @media (min-width: 991px) and (max-width: 1200px) {
    width: 200px;
    font-size: 14px;
  }

  @media (min-width: 476px) and (max-width: 990px) {
    width: 320px;
    font-size: 18px;
  }

  @media (max-width: 475px) {
    width: 215px;
    font-size: 14px;
  }
`;

export const CourseTitle = styled.h5`
  &:hover {
    text-decoration: underline;
  }
  transition-delay: initial;
  transition-duration: 0.08s;
  transition-property: all;
  transition-timing-function: ease-in-out;
  font-size: 18px;
  line-height: 1.5;
  font-weight: 500;
  letter-spacing: -0.009em;
  overflow: hidden;
  white-space: nowrap;
  text-overflow: ellipsis;
`;

export const CompanyNameWrapper = styled(FlexBox)`
  align-items: center;
`;

export const CompanyName = styled.a`
  &:hover {
    text-decoration: underline;
  }
  display: flex;
  align-items: center;
  margin-top: 0.5rem;
  color: #98a8b9;
  transition: 0s !important;
  line-height: 1.5;
  font-weight: 400;
  vertical-align: baseline;
  font-size: 14px;

  @media (max-width: 575px) {
    font-size: 13px;
    margin-top: 0.25rem;
  }

  @media (max-width: 767px) {
    font-size: 13px;
  }
`;

export const CourseInfo = styled.ul`
  padding: 0;
  margin-top: 0.5rem;
  list-style: none;
  vertical-align: baseline;
`;

export const ItemWrapper = styled.li`
  font-size: 14px;
  line-height: 1.5;
  display: inline-block;
  margin-right: 0.75rem;
  color: #98a8b9;
  vertical-align: top;
  transition: 0s !important;
  letter-spacing: -0.009em;
  list-style: none;

  @media (max-width: 360px) {
    font-size: 12.5px;
  }

  @media (min-width: 361px) and (max-width: 767px) {
    font-size: 13px;
  }
`;

export const CourseTags = styled.ul`
  position: relative;
  padding: 0;
  margin: 0.375rem 0 0 0;
  list-style: none;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;

  @media (max-width: 767px) {
    margin: 0.25rem 0 0 0;
  }
`;

export const TagItem = styled.li`
  font-size: 12px;
  line-height: 1.5;
  display: inline-block;
  padding: 0.1875rem 0.5rem;
  margin: 0.25rem 0.25rem 0 0;
  border-radius: 0.25rem;
  font-weight: 500;
  vertical-align: top;
`;

export const ThreeDotsWrapper = styled.div`
  position: absolute;
  top: 10px;
  right: 7px;

  svg {
    width: 22px;
    height: 100%;
    fill: #acacb2;
  }
`;

export const Bookmark = styled.button`
  position: absolute;
  top: 1.2rem;
  right: 1.2rem;
  display: inline-flex;
  align-items: center;
  justify-content: center;
  background-color: transparent;
  width: 2.25rem;
  height: 2.25rem;
  cursor: pointer;
  border-width: initial;
  border-style: none;
  border-color: initial;
  border-image: initial;
  margin: 0;

  svg {
    width: 1.5rem;
    height: 1.5rem;
    fill: rgb(178, 192, 204);
    transition-delay: initial;
    transition-duration: 0.08s;
    transition-property: all;
    transition-timing-function: ease-in-out;
    vertical-align: top;
    overflow: hidden;

    @media (max-width: 575px) {
      width: 1.3rem;
      height: 1.3rem;
    }
  }
  @media (max-width: 575px) {
    top: 0.6rem;
    right: 1rem;
  }

  @media (min-width: 576px) and (max-width: 991px) {
    top: 1.2rem;
    right: 1.2rem;
  }

  @media (min-width: 992px) and (max-width: 1200px) {
    top: 1.3rem;
    right: 0.3rem;
  }
`;
