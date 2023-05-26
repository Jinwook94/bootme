import styled from 'styled-components';
import Search from 'antd/es/input/Search';

export const Wrapper = styled.div`
  display: block;
`;

export const SearchInput = styled.div`
  padding-bottom: 1rem;
`;

export const StyledSearch = styled(Search)`
  & input {
    font-family: 'Noto Sans KR' !important;
    font-size: 16px !important;
  }

  @media (max-width: 1200px) {
    width: 206px !important;
  }
`;

export const FilterReset = styled.div``;

export const ResetButton = styled.button`
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  font-size: 0.875rem;
  font-weight: 700;
  color: rgb(38, 55, 71);
  width: 100%;
  padding: 0.75rem;
  border: 0;
  background: none;
  cursor: pointer;
`;

export const FilterItemWrapper = styled.div`
  display: block;
`;
