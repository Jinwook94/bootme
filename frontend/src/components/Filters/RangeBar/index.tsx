import React from 'react';
import { Wrapper, BarWrapper, Bar, InputWrapper1, InputWrapper2, Input, Unit, CustomRangeBar } from './style';
import { useRangeBar } from '../../../hooks/useRangeBar';

export const RangeBar = ({ filterType, filterName, isReset }: RangeBarProps) => {
  const { Filter, currentValue, handleRangeChange, handleInputChange } = useRangeBar(filterName, isReset);

  return (
    <Wrapper>
      <BarWrapper filterType={filterType}>
        <CustomRangeBar>
          <Bar
            value={currentValue}
            min={Filter.minValue}
            max={Filter.maxValue}
            step={Filter.step}
            onChange={handleRangeChange}
          />
        </CustomRangeBar>
      </BarWrapper>
      <InputWrapper1>
        <InputWrapper2>
          <Input value={currentValue} onChange={handleInputChange} />
        </InputWrapper2>
        <Unit>{Filter.unit}</Unit>
      </InputWrapper1>
    </Wrapper>
  );
};

interface RangeBarProps {
  filterType: string;
  filterName: string;
  isReset: boolean;
}

export default RangeBar;
