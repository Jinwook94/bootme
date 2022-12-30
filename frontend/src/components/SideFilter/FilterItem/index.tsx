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
} from './style';
import FilterOption from './FilterOption';
import React, { useState } from 'react';

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
    setPaddingTop(paddingTop === '2rem' ? '1rem' : '2rem');
    setPaddingBottom(paddingBottom === '0.875rem' ? '0.25rem' : '0.875rem');
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
      <FilterBodyWrapper style={{ display: isOpen ? 'block' : 'none' }}>
        <FilterBody>
          <FilterOptionList style={{ maxHeight: isMoreOpen ? '999rem' : '9.25rem' }}>
            {filterOptions.map((filterOption: string) => (
              <FilterOption key={filterOption} filterOption={filterOption} />
            ))}
          </FilterOptionList>
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

export interface FilterItemProps {
  filterName: string;
  filterOptions: string[];
  isMore: boolean;
}

export default FilterItem;
