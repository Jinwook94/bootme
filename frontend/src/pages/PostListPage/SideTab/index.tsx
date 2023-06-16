import {
  SideTabWrapper1,
  SideTabWrapper2,
  TopicTitle,
  TopicText,
  ResetIconWrapper,
  TopicWrapper,
  Topic,
  TopicName,
  WriteButton,
  TopicEmoji,
} from './style';
import { Button } from 'antd';
import { 개발질문, 부트캠프질문, 자유 } from '../../../constants/filters';
import PATH from '../../../constants/path';
import { ResetIcon } from '../../../constants/icons';
import React from 'react';
import { usePost } from '../../../hooks/usePost';
import { usePostFilters } from '../../../hooks/useFilters';
import { Link, useNavigate } from 'react-router-dom';
import SNACKBAR_MESSAGE, { CHECK } from '../../../constants/snackbar';
import { useSnackbar } from '../../../hooks/useSnackbar';
import { useLogin } from '../../../hooks/useLogin';

const SideTab = () => {
  const { showSnackbar } = useSnackbar();
  const { isLogin } = useLogin();
  const navigate = useNavigate();
  const { sortOption } = usePost();
  const { selectedFilters } = usePostFilters();

  const handleCreatePost = () => {
    if (isLogin) {
      navigate(PATH.POST.WRITE);
    } else {
      showSnackbar(SNACKBAR_MESSAGE.NEED_LOGIN, CHECK);
    }
  };

  return (
    <SideTabWrapper1>
      <SideTabWrapper2>
        <TopicTitle>
          <TopicText>토픽</TopicText>
          <Link to={`${PATH.POST.LIST}?sort=${sortOption}&topic=`}>
            <ResetIconWrapper>
              <ResetIcon />
            </ResetIconWrapper>
          </Link>
        </TopicTitle>
        <TopicWrapper>
          <Link to={`${PATH.POST.LIST}?sort=${sortOption}&topic=${자유}`}>
            <Topic>
              <TopicEmoji>💬</TopicEmoji>
              <TopicName
                style={{ color: selectedFilters.topic?.includes(자유) ? 'rgb(0, 132, 255)' : 'rgb(34, 34, 34)' }}
              >
                {자유}
              </TopicName>
            </Topic>
          </Link>
          <Link to={`${PATH.POST.LIST}?sort=${sortOption}&topic=${부트캠프질문}`}>
            <Topic>
              <TopicEmoji>🚀</TopicEmoji>
              <TopicName
                style={{
                  color: selectedFilters.topic?.includes(부트캠프질문) ? 'rgb(0, 132, 255)' : 'rgb(34, 34, 34)',
                }}
              >
                {부트캠프질문}
              </TopicName>
            </Topic>
          </Link>
          <Link to={`${PATH.POST.LIST}?sort=${sortOption}&topic=${개발질문}`}>
            <Topic>
              <TopicEmoji>🧑‍💻</TopicEmoji>
              <TopicName
                style={{
                  color: selectedFilters.topic?.includes(개발질문) ? 'rgb(0, 132, 255)' : 'rgb(34, 34, 34)',
                }}
              >
                {개발질문}
              </TopicName>
            </Topic>
          </Link>
        </TopicWrapper>
      </SideTabWrapper2>
      <WriteButton onClick={handleCreatePost}>
        <Button type="primary" block style={{ fontSize: '16px', fontWeight: '700', lineHeight: 'normal' }}>
          글 작성하기
        </Button>
      </WriteButton>
    </SideTabWrapper1>
  );
};

export default SideTab;
