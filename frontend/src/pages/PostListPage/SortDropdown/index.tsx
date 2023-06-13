import { Dropdown, MenuProps } from 'antd';
import React, { useEffect, useState } from 'react';
import { CaretIconBlue, StyledButton } from '../style';
import { CaretDownIcon, FireIcon, SparklesIcon } from '../../../constants/icons';
import { usePost } from '../../../hooks/usePost';
import { useNavigation } from '../../../hooks/useNavigation';
import PATH from '../../../constants/path';
import { usePostFilters } from '../../../hooks/useFilters';
import './style.css';

/* 사용 안함 */
const SortDropdown = () => {
  const { goToPage } = useNavigation();
  const { sortOption } = usePost();
  const { selectedFilters } = usePostFilters();
  const [currentTopic, setCurrentTopic] = useState('');

  useEffect(() => {
    setCurrentTopic(selectedFilters?.topic ? selectedFilters?.topic[0] : '전체');
  }, [selectedFilters]);

  const handleSortSelect: MenuProps['onClick'] = e => {
    {
      currentTopic === '전체'
        ? goToPage(`${PATH.POST.LIST}?sort=${e.key}`)
        : goToPage(`${PATH.POST.LIST}?sort=${e.key}&topic=${currentTopic}`);
    }
  };

  const items: MenuProps['items'] = [
    {
      label: '인기글',
      key: 'views',
      icon: <FireIcon />,
    },
    {
      label: '최신글',
      key: 'newest',
      icon: <SparklesIcon />,
    },
  ];

  const sortProps = {
    items,
    selectable: true,
    defaultSelectedKeys: [sortOption],
    onClick: handleSortSelect,
  };

  const getLabel = (option: string) => {
    switch (option) {
      case 'views':
        return '인기글';
      case 'newest':
        return '최신글';
      default:
        return '인기글';
    }
  };

  return (
    <Dropdown menu={sortProps} trigger={['click']}>
      <StyledButton>
        {sortOption === 'views' ? <FireIcon /> : <SparklesIcon />}
        {getLabel(sortOption)}
        <CaretIconBlue>
          <CaretDownIcon />
        </CaretIconBlue>
      </StyledButton>
    </Dropdown>
  );
};

export default SortDropdown;
