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
} from './style';
import FilterOption from '../FilterOption';
import React, { useState } from 'react';
import { RangeBar } from '../RangeBar';
import { CourseFilterTypes, CATEGORIES, COST_TYPE, PERIOD, STACKS, TEST } from '../../../constants/courseFilter';
import { CaretDownIcon, CaretIcon, CaretUpIcon } from '../../../constants/icons';

type Position = 'absolute' | 'relative' | 'fixed' | 'unset';

const FilterItem = ({ filter, filterName, filterOptions, isMore, isReset }: FilterItemProps) => {
  const [isOpen, setIsOpen] = useState(true);
  const [isMoreOpen, setIsMoreOpen] = useState(false);
  const [position, setPosition] = useState<Position | undefined>('absolute');
  const [paddingTop, setPaddingTop] = useState('2rem');
  const [paddingBottom, setPaddingBottom] = useState('0.875rem');
  const [rotation, setRotation] = useState(0);

  function handleClick() {
    setIsOpen(!isOpen);
    setRotation(rotation === 0 ? 180 : 0);
  }

  function handleMoreClick() {
    setIsMoreOpen(!isMoreOpen);
    setPosition(position === 'absolute' ? 'unset' : 'absolute');
    setPaddingTop(paddingTop === '2.25rem' ? '0.75rem' : '2.25rem');
    setPaddingBottom(paddingBottom === '0.875rem' ? '0.5rem' : '0.875rem');
  }

  const renderFilterOptionList = () => {
    switch (filter) {
      case CATEGORIES:
        return (
          <FilterOptionList style={{ maxHeight: isMoreOpen ? '999rem' : '6.5rem' }}>
            {filterOptions.map((filterOption: string, index) => (
              <FilterOption
                key={index}
                filterName={filterName}
                filterOption={filterOption}
                isReset={isReset}
                borderTop={index === 9}
              />
            ))}
          </FilterOptionList>
        );
      case STACKS:
        return (
          <FilterOptionList style={{ maxHeight: isMoreOpen ? '999rem' : '6.5rem' }}>
            {filterOptions.map((filterOption: string, index) => (
              <FilterOption
                key={index}
                filterName={filterName}
                filterOption={filterOption}
                isReset={isReset}
                borderTop={index === 8}
              />
            ))}
          </FilterOptionList>
        );
      case COST_TYPE:
        return (
          <>
            <CostFilterOptionList style={{ maxHeight: isMoreOpen ? '999rem' : '6.25rem' }}>
              <FilterOption filterName={filterName} filterOption={filterOptions[0]} isReset={isReset} />
              <FilterOption filterName={filterName} filterOption={filterOptions[1]} isReset={isReset} />
            </CostFilterOptionList>
            <RangeBar filterName={filterName} isReset={isReset} />
          </>
        );
      case PERIOD:
        return <RangeBar filterName={filterName} isReset={isReset} />;
      case TEST:
        return (
          <TestOptionList style={{ maxHeight: isMoreOpen ? '999rem' : '6.25rem' }}>
            <FilterOption filterName={filterName} filterOption={filterOptions[0]} isReset={isReset} />
            <FilterOption filterName={filterName} filterOption={filterOptions[1]} isReset={isReset} />
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
        <CaretWrapper onClick={handleClick}>
          <CaretIcon rotation={rotation} />
        </CaretWrapper>
      </FilterTitle>
      <FilterBodyWrapper style={{ display: isOpen ? 'block' : 'none', paddingBottom: isMoreOpen ? '0.3rem' : '1rem' }}>
        <FilterBody>
          {renderFilterOptionList()}
          {isMore && (
            <MoreButton
              onClick={handleMoreClick}
              style={{ position: position, paddingTop: paddingTop, paddingBottom: paddingBottom }}
            >
              {isMoreOpen ? (
                <>
                  <span>접기</span>
                  <CaretUpIcon />
                </>
              ) : (
                <>
                  <span>더 보기</span>
                  <CaretDownIcon />
                </>
              )}
            </MoreButton>
          )}
        </FilterBody>
      </FilterBodyWrapper>
    </Wrapper>
  );
};

export type FilterItemProps = CourseFilterTypes & {
  filter: CourseFilterTypes;
  isReset: boolean;
};

export default FilterItem;
