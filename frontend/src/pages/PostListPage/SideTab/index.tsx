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
import { Link } from 'react-router-dom';

const SideTab = () => {
  const { sortOption } = usePost();
  const { selectedFilters } = usePostFilters();

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
      <Link to={PATH.POST.WRITE}>
        <WriteButton>
          <Button type="primary" block style={{ fontSize: '16px', fontWeight: '700', lineHeight: 'normal' }}>
            글 작성하기
          </Button>
        </WriteButton>
      </Link>
    </SideTabWrapper1>
  );
};

export default SideTab;
