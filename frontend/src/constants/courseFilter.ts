export const Categories = {
  super: ['웹', '모바일 앱', '게임', '임베디드', '데스크탑 앱', 'AI', '데이터', '데브옵스', '보안'],
  sub: ['프론트엔드', '백엔드', '풀스택', '안드로이드', 'iOS', '크로스플랫폼'],
};

export const Stacks = {
  languages: ['HTML', 'CSS', 'JavaScript', 'TypeScript', 'Java', 'Python', 'Kotlin', 'Swift'],
  frameworks: ['Spring', 'Django', 'React', 'Vue.js', 'Node.js', 'Angular'],
};

export const CourseFilter: { [key: string]: CourseFilterTypes } = {
  Categories: {
    filterName: '개발 분야',
    filterOptions: [...Categories.super, ...Categories.sub],
    isMore: true,
    borderTop: true,
  },
  Stacks: {
    filterName: '기술 스택',
    filterOptions: [...Stacks.languages, ...Stacks.frameworks],
    isMore: true,
    borderTop: true,
  },
  CostType: {
    filterName: '비용 타입',
    filterOptions: ['무료', '무료 (국비)'],
    isMore: false,
    borderTop: false,
  },
  Period: {
    filterName: '수강 기간',
    filterOptions: [],
    isMore: false,
    borderTop: false,
  },
  Test: {
    filterName: '코딩 테스트',
    filterOptions: ['있음', '없음'],
    isMore: false,
    borderTop: false,
  },
};

export interface CourseFilterTypes {
  filterName: string;
  filterOptions: string[];
  isMore: boolean;
  borderTop: boolean;
}
