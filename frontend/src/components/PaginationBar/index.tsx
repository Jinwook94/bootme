import React from 'react';
import { PageItem, PageNumb, Pagination } from './style';

const PaginationBar = ({
  maxPage,
  currentPage,
  handleNumberClick,
  handlePrevClick,
  handleNextClick,
}: PaginationBarProps) => {
  const pageNumbers = [];
  for (let i = 1; i <= Math.max(maxPage, 1); i++) {
    pageNumbers.push(i);
  }

  return (
    <Pagination>
      <PageItem>
        <PageNumb onClick={handlePrevClick}>{'<'}</PageNumb>
      </PageItem>

      {pageNumbers.map(number => (
        <PageItem key={number}>
          <PageNumb
            onClick={handleNumberClick ? handleNumberClick(number) : undefined}
            className={number == currentPage ? 'active' : undefined}
          >
            {number}
          </PageNumb>
        </PageItem>
      ))}

      <PageItem>
        <PageNumb onClick={handleNextClick}>{'>'}</PageNumb>
      </PageItem>
    </Pagination>
  );
};

interface PaginationBarProps {
  maxPage: number;
  handleNumberClick?: (number: number) => React.MouseEventHandler;
  currentPage: number;
  handleNextClick: React.MouseEventHandler;
  handlePrevClick: React.MouseEventHandler;
}

export default PaginationBar;
