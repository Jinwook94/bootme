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
      <Link to={PATH.POST.LIST} style={{ width: '100%', height: '100%' }}>
        <TapBarItem>
          <TapBarItemIcon>
            {/*<HomeOutlined />*/}
            <HomeFilled />
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
      <Link to={PATH.POST.SEARCH} style={{ width: '100%', height: '100%' }}>
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
