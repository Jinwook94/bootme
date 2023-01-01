import FilterItem from './FilterItem';
import { FilterItemWrapper, FilterReset, ResetButton, Wrapper } from './style';

const SideFilter = () => {
  const filters = [
    {
      name: '개발 분야',
      options: ['웹', '모바일 앱', '게임', '임베디드', '데스크탑 앱', 'AI', '데이터', '데브옵스', '보안'],
      isMore: true,
      borderTop: 'none',
    },
    {
      name: '기술 스택',
      options: [
        'HTML',
        'CSS',
        'JavaScript',
        'TypeScript',
        'Java',
        'Python',
        'Kotlin',
        'Swift',
        'Spring',
        'Django',
        'React',
        'Vue.js',
        'Node.js',
        'Angular',
      ],
      isMore: true,
      borderTop: 8,
    },
    {
      name: '비용',
      options: ['무료', '무료 (국비)', '유료 (Progress bar)'],
      isMore: false,
      borderTop: 'none',
    },
    {
      name: '수강 기간',
      options: ['0 ~ 12 Progress Bar'],
      isMore: false,
      borderTop: 'none',
    },
    {
      name: '코딩 테스트',
      options: ['있음', '없음'],
      isMore: false,
      borderTop: 'none',
    },
  ];

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
        {filters.map(option => (
          <FilterItem
            key={option.name}
            filterName={option.name}
            filterOptions={option.options}
            isMore={option.isMore}
            borderTop={option.borderTop}
          />
        ))}
      </FilterItemWrapper>
    </Wrapper>
  );
};

export default SideFilter;
