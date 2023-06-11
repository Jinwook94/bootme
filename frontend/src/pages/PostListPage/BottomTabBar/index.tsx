import {
  BottomTapBarWrapper,
  SearchBarWrapper,
  StyledSearchBar,
  TapBarItem,
  TapBarItemIcon,
  TapBarItemText,
} from './style';
import { BulbOutlined, CommentOutlined, HomeOutlined, PlusOutlined, SearchOutlined } from '@ant-design/icons';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../../constants/snackbar';
import PATH from '../../../constants/path';
import React, { useState } from 'react';
import { useNavigation } from '../../../hooks/useNavigation';
import { useSnackbar } from '../../../hooks/useSnackbar';
import { usePost } from '../../../hooks/usePost';

const BottomTapBar = () => {
  const { goToPage } = useNavigation();
  const { showSnackbar } = useSnackbar();
  const { onSearch } = usePost();
  const [isSearchingMobile, setIsSearchingMobile] = useState(false);

  return (
    <BottomTapBarWrapper>
      <TapBarItem onClick={() => goToPage(PATH.POST.LIST)}>
        <TapBarItemIcon>
          <HomeOutlined />
        </TapBarItemIcon>
        <TapBarItemText>커뮤니티 홈</TapBarItemText>
      </TapBarItem>
      <TapBarItem onClick={() => showSnackbar(SNACKBAR_MESSAGE.WORK_IN_PROGRESS, EXCLAMATION)}>
        <TapBarItemIcon>
          <BulbOutlined />
        </TapBarItemIcon>
        <TapBarItemText>디스커버리</TapBarItemText>
      </TapBarItem>
      <TapBarItem onClick={() => goToPage(PATH.POST.WRITE)}>
        <TapBarItemIcon>
          <PlusOutlined />
        </TapBarItemIcon>
        <TapBarItemText>글쓰기</TapBarItemText>
      </TapBarItem>
      <TapBarItem onClick={() => showSnackbar(SNACKBAR_MESSAGE.WORK_IN_PROGRESS, EXCLAMATION)}>
        <TapBarItemIcon>
          <CommentOutlined />
        </TapBarItemIcon>
        <TapBarItemText>채팅</TapBarItemText>
      </TapBarItem>
      <TapBarItem>
        {isSearchingMobile ? (
          <>
            <SearchBarWrapper>
              <StyledSearchBar placeholder="게시글 검색" onSearch={onSearch} />
            </SearchBarWrapper>
            <TapBarItemIcon onClick={() => setIsSearchingMobile(prev => !prev)}>
              <SearchOutlined />
            </TapBarItemIcon>
            <TapBarItemText onClick={() => setIsSearchingMobile(prev => !prev)}>검색</TapBarItemText>
          </>
        ) : (
          <>
            <TapBarItemIcon onClick={() => setIsSearchingMobile(prev => !prev)}>
              <SearchOutlined />
            </TapBarItemIcon>
            <TapBarItemText onClick={() => setIsSearchingMobile(prev => !prev)}>검색</TapBarItemText>
          </>
        )}
      </TapBarItem>
    </BottomTapBarWrapper>
  );
};

export default BottomTapBar;
