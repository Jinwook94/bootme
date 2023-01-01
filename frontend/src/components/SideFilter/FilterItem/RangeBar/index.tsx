import React, { useState } from 'react';
import { Bar, Input, InputWrapper, Unit, Wrapper1, Wrapper2 } from './style';

export const RangeBar = ({ filterName }: RangeBarProps) => {
  let Filter = {
    minValue: 0,
    initialState: 0,
    step: 0,
    unit: '',
  };

  const CostFilter = {
    minValue: 0,
    initialState: 1000,
    step: 50,
    unit: '만원 이하',
  };
  const PeriodFilter = {
    minValue: 1,
    initialState: 12,
    step: 1,
    unit: '개월 이하',
  };

  {
    filterName === '비용' ? (Filter = CostFilter) : null;
    filterName === '수강 기간' ? (Filter = PeriodFilter) : null;
  }

  const [currentValue, setCurrentValue] = useState(Filter.initialState);

  const handleRangeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentValue(Number(event.target.value));
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentValue(Number(event.target.value));
  };

  return (
    <Wrapper1>
      <Bar
        value={currentValue}
        min={Filter.minValue}
        max={Filter.initialState}
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
}
