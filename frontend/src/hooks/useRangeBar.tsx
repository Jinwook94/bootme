import React, { useEffect, useState } from 'react';
import { useFilters } from './useFilters';

export const useRangeBar = (filterName: string, isReset: boolean) => {
  let Filter = {
    filterName: '',
    minValue: 0,
    maxValue: 0,
    step: 0,
    unit: '',
  };
  const CostFilter = {
    filterName: '비용',
    minValue: 0,
    maxValue: 1000,
    step: 50,
    unit: '만원 이하',
  };
  const PeriodFilter = {
    filterName: filterName,
    minValue: 0,
    maxValue: 12,
    step: 1,
    unit: '개월 이하',
  };

  {
    filterName === '수강 비용' ? (Filter = CostFilter) : null;
    filterName === '수강 기간' ? (Filter = PeriodFilter) : null;
  }

  const [currentValue, setCurrentValue] = useState(Filter.maxValue);
  const { selectedFilters, addFilter, removeBeforeAdd, removeFilter } = useFilters();
  const [, setIsFree] = useState(false);

  const handleRangeChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setCurrentValue(Number(event.target.value));
    removeBeforeAdd(Filter.filterName);
    addFilter(Filter.filterName, event.target.value);
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const positiveIntegerRegEx = /^\d+$/;
    if (event.target.value === '') {
      setCurrentValue(0);
      removeBeforeAdd(Filter.filterName);
      addFilter(Filter.filterName, '0');
    } else if (positiveIntegerRegEx.test(event.target.value) && Number(event.target.value) <= Filter.maxValue) {
      setCurrentValue(Number(event.target.value));
      removeBeforeAdd(Filter.filterName);
      addFilter(Filter.filterName, event.target.value);
    } else {
      event.target.value = '';
    }
  };

  useEffect(() => {
    if (isReset) {
      setCurrentValue(Filter.maxValue);
    }
  }, [isReset]);

  return { Filter, currentValue, handleRangeChange, handleInputChange };
};
