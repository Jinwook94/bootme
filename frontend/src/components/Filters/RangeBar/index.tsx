import React from 'react';
import { Bar, Input, InputWrapper, Unit, Wrapper1, Wrapper2 } from './style';
import { useRangeBar } from '../../../hooks/useRangeBar';

export const RangeBar = ({ filterName, isReset }: RangeBarProps) => {
  const { Filter, currentValue, handleRangeChange, handleInputChange } = useRangeBar(filterName, isReset);

  return (
    <Wrapper1>
      <Bar
        value={currentValue}
        min={Filter.minValue}
        max={Filter.maxValue}
        step={Filter.step}
        onChange={handleRangeChange}
      />
      <Wrapper2>
        <InputWrapper>
          <Input value={currentValue} onChange={handleInputChange} />
        </InputWrapper>
        <Unit>{Filter.unit}</Unit>
      </Wrapper2>
    </Wrapper1>
  );
};

interface RangeBarProps {
  filterName: string;
  isReset: boolean;
}

export default RangeBar;
