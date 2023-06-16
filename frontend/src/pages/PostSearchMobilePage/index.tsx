import React from 'react';
import { Wrapper, SearchBarWrapper, StyledSearchBar, ArrowWrapper } from './style';
import PATH from '../../constants/path';
import {
  ArrowLeftOutlined,
  BulbOutlined,
  CommentOutlined,
  HomeOutlined,
  PlusOutlined,
  SearchOutlined,
} from '@ant-design/icons';
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
import { Link, useNavigate } from 'react-router-dom';

const PostSearchMobilePage = () => {
  const { showSnackbar } = useSnackbar();
  const navigate = useNavigate();

  const handleSearch = (value: string) => {
    navigate(`${PATH.POST.LIST}?search=${value}`);
  };

  return (
    <>
      <Wrapper>
        <SearchBarWrapper>
          <Link to={PATH.POST.LIST}>
            <ArrowWrapper>
              <ArrowLeftOutlined />
            </ArrowWrapper>
          </Link>
          <StyledSearchBar autoFocus placeholder="게시글 검색" onSearch={handleSearch} />
        </SearchBarWrapper>
      </Wrapper>
      <BottomTapBarWrapper>
        <Link to={PATH.POST.LIST} style={{ width: '100%', height: '100%' }}>
          <TapBarItem>
            <TapBarItemIcon>
              <HomeOutlined />
            </TapBarItemIcon>
            <TapBarItemText>커뮤니티 홈</TapBarItemText>
          </TapBarItem>
        </Link>
        <a style={{ width: '100%', height: '100%', cursor: 'pointer' }}>
          <TapBarItem onClick={() => showSnackbar(SNACKBAR_MESSAGE.WORK_IN_PROGRESS, EXCLAMATION)}>
            <TapBarItemIcon>
              <BulbOutlined />
            </TapBarItemIcon>
            <TapBarItemText>디스커버리</TapBarItemText>
          </TapBarItem>
        </a>
        <Link to={PATH.POST.WRITE} style={{ width: '100%', height: '100%' }}>
          <TapBarItem>
            <TapBarItemIcon>
              <PlusOutlined />
            </TapBarItemIcon>
            <TapBarItemText>글쓰기</TapBarItemText>
          </TapBarItem>
        </Link>
        <a style={{ width: '100%', height: '100%', cursor: 'pointer' }}>
          <TapBarItem onClick={() => showSnackbar(SNACKBAR_MESSAGE.WORK_IN_PROGRESS, EXCLAMATION)}>
            <TapBarItemIcon>
              <CommentOutlined />
            </TapBarItemIcon>
            <TapBarItemText>채팅</TapBarItemText>
          </TapBarItem>
        </a>
        <a style={{ width: '100%', height: '100%', cursor: 'pointer' }}>
          <TapBarItem>
            <ColoredIcon>
              <SearchOutlined />
            </ColoredIcon>
            <ColoredText>검색</ColoredText>
          </TapBarItem>
        </a>
      </BottomTapBarWrapper>
    </>
  );
};

export default PostSearchMobilePage;
