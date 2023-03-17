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
import {
  CourseFilterTypes,
  CATEGORIES,
  COST_TYPE,
  PERIOD,
  STACKS,
  TEST,
  SIDE_FILTER,
  CATEGORIES_OPTIONS,
  STACKS_OPTIONS,
} from '../../../constants/courseFilter';
import { CaretDownIcon, CaretIcon, CaretUpIcon } from '../../../constants/icons';

const SideFilterItem = ({ filter, filterName, filterOptions, isMore, isReset }: SideFilterItemProps) => {
  const [isOpen, setIsOpen] = useState(true);
  const [isMoreOpen, setIsMoreOpen] = useState(false);
  const [rotation, setRotation] = useState(0);

  function handleClick() {
    setIsOpen(!isOpen);
    setRotation(rotation === 0 ? 180 : 0);
  }

  function handleMoreClick() {
    setIsMoreOpen(!isMoreOpen);
  }

  const renderFilterOptionList = () => {
    switch (filter) {
      case CATEGORIES:
        return (
          <FilterOptionList style={{ maxHeight: isMoreOpen ? '20rem' : '6.5rem' }}>
            {filterOptions.map((filterOption: string, index) => (
              <FilterOption
                key={index}
                filterType={SIDE_FILTER}
                filterName={filterName}
                filterOption={filterOption}
                isReset={isReset}
                borderTop={index === CATEGORIES_OPTIONS.SUPER.length}
              />
            ))}
          </FilterOptionList>
        );
      case STACKS:
        return (
          <FilterOptionList style={{ maxHeight: isMoreOpen ? '20rem' : '6.5rem' }}>
            {filterOptions.map((filterOption: string, index) => (
              <FilterOption
                key={index}
                filterType={SIDE_FILTER}
                filterName={filterName}
                filterOption={filterOption}
                isReset={isReset}
                borderTop={index === STACKS_OPTIONS.LANGUAGES.length}
              />
            ))}
          </FilterOptionList>
        );
      case COST_TYPE:
        return (
          <>
            <CostFilterOptionList style={{ maxHeight: isMoreOpen ? '20rem' : '6.25rem' }}>
              <FilterOption
                filterType={SIDE_FILTER}
                filterName={filterName}
                filterOption={filterOptions[0]}
                isReset={isReset}
              />
              <FilterOption
                filterType={SIDE_FILTER}
                filterName={filterName}
                filterOption={filterOptions[1]}
                isReset={isReset}
              />
            </CostFilterOptionList>
            <RangeBar filterType={SIDE_FILTER} filterName={filterName} isReset={isReset} />
          </>
        );
      case PERIOD:
        return <RangeBar filterType={SIDE_FILTER} filterName={filterName} isReset={isReset} />;
      case TEST:
        return (
          <TestOptionList style={{ maxHeight: isMoreOpen ? '999rem' : '6.25rem' }}>
            <FilterOption
              filterType={SIDE_FILTER}
              filterName={filterName}
              filterOption={filterOptions[0]}
              isReset={isReset}
            />
            <FilterOption
              filterType={SIDE_FILTER}
              filterName={filterName}
              filterOption={filterOptions[1]}
              isReset={isReset}
            />
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
            <MoreButton onClick={handleMoreClick}>
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

export type SideFilterItemProps = CourseFilterTypes & {
  filter: CourseFilterTypes;
  isReset: boolean;
};

export default SideFilterItem;
