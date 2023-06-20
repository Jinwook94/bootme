import React, { useEffect, useState } from 'react';
import { useCourseFilters } from './useFilters';
import { COURSE_FILTERS, CourseFilterTypes } from '../constants/filters';

export const useRangeBar = (filterName: string, isReset: boolean) => {
  let Filter: CourseFilterTypes = {
    filterOptions: [],
    filterName: '',
    minValue: 0,
    maxValue: 0,
    step: 0,
    unit: '',
  };

  {
    filterName === COURSE_FILTERS.COST_TYPE.filterName ? (Filter = COURSE_FILTERS.COST_INPUT) : null;
    filterName === COURSE_FILTERS.PERIOD_INPUT.filterName ? (Filter = COURSE_FILTERS.PERIOD_INPUT) : null;
  }

  const [currentValue, setCurrentValue] = useState(Filter.maxValue);
  const { selectedFilters, clearAndAddFilter, clearFilterGroup } = useCourseFilters();
  const [isFreeSelected, setIsFreeSelected] = useState(false);

  const handleRangeChange = (value: number) => {
    setCurrentValue(value);
  };

  const handleRangeChangeEnd = (value: number) => {
    setCurrentValue(value);
    clearAndAddFilter(Filter.filterName, value.toString());
  };

  const handleInputChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const positiveIntegerRegEx = /^\d+$/;
    if (event.target.value === '') {
      setCurrentValue(0);
      clearAndAddFilter(Filter.filterName, '0');
    } else if (
      positiveIntegerRegEx.test(event.target.value) &&
      Number(event.target.value) <= (Filter.maxValue ?? 1000)
    ) {
      setCurrentValue(Number(event.target.value));
      clearAndAddFilter(Filter.filterName, event.target.value);
    } else {
      event.target.value = '';
    }
  };

  useEffect(() => {
    if (isReset) {
      setCurrentValue(Filter.maxValue);
    }
  }, [isReset]);

  // 무료, 무료 (국비) 체크박스 선택되면 -> 1. range bar, text input 값을 0으로 변경 2. 선택된 필터 중 비용 필터를 삭제
  useEffect(() => {
    const isFreeFilterSelected = selectedFilters.isFree?.includes(String(true)) ?? false;
    const isKdtFilterSelected = selectedFilters.isKdt?.includes(String(true)) ?? false;

    const isFreeSelected = isFreeFilterSelected || isKdtFilterSelected;
    setIsFreeSelected(isFreeSelected);

    if (
      isFreeSelected &&
      filterName === COURSE_FILTERS.COST_TYPE.filterName &&
      Array.isArray(selectedFilters.costInput) &&
      selectedFilters.costInput.length > 0
    ) {
      setCurrentValue(0);
      clearFilterGroup('costInput');
    }
  }, [selectedFilters]);

  // 무료 옵션 해제시 RangeBar 값 1000으로 설정
  useEffect(() => {
    if (!isFreeSelected && filterName === COURSE_FILTERS.COST_TYPE.filterName) {
      setCurrentValue(1000);
    }
  }, [isFreeSelected]);

  return { Filter, currentValue, handleRangeChange, handleRangeChangeEnd, handleInputChange };
};
