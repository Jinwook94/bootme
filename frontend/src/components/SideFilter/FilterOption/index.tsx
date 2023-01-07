import { BoxWrapper, Checkbox, ListItem, Option, Wrapper } from './style';
import React, { useEffect, useState } from 'react';
import { useFilters } from '../../../hooks/useFilters';

export const FilterOption = ({ filterName, filterOption, isReset, borderTop }: FilterOptionProps) => {
  const [isChecked, setIsChecked] = useState(false);
  const { addFilter, removeFilter } = useFilters();

  const handleListItemClick = () => {
    setIsChecked(!isChecked);
    isChecked ? removeFilter(filterName, filterOption) : addFilter(filterName, filterOption);
  };

  useEffect(() => {
    if (isReset) {
      setIsChecked(false);
    }
  }, [isReset]);

  return (
    <ListItem onClick={handleListItemClick} borderTop={borderTop}>
      <Wrapper>
        <BoxWrapper>
          <Checkbox
            checked={isChecked}
            onChange={handleListItemClick}
            style={{ appearance: isChecked ? 'auto' : 'none' }}
          />
        </BoxWrapper>
        <Option>{filterOption}</Option>
      </Wrapper>
    </ListItem>
  );
};

export interface FilterOptionProps {
  filterName: string;
  filterOption: string;
  isReset: boolean;
  borderTop?: boolean | undefined;
}

export default FilterOption;
