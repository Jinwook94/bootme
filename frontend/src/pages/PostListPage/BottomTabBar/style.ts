import styled from 'styled-components';
import Search from 'antd/es/input/Search';

export const BottomTapBarWrapper = styled.div`
  display: none;

  @media (max-width: 640px) {
    position: fixed;
    bottom: 0;
    display: flex;
    justify-content: space-around;
    align-items: center;
    background: #ffffff;
    color: rgb(34, 34, 34);
    width: 100%;
    height: 50px;
    border-top: 3px solid rgb(242, 242, 242);
  }
`;

export const TapBarItem = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
  height: 100%;
`;

export const TapBarItemIcon = styled.div`
  width: 100%;
  height: 100%;
  position: relative;
  margin-top: 6px;
  span {
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    align-items: center;
  }
  svg {
    fill: #878a8c;
    width: 22px;
    height: 100%;
  }
`;

export const TapBarItemText = styled.div`
  color: #878a8c;
  font-size: 8px;
  padding-bottom: 6px;
`;

export const SearchBarWrapper = styled.div`
  position: fixed;
  top: 834px;
  left: 51px;
  width: 100%;
  height: 50px;
  z-index: 100;
`;

export const StyledSearchBar = styled(Search)`
  & input {
    font-family: 'Noto Sans KR' !important;
    font-size: 14px !important;
    border-radius: 4px;
  }
  width: 300px !important;
`;
