import {
  FilterTitle,
  CaretWrapper,
  Wrapper,
  FilterBodyWrapper,
  FilterBody,
  MoreButton,
  FilterOptionList,
  CaretUp,
  CaretDown,
  CostFilterOptionList,
  TestOptionList,
} from './style';
import FilterOption from '../FilterOption';
import React, { useState } from 'react';
import { RangeBar } from '../RangeBar';
import { CourseFilterTypes } from '../../../constants/courseFilter';

type Position = 'absolute' | 'relative' | 'fixed' | 'unset';

const FilterItem = ({ filterName, filterOptions, isMore }: FilterItemProps) => {
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

  return (
    <Wrapper>
      <FilterTitle>
        {filterName}
        <CaretWrapper onClick={handleClick}>
          <svg
            style={{ verticalAlign: 'middle', transform: `rotate(${rotation}deg)`, transition: 'all 0.3s linear 0s' }}
            xmlns="http://www.w3.org/2000/svg"
            fill="currentColor"
            viewBox="0 0 24 24"
            width="1rem"
            height="1rem"
          >
            <path d="m2 8 1.775-1.77L12 14.45l8.225-8.22L22 8 12 18 2 8Z"></path>
          </svg>
        </CaretWrapper>
      </FilterTitle>
      <FilterBodyWrapper style={{ display: isOpen ? 'block' : 'none', paddingBottom: isMoreOpen ? '0.3rem' : '1rem' }}>
        <FilterBody>
          {filterName === '비용 타입' ? (
            <>
              <CostFilterOptionList style={{ maxHeight: isMoreOpen ? '999rem' : '6.25rem' }}>
                <FilterOption filterName={filterName} filterOption={filterOptions[0]} />
                <FilterOption filterName={filterName} filterOption={filterOptions[1]} />
              </CostFilterOptionList>
              <RangeBar filterName={filterName} />
            </>
          ) : null}
          {filterName === '수강 기간' ? <RangeBar filterName={filterName} /> : null}
          {filterName === '코딩 테스트' ? (
            <TestOptionList style={{ maxHeight: isMoreOpen ? '999rem' : '6.25rem' }}>
              {filterOptions.map((filterOption: string) => (
                <FilterOption key={filterOption} filterName={filterName} filterOption={filterOption} />
              ))}
            </TestOptionList>
          ) : null}
          {filterName !== '비용 타입' && filterName !== '수강 기간' && filterName !== '코딩 테스트' ? (
            <FilterOptionList style={{ maxHeight: isMoreOpen ? '999rem' : '6.5rem' }}>
              {filterOptions.map((filterOption: string, index) => (
                <FilterOption key={index} filterName={filterName} filterOption={filterOption} />
              ))}
            </FilterOptionList>
          ) : null}
          {isMore && (
            <MoreButton
              onClick={handleMoreClick}
              style={{ position: position, paddingTop: paddingTop, paddingBottom: paddingBottom }}
              paddingBottom={'0.875rem'}
              paddingTop={'2rem'}
              position={'absolute'}
            >
              {isMoreOpen ? (
                <>
                  <span>접기</span>
                  <CaretUp
                    xmlns={'http://www.w3.org/2000/svg'}
                    fill={'#0078FF'}
                    viewBox={'0 0 24 24'}
                    width={'0.875rem'}
                    height={'0.875rem'}
                  >
                    <path d="m2 8 1.775-1.77L12 14.45l8.225-8.22L22 8 12 18 2 8Z"></path>
                  </CaretUp>
                </>
              ) : (
                <>
                  <span>더 보기</span>
                  <CaretDown
                    xmlns={'http://www.w3.org/2000/svg'}
                    fill={'#0078FF'}
                    viewBox={'0 0 24 24'}
                    width={'0.875rem'}
                    height={'0.875rem'}
                  >
                    <path d="m2 8 1.775-1.77L12 14.45l8.225-8.22L22 8 12 18 2 8Z"></path>
                  </CaretDown>
                </>
              )}
            </MoreButton>
          )}
        </FilterBody>
      </FilterBodyWrapper>
    </Wrapper>
  );
};

export type FilterItemProps = CourseFilterTypes;

export default FilterItem;
