import { BoxWrapper, Checkbox, ListItem, Option, Wrapper } from './style';
import React from 'react';
import { useCheckbox } from '../../../hooks/useCheckbox';

export const FilterOption = ({ filterName, filterOption, isReset, borderTop }: FilterOptionProps) => {
  const { isChecked, handleClick, resetFilter } = useCheckbox(filterName, filterOption, isReset);
  resetFilter();

  return (
    <ListItem onClick={handleClick} borderTop={borderTop}>
      <Wrapper>
        <BoxWrapper>
          <Checkbox checked={isChecked} onChange={handleClick} style={{ appearance: isChecked ? 'auto' : 'none' }} />
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
