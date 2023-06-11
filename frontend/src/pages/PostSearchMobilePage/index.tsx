import React, { useEffect, useRef } from 'react';
import { Wrapper, SearchBarWrapper, StyledSearchBar, ArrowWrapper } from './style';
import { useNavigation } from '../../hooks/useNavigation';
import PATH from '../../constants/path';
import {
  ArrowLeftOutlined,
  BulbOutlined,
  CommentOutlined,
  HomeOutlined,
  PlusOutlined,
  SearchOutlined,
} from '@ant-design/icons';
import { InputRef } from 'antd';
import {
  BottomTapBarWrapper,
  ColoredIcon,
  ColoredText,
  TapBarItem,
  TapBarItemIcon,
  TapBarItemText,
} from '../PostListPage/BottomTabBar/style';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../constants/snackbar';
import { useSnackbar } from '../../hooks/useSnackbar';

const PostSearchMobilePage = () => {
  const { showSnackbar } = useSnackbar();
  const { goToPage } = useNavigation();
  const inputRef = useRef<InputRef>(null);

  const handleSearch = (value: string) => {
    goToPage(`${PATH.POST.LIST}?search=${value}`);
  };

  useEffect(() => {
    if (inputRef.current && inputRef.current.input) {
      inputRef.current.input.focus();
      const event = new Event('touchend');
      inputRef.current.input.dispatchEvent(event);
    }
  }, []);

  return (
    <>
      <Wrapper>
        <SearchBarWrapper>
          <ArrowWrapper onClick={() => goToPage(PATH.POST.LIST)}>
            <ArrowLeftOutlined />
          </ArrowWrapper>
          <StyledSearchBar ref={inputRef} placeholder="게시글 검색" onSearch={handleSearch} />
        </SearchBarWrapper>
      </Wrapper>
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
          <ColoredIcon>
            <SearchOutlined />
          </ColoredIcon>
          <ColoredText>검색</ColoredText>
        </TapBarItem>
      </BottomTapBarWrapper>
    </>
  );
};

export default PostSearchMobilePage;
