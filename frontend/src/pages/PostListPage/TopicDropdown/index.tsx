import { Dropdown, MenuProps } from 'antd';
import { CaretIconWrapper, StyledTopicButton } from '../style';
import { CaretDownIcon } from '../../../constants/icons';
import React, { useEffect, useState } from 'react';
import { 개발질문, 부트캠프질문, 자유, 전체 } from '../../../constants/filters';
import PATH from '../../../constants/path';
import { usePostFilters } from '../../../hooks/useFilters';
import { usePost } from '../../../hooks/usePost';
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faCode, faComments, faGlobe, faRocket } from '@fortawesome/free-solid-svg-icons';
import './style.css';
import { useNavigate } from 'react-router-dom';

const TopicDropdown = () => {
  const navigate = useNavigate();
  const { selectedFilters } = usePostFilters();
  const { sortOption } = usePost();
  const [currentTopic, setCurrentTopic] = useState('');

  useEffect(() => {
    setCurrentTopic(selectedFilters?.topic ? selectedFilters?.topic[0] : 전체);
  }, [selectedFilters]);

  const handleTopicSelect: MenuProps['onClick'] = e => {
    {
      e.key === 전체
        ? navigate(`${PATH.POST.LIST}?sort=${sortOption}`)
        : navigate(`${PATH.POST.LIST}?sort=${sortOption}&topic=${e.key}`);
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
        {currentTopic === 전체 ? (
          <FontAwesomeIcon icon={faGlobe} />
        ) : currentTopic === 자유 ? (
          <FontAwesomeIcon icon={faComments} />
        ) : currentTopic === 부트캠프질문 ? (
          <FontAwesomeIcon icon={faRocket} />
        ) : currentTopic === 개발질문 ? (
          <FontAwesomeIcon icon={faCode} />
        ) : (
          <FontAwesomeIcon icon={faGlobe} />
        )}
        {currentTopic ? currentTopic : 전체}
        <CaretIconWrapper>
          <CaretDownIcon />
        </CaretIconWrapper>
      </StyledTopicButton>
    </Dropdown>
  );
};

export default TopicDropdown;
