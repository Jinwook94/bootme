import { Dropdown, MenuProps } from 'antd';
import React, { useEffect, useState } from 'react';
import { CaretIconBlue, StyledButton } from '../style';
import { CaretDownIcon, FireIcon, SparklesIcon } from '../../../constants/icons';
import { usePost } from '../../../hooks/usePost';
import PATH from '../../../constants/path';
import { usePostFilters } from '../../../hooks/useFilters';
import './style.css';
import { useNavigate } from 'react-router-dom';
import { SORT_OPTION } from '../../../constants/others';

/* 사용 안함 */
const SortDropdown = () => {
  const navigate = useNavigate();
  const { sortOption } = usePost();
  const { selectedFilters } = usePostFilters();
  const [currentTopic, setCurrentTopic] = useState('');

  useEffect(() => {
    setCurrentTopic(selectedFilters?.topic ? selectedFilters?.topic[0] : '전체');
  }, [selectedFilters]);

  const handleSortSelect: MenuProps['onClick'] = e => {
    {
      currentTopic === '전체'
        ? navigate(`${PATH.POST.LIST}?sort=${e.key}`)
        : navigate(`${PATH.POST.LIST}?sort=${e.key}&topic=${currentTopic}`);
    }
  };

  const items: MenuProps['items'] = [
    {
      label: '인기글',
      key: SORT_OPTION.LIKES,
      icon: <FireIcon />,
    },
    {
      label: '최신글',
      key: SORT_OPTION.CREATED_AT,
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
      case SORT_OPTION.LIKES:
        return '인기글';
      case SORT_OPTION.CREATED_AT:
        return '최신글';
      default:
        return '인기글';
    }
  };

  return (
    <Dropdown menu={sortProps} trigger={['click']}>
      <StyledButton>
        {sortOption === SORT_OPTION.LIKES ? <FireIcon /> : <SparklesIcon />}
        {getLabel(sortOption)}
        <CaretIconBlue>
          <CaretDownIcon />
        </CaretIconBlue>
      </StyledButton>
    </Dropdown>
  );
};

export default SortDropdown;
