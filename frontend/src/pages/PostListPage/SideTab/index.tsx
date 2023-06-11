import {
  SideTabWrapper1,
  SideTabWrapper2,
  TopicTitle,
  TopicText,
  ResetIconWrapper,
  TopicWrapper,
  Topic,
  TopicIconWrapper,
  TopicIcon,
  TopicName,
  WriteButton,
} from './style';
import { Button } from 'antd';
import { 개발질문, 부트캠프질문, 자유 } from '../../../constants/filters';
import PATH from '../../../constants/path';
import { ResetIcon } from '../../../constants/icons';
import React from 'react';
import { useNavigation } from '../../../hooks/useNavigation';
import { usePost } from '../../../hooks/usePost';
import { usePostFilters } from '../../../hooks/useFilters';

const SideTab = () => {
  const { goToPage } = useNavigation();
  const { sortOption } = usePost();
  const { selectedFilters } = usePostFilters();
  const handleTopicReset = () => {
    goToPage(PATH.POST.LIST);
  };

  return (
    <SideTabWrapper1>
      <SideTabWrapper2>
        <TopicTitle>
          <TopicText>토픽</TopicText>
          <ResetIconWrapper onClick={handleTopicReset}>
            <ResetIcon />
          </ResetIconWrapper>
        </TopicTitle>
        <TopicWrapper>
          <Topic onClick={() => goToPage(`${PATH.POST.LIST}?sort=${sortOption}&topic=${자유}`)}>
            <TopicIconWrapper>
              <TopicIcon
                loading="lazy"
                srcSet="https://ph-files.imgix.net/5266e8bd-bfca-4fd5-af60-7609318ee3d5.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max&amp;dpr=1 1x, https://ph-files.imgix.net/5266e8bd-bfca-4fd5-af60-7609318ee3d5.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max&amp;dpr=2 2x, https://ph-files.imgix.net/5266e8bd-bfca-4fd5-af60-7609318ee3d5.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max&amp;dpr=3 3x"
                src="https://ph-files.imgix.net/5266e8bd-bfca-4fd5-af60-7609318ee3d5.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max"
              />
            </TopicIconWrapper>
            <TopicName
              style={{ color: selectedFilters.topic?.includes(자유) ? 'rgb(0, 132, 255)' : 'rgb(34, 34, 34)' }}
            >
              {자유}
            </TopicName>
          </Topic>
          <Topic onClick={() => goToPage(`${PATH.POST.LIST}?sort=${sortOption}&topic=${부트캠프질문}`)}>
            <TopicIconWrapper>
              <TopicIcon
                loading="lazy"
                srcSet="https://ph-files.imgix.net/5266e8bd-bfca-4fd5-af60-7609318ee3d5.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max&amp;dpr=1 1x, https://ph-files.imgix.net/5266e8bd-bfca-4fd5-af60-7609318ee3d5.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max&amp;dpr=2 2x, https://ph-files.imgix.net/5266e8bd-bfca-4fd5-af60-7609318ee3d5.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max&amp;dpr=3 3x"
                src="https://ph-files.imgix.net/5266e8bd-bfca-4fd5-af60-7609318ee3d5.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max"
              />
            </TopicIconWrapper>
            <TopicName
              style={{
                color: selectedFilters.topic?.includes(부트캠프질문) ? 'rgb(0, 132, 255)' : 'rgb(34, 34, 34)',
              }}
            >
              {부트캠프질문}
            </TopicName>
          </Topic>
          <Topic onClick={() => goToPage(`${PATH.POST.LIST}?sort=${sortOption}&topic=${개발질문}`)}>
            <TopicIconWrapper>
              <TopicIcon
                loading="lazy"
                srcSet="https://ph-files.imgix.net/575f4ca9-da9c-41c5-8f14-18c88e427ced.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max&amp;dpr=1 1x, https://ph-files.imgix.net/575f4ca9-da9c-41c5-8f14-18c88e427ced.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max&amp;dpr=2 2x, https://ph-files.imgix.net/575f4ca9-da9c-41c5-8f14-18c88e427ced.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max&amp;dpr=3 3x"
                src="https://ph-files.imgix.net/575f4ca9-da9c-41c5-8f14-18c88e427ced.png?auto=compress&amp;codec=mozjpeg&amp;cs=strip&amp;auto=format&amp;w=36&amp;h=36&amp;fit=max"
              />
            </TopicIconWrapper>
            <TopicName
              style={{
                color: selectedFilters.topic?.includes(개발질문) ? 'rgb(0, 132, 255)' : 'rgb(34, 34, 34)',
              }}
            >
              {개발질문}
            </TopicName>
          </Topic>
        </TopicWrapper>
      </SideTabWrapper2>
      <WriteButton onClick={() => goToPage(PATH.POST.WRITE)}>
        <Button type="primary" block style={{ fontSize: '16px', fontWeight: '700', lineHeight: 'normal' }}>
          글 작성하기
        </Button>
      </WriteButton>
    </SideTabWrapper1>
  );
};

export default SideTab;
