import styled from 'styled-components';
import Search from 'antd/es/input/Search';
import { Button } from 'antd';

export const CommunityPageLayout = styled.div`
  background: #dae0e6;
  max-width: 100%;
  box-sizing: border-box;
  display: flex;
  flex-direction: row;
  justify-content: center;
  margin: 0 auto;

  @media (min-width: 640px) {
    padding: 20px 24px;
  }
`;

export const BodyWrapper = styled.div`
  display: block;
  min-width: 0;
  width: 100%;
  margin-bottom: 71px;
  @media (min-width: 960px) {
    width: 640px;
    margin-bottom: 0;
  }
`;

export const MobileHeader = styled.div`
  background: linear-gradient(to right, rgba(255, 255, 255, 0.9), rgba(200, 234, 255, 1));
  display: flex;
  flex-direction: column;
  padding: 0.5rem 1rem 1.2rem 1rem;
  border-bottom: 2px solid rgb(242, 242, 242);
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

export const CreatePostWrapper = styled.div`
  background-color: #ffffff;
  border-radius: 4px;
  border: 1px solid #f2f2f2;
  display: flex;
  margin-bottom: 16px;
  padding: 8px;
  @media (max-width: 640px) {
    display: none;
  }
`;

export const ProfileWrapper1 = styled.div`
  border: 1px solid #edeff1;
  flex-basis: 38px;
  margin-right: 8px;
  border-radius: 50%;
  width: 38px;
  height: 38px;
`;

export const ProfileWrapper2 = styled.div`
  position: relative;
  height: 100%;
`;

export const ProfileWrapper3 = styled.div`
  border-radius: 50%;
  width: 38px;
  height: 38px;
  position: relative;
`;

export const ProfileWrapper4 = styled.div`
  width: 100%;
  height: 100%;
  border-radius: 50%;
`;

export const ProfileWrapper5 = styled.div`
  width: 100%;
  position: absolute;
  bottom: 0;
`;

export const ProfilePic = styled.img`
  width: 100%;
  height: auto;
  border-radius: 50%;
  transform-origin: bottom center;
  display: block;
  object-fit: cover;
`;

export const CreatePostInput = styled.input`
  background-color: #f6f7f8;
  border-radius: 4px;
  border: 1px solid #edeff1;
  box-shadow: none;
  box-sizing: border-box;
  display: block;
  flex-grow: 1;
  height: 38px;
  margin-right: 8px;
  outline: none;
  padding: 0 16px;

  color: #1c1c1c;
  font-size: 14px;
  font-weight: 400;
  line-height: 21px;
`;

export const SortAndFilterMobile = styled.div`
  position: sticky;
  top: 0;
  width: 100%;
  background-color: rgb(255, 255, 255);
  z-index: 100;
  border-bottom: 2px solid #f2f2f2;

  @media (min-width: 640px) {
    display: none;
  }
`;

export const SortAndFilterWrapper = styled.div`
  align-items: center;
  cursor: pointer;
  display: flex;
  padding: 0 8px;
  margin-right: 8px;

  @media (min-width: 640px) {
    display: none;
  }
`;

export const SortButton = styled.div`
  display: flex;
  align-items: center;
  font-size: 14px;
  font-weight: 700;
  background-color: rgb(255, 255, 255);
  line-height: 17px;
  min-height: 2.5rem;
  margin: 0;
  outline: none;

  &:hover {
    background-color: #efefef;
    border-radius: 4px;
  }
`;

export const TopicFilterButton = styled(SortButton)``;

export const SortSearchDesktop = styled.div`
  display: flex;
  justify-content: space-between;

  @media (max-width: 640px) {
    display: none;
  }
`;

export const SortWrapper = styled.div`
  background-color: #ffffff;
  height: 40px;
  border-radius: 4px;
  border: 1px solid #f2f2f2;
  align-items: center;
  cursor: pointer;
  display: flex;

  @media (max-width: 640px) {
    margin-bottom: 8px;
  }
`;

export const CaretIconWrapper = styled.div`
  svg {
    fill: rgb(135, 138, 140);
    width: 0.875rem;
    height: 0.875rem;
  }
`;

export const SortOption = styled.div`
  display: inline-flex;
  align-items: center;
  margin-right: 8px;
  position: relative;
  border: 1px solid transparent;
  min-height: unset;
  min-width: unset;
  padding: 4px 8px;
  border-radius: 20px;
  fill: #878a8c;

  &:hover {
    background-color: #efefef;
    border-radius: 20px;
  }

  @media (max-width: 640px) {
    display: none;
  }
`;

export const SortName = styled.span`
  color: rgb(135, 138, 140);
  font-weight: 500;
  line-height: 18px;
  padding-left: 5px;
`;

export const StyledButton = styled(Button)`
  color: #0079d3;
  font-size: 14px;
  font-weight: 500;
  line-height: 18px;
  height: 40px;
  border: none;
  display: flex;
  flex-direction: row;
  align-items: center;
`;

export const StyledTopicButton = styled(StyledButton)`
  color: rgb(135, 138, 140);
`;

export const CaretIconBlue = styled.div`
  svg {
    fill: #0079d3;
    width: 0.875rem;
    height: 0.875rem;
  }
`;

export const SearchWrapper = styled.div``;

export const StyledSearch = styled(Search)`
  & input {
    font-family: 'Noto Sans KR' !important;
    font-size: 16px !important;
    border-radius: 4px;
  }

  @media (max-width: 1200px) {
    width: 206px !important;
  }
`;

export const NoResultMessage = styled.div`
  color: rgb(34, 34, 34);
  font-size: 1rem;
  text-align: center;
  width: 100%;
  min-height: 50vw;
  padding: 2rem;
  background-color: #ffffff;
  margin-top: 12px;

  @media (max-width: 640px) {
    width: 100vw;
  }
`;

export const PostCardList = styled.div`
  min-height: 1000px;
  width: 100%;

  @media (max-width: 640px) {
    min-height: auto;
  }
`;
