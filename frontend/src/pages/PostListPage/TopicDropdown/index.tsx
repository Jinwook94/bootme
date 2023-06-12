import { Dropdown, MenuProps } from 'antd';
import { CaretIconWrapper, StyledTopicButton } from '../style';
import { SmileOutlined, UserOutlined } from '@ant-design/icons';
import { CaretDownIcon } from '../../../constants/icons';
import React, { useEffect, useState } from 'react';
import { 개발질문, 부트캠프질문, 자유, 전체 } from '../../../constants/filters';
import PATH from '../../../constants/path';
import { useNavigation } from '../../../hooks/useNavigation';
import { usePostFilters } from '../../../hooks/useFilters';
import { usePost } from '../../../hooks/usePost';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCode, faComments, faGlobe, faQuestion, faRocket } from '@fortawesome/free-solid-svg-icons';

const TopicDropdown = () => {
  const { goToPage } = useNavigation();
  const { selectedFilters } = usePostFilters();
  const { sortOption } = usePost();
  const [currentTopic, setCurrentTopic] = useState('');

  useEffect(() => {
    setCurrentTopic(selectedFilters?.topic ? selectedFilters?.topic[0] : 전체);
  }, [selectedFilters]);

  const handleTopicSelect: MenuProps['onClick'] = e => {
    {
      e.key === 전체
        ? goToPage(`${PATH.POST.LIST}?sort=${sortOption}`)
        : goToPage(`${PATH.POST.LIST}?sort=${sortOption}&topic=${e.key}`);
    }
  };

  const items: MenuProps['items'] = [
    {
      label: 전체,
      key: 전체,
      icon: <FontAwesomeIcon icon={faGlobe} />,
    },
    {
      label: 자유,
      key: 자유,
      icon: <FontAwesomeIcon icon={faComments} />,
    },
    {
      label: 부트캠프질문,
      key: 부트캠프질문,
      icon: <FontAwesomeIcon icon={faRocket} />,
    },
    {
      label: 개발질문,
      key: 개발질문,
      icon: <FontAwesomeIcon icon={faCode} />,
    },
  ];

  const topicProps = {
    items,
    selectable: true,
    defaultSelectedKeys: [currentTopic],
    onClick: handleTopicSelect,
  };

  return (
    <Dropdown menu={topicProps} trigger={['click']}>
      <StyledTopicButton>
        {currentTopic === '전체' ? (
          <FontAwesomeIcon icon={faGlobe} />
        ) : currentTopic === 자유 ? (
          <SmileOutlined />
        ) : currentTopic === 부트캠프질문 ? (
          <FontAwesomeIcon icon={faQuestion} />
        ) : currentTopic === 개발질문 ? (
          <FontAwesomeIcon icon={faCode} />
        ) : (
          <UserOutlined />
        )}
        {`토픽: ${currentTopic}`}
        <CaretIconWrapper>
          <CaretDownIcon />
        </CaretIconWrapper>
      </StyledTopicButton>
    </Dropdown>
  );
};

export default TopicDropdown;
