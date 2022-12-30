import FilterItem from './FilterItem';
import { FilterItemWrapper, FilterReset, ResetButton, Wrapper } from './style';

const SideFilter = () => {
  return (
    <Wrapper>
      <FilterReset>
        <ResetButton>
          <svg
            style={{
              verticalAlign: 'middle',
              marginRight: '0.25rem',
              color: 'rgb(38, 55, 71)',
            }}
            width="1rem"
            height="1rem"
            viewBox="0 0 24 24"
            fill="currentColor"
            xmlns="http://www.w3.org/2000/svg"
          >
            <path
              fillRule="evenodd"
              clipRule="evenodd"
              d="M17.65 6.35C16.2 4.9 14.21 4 12 4C7.58001 4 4.01001 7.58 4.01001 12C4.01001 16.42 7.58001 20 12 20C15.73 20 18.84 17.45 19.73 14H17.65C16.83 16.33 14.61 18 12 18C8.69001 18 6.00001 15.31 6.00001 12C6.00001 8.69 8.69001 6 12 6C13.66 6 15.14 6.69 16.22 7.78L13 11H20V4L17.65 6.35Z"
              fill="currentColor"
            ></path>
          </svg>
          필터 초기화
        </ResetButton>
      </FilterReset>
      <FilterItemWrapper>
        <FilterItem
          filterName={'프로그래밍 언어'}
          filterOptions={['HTML', 'JavaScript', 'CSS', 'Java', 'Ruby', 'Python', 'TypeScript']}
          isMore={true}
        />
        <FilterItem filterName={'수업 난이도'} filterOptions={['입문', '초급', '중급', '고급']} isMore={false} />
        <FilterItem filterName={'수강 기간'} filterOptions={['무제한', '30일 이내', '2달', '3달']} isMore={false} />
      </FilterItemWrapper>
    </Wrapper>
  );
};

export default SideFilter;
