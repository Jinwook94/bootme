import {
  FilterTitle,
  CaretWrapper,
  Wrapper,
  FilterBodyWrapper,
  FilterBody,
  MoreButton,
  FilterOptionList,
  CostFilterOptionList,
  TestOptionList,
  CaretIconWrapper,
} from './style';
import FilterOption from '../FilterOption';
import React, { useState } from 'react';
import { RangeBar } from '../RangeBar';
import { CourseFilterTypes, SIDE_FILTER, COURSE_FILTERS } from '../../../constants/filters';
import { CaretDownIcon, CaretIcon, CaretUpIcon } from '../../../constants/icons';

const SideFilterItem = ({ filterName, filterOptions, isMore, isReset }: SideFilterItemProps) => {
  const [isOpen, setIsOpen] = useState(true);
  const [isMoreOpen, setIsMoreOpen] = useState(false);
  const [rotation, setRotation] = useState(0);

  function handleCaretClick() {
    setIsOpen(!isOpen);
    setRotation(rotation === 0 ? 180 : 0);
  }

  function handleMoreClick() {
    setIsMoreOpen(!isMoreOpen);
  }

  const renderFilterOptionList = () => {
    switch (filterName) {
      case '개발 분야':
        return (
          <FilterOptionList style={{ maxHeight: isMoreOpen ? '20rem' : '6.5rem' }}>
            {filterOptions?.map((filterOption: string, index) => (
              <FilterOption
                key={index}
                filterType={SIDE_FILTER}
                filterOption={filterOption}
                isReset={isReset}
                borderTop={index === COURSE_FILTERS.SUPER_CATEGORY.filterOptions.length}
              />
            ))}
          </FilterOptionList>
        );
      case '기술 스택':
        return (
          <FilterOptionList style={{ maxHeight: isMoreOpen ? '20rem' : '6.5rem' }}>
            {filterOptions?.map((filterOption: string, index) => (
              <FilterOption
                key={index}
                filterType={SIDE_FILTER}
                filterOption={filterOption}
                isReset={isReset}
                borderTop={index === COURSE_FILTERS.LANGUAGES.filterOptions.length}
              />
            ))}
          </FilterOptionList>
        );
      case '수강 비용':
        return (
          <>
            <CostFilterOptionList style={{ maxHeight: isMoreOpen ? '20rem' : '6.25rem' }}>
              <FilterOption filterType={SIDE_FILTER} filterOption={filterOptions[0]} isReset={isReset} />
              <FilterOption filterType={SIDE_FILTER} filterOption={filterOptions[1]} isReset={isReset} />
            </CostFilterOptionList>
            <RangeBar filterType={SIDE_FILTER} filterName={COURSE_FILTERS.COST_TYPE.filterName} isReset={isReset} />
          </>
        );
      case '수강 기간':
        return (
          <RangeBar filterType={SIDE_FILTER} filterName={COURSE_FILTERS.PERIOD_INPUT.filterName} isReset={isReset} />
        );
      case '코딩 테스트':
        return (
          <TestOptionList style={{ maxHeight: isMoreOpen ? '999rem' : '6.25rem' }}>
            <FilterOption filterType={SIDE_FILTER} filterOption={filterOptions[0]} isReset={isReset} />
            <FilterOption filterType={SIDE_FILTER} filterOption={filterOptions[1]} isReset={isReset} />
          </TestOptionList>
        );
      default:
        return null;
    }
  };

  return (
    <Wrapper>
      <FilterTitle>
        {filterName}
        <CaretWrapper onClick={handleCaretClick}>
          <CaretIcon rotation={rotation} />
        </CaretWrapper>
      </FilterTitle>
      <FilterBodyWrapper style={{ display: isOpen ? 'block' : 'none' }}>
        <FilterBody>
          {renderFilterOptionList()}
          {isMore && (
            <MoreButton onClick={handleMoreClick}>
              {isMoreOpen ? (
                <CaretIconWrapper>
                  <span>접기</span>
                  <CaretUpIcon />
                </CaretIconWrapper>
              ) : (
                <CaretIconWrapper>
                  <span>더 보기</span>
                  <CaretDownIcon />
                </CaretIconWrapper>
              )}
            </MoreButton>
          )}
        </FilterBody>
      </FilterBodyWrapper>
    </Wrapper>
  );
};

export type SideFilterItemProps = CourseFilterTypes & {
  isReset: boolean;
};

export default SideFilterItem;
