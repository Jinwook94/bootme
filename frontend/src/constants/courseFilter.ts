const CATEGORIES = {
  SUPER: ['웹', '모바일 앱', '게임', '임베디드', '데스크탑 앱', 'AI', '데이터', '데브옵스', '보안'],
  SUB: ['프론트엔드', '백엔드', '풀스택', '안드로이드', 'iOS', '크로스플랫폼'],
};

const STACKS = {
  LANGUAGES: ['HTML', 'CSS', 'JavaScript', 'TypeScript', 'Java', 'Python', 'Kotlin', 'Swift'],
  FRAMEWORKS: ['Spring', 'Django', 'React', 'Vue.js', 'Node.js', 'Angular'],
};

export const COURSE_FILTER: { [key: string]: CourseFilterTypes } = {
  CATEGORIES: {
    filterName: '개발 분야',
    filterOptions: [...CATEGORIES.SUPER, ...CATEGORIES.SUB],
    isMore: true,
  },
  STACKS: {
    filterName: '기술 스택',
    filterOptions: [...STACKS.LANGUAGES, ...STACKS.FRAMEWORKS],
    isMore: true,
  },
  CostType: {
    filterName: '수강 비용',
    filterOptions: ['무료', '무료 (국비)'],
    isMore: false,
  },
  Period: {
    filterName: '수강 기간',
    filterOptions: [],
    isMore: false,
  },
  Test: {
    filterName: '코딩 테스트',
    filterOptions: ['있음', '없음'],
    isMore: false,
  },
};

export interface CourseFilterTypes {
  filterName: string;
  filterOptions: string[];
  isMore: boolean;
}
