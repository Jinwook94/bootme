import { BottomTapBarWrapper, TapBarItem, TapBarItemIcon, TapBarItemText } from './style';
import { BulbOutlined, CommentOutlined, HomeFilled, PlusOutlined, SearchOutlined } from '@ant-design/icons';
import SNACKBAR_MESSAGE, { EXCLAMATION } from '../../../constants/snackbar';
import PATH from '../../../constants/path';
import React from 'react';
import { useNavigation } from '../../../hooks/useNavigation';
import { useSnackbar } from '../../../hooks/useSnackbar';

const BottomTapBar = () => {
  const { goToPage } = useNavigation();
  const { showSnackbar } = useSnackbar();
  return (
    <BottomTapBarWrapper>
      <TapBarItem onClick={() => goToPage(PATH.POST.LIST)}>
        <TapBarItemIcon>
          {/*<HomeOutlined />*/}
          <HomeFilled />
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
      <TapBarItem onClick={() => goToPage(PATH.POST.SEARCH)}>
        <TapBarItemIcon>
          <SearchOutlined />
        </TapBarItemIcon>
        <TapBarItemText>검색</TapBarItemText>
      </TapBarItem>
    </BottomTapBarWrapper>
  );
};

export default BottomTapBar;
