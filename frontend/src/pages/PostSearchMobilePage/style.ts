import styled from 'styled-components';
import Search from 'antd/es/input/Search';

export const Wrapper = styled.div`
  position: fixed;
  top: 0;
  width: 100%;
  min-height: 80vw;
  z-index: 1;
  background-color: #ffffff;
`;

export const SearchBarWrapper = styled.div`
  position: fixed;
  top: 0;
  width: 100%;
  z-index: 100;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 12px;
  padding-right: 12px;
`;

export const ArrowWrapper = styled.div`
  margin: 0 1rem;
  cursor: pointer;

  svg {
    width: 18px !important;
    height: 18px !important;
  }
`;

export const StyledSearchBar = styled(Search)`
  & input {
    font-family: 'Noto Sans KR' !important;
    height: 40px;
    color: rgb(28, 28, 28);
    font-size: 14px !important;
    border-radius: 4px;
    background-color: rgb(246, 247, 248);
  }
  & button {
    width: 40px !important;
    height: 40px !important;
  }
  & svg {
    width: 21px !important;
    height: 21px !important;
  }
  width: 100% !important;
`;
