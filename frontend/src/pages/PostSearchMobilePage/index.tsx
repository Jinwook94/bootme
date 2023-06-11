import React from 'react';
import { Wrapper, SearchBarWrapper, StyledSearchBar, ArrowWrapper } from './style';
import { useNavigation } from '../../hooks/useNavigation';
import PATH from '../../constants/path';
import { ArrowLeftOutlined } from '@ant-design/icons';

const PostSearchMobilePage = () => {
  const { goToPage } = useNavigation();

  const handleSearch = (value: string) => {
    goToPage(`${PATH.POST.LIST}?search=${value}`);
  };

  return (
    <Wrapper>
      <SearchBarWrapper>
        <ArrowWrapper onClick={() => goToPage(PATH.POST.LIST)}>
          <ArrowLeftOutlined />
        </ArrowWrapper>
        <StyledSearchBar autoFocus placeholder="게시글 검색" onSearch={handleSearch} />
      </SearchBarWrapper>
    </Wrapper>
  );
};

export default PostSearchMobilePage;
