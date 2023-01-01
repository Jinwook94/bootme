import { BoxWrapper, Checkbox, ListItem, Option, Wrapper } from './style';
import React, { useState } from 'react';

export const FilterOption = ({ filterOption, borderTop }: FilterOptionProps) => {
  const [isChecked, setIsChecked] = useState(false);

  function handleChange(event: React.ChangeEvent<HTMLInputElement>) {
    setIsChecked(event.target.checked);
  }

  return (
    <ListItem borderTop={borderTop}>
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
