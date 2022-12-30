import { BoxWrapper, Checkbox, ListItem, Option, Wrapper } from './style';
import React, { useState } from 'react';

export const FilterOption = ({ filterOption }: FilterOptionProps) => {
  const [isChecked, setIsChecked] = useState(false);

  function handleChange(event: React.ChangeEvent<HTMLInputElement>) {
    setIsChecked(event.target.checked);
  }

  return (
    <ListItem>
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
}

export default FilterOption;
