import { BottomTapBarWrapper, TapBarItem, TapBarItemIcon, TapBarItemText } from './style';
import { BulbOutlined, CommentOutlined, HomeFilled, PlusOutlined, SearchOutlined } from '@ant-design/icons';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../../constants/snackbar';
import PATH from '../../../constants/path';
import React from 'react';
import { useSnackbar } from '../../../hooks/useSnackbar';
import { Link } from 'react-router-dom';

const BottomTapBar = () => {
  const { showSnackbar } = useSnackbar();
  return (
    <BottomTapBarWrapper>
      <Link to={PATH.POST.LIST}>
        <TapBarItem>
          <TapBarItemIcon>
            {/*<HomeOutlined />*/}
            <HomeFilled />
          </TapBarItemIcon>
          <TapBarItemText>커뮤니티 홈</TapBarItemText>
        </TapBarItem>
      </Link>
      <TapBarItem onClick={() => showSnackbar(SNACKBAR_MESSAGE.WORK_IN_PROGRESS, EXCLAMATION)}>
        <TapBarItemIcon>
          <BulbOutlined />
        </TapBarItemIcon>
        <TapBarItemText>디스커버리</TapBarItemText>
      </TapBarItem>
      <Link to={PATH.POST.WRITE}>
        <TapBarItem>
          <TapBarItemIcon>
            <PlusOutlined />
          </TapBarItemIcon>
          <TapBarItemText>글쓰기</TapBarItemText>
        </TapBarItem>
      </Link>
      <TapBarItem onClick={() => showSnackbar(SNACKBAR_MESSAGE.WORK_IN_PROGRESS, EXCLAMATION)}>
        <TapBarItemIcon>
          <CommentOutlined />
        </TapBarItemIcon>
        <TapBarItemText>채팅</TapBarItemText>
      </TapBarItem>
      <Link to={PATH.POST.SEARCH}>
        <TapBarItem>
          <TapBarItemIcon>
            <SearchOutlined />
          </TapBarItemIcon>
          <TapBarItemText>검색</TapBarItemText>
        </TapBarItem>
      </Link>
    </BottomTapBarWrapper>
  );
};

export default BottomTapBar;
