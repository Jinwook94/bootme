import { BoxWrapper, Checkbox, ListItem, Option, Wrapper } from './style';
import React, { useState } from 'react';
import { useFilters } from '../../../hooks/useFilters';

export const FilterOption = ({ filterOption, borderTop }: FilterOptionProps) => {
  const [isChecked, setIsChecked] = useState(false);
  const { addFilter, removeFilter } = useFilters();

  function handleChange(event: React.ChangeEvent<HTMLInputElement>) {
    setIsChecked(event.target.checked);
  }
  function handleListItemClick() {
    setIsChecked(!isChecked);
    isChecked ? removeFilter(filterOption) : addFilter(filterOption);
  }

  return (
    <ListItem borderTop={borderTop} onClick={handleListItemClick}>
      <Wrapper>
        <BoxWrapper>
          <Checkbox checked={isChecked} onChange={handleChange} style={{ appearance: isChecked ? 'auto' : 'none' }} />
        </BoxWrapper>
        <Option>{filterOption}</Option>
      </Wrapper>
    </ListItem>
  );
};

export interface FilterOptionProps {
  filterOption: string;
  borderTop: number | string;
}

export default FilterOption;
