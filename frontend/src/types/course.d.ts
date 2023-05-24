interface CoursesResponse {
  content: Course[];
  pageable: Pageable;
  totalElements: number;
  totalPages: number;
  last: boolean;
  numberOfElements: number;
  size: number;
  first: boolean;
  number: number;
  sort: Sort;
  empty: boolean;
}

interface Pageable {
  sort: Sort;
  pageNumber: number;
  pageSize: number;
  offset: number;
  paged: boolean;
  unpaged: boolean;
}

interface Sort {
  unsorted: boolean;
  sorted: boolean;
  empty: boolean;
}

interface Course {
  id: number;
  title: string;
  name: string;
  generation: number;
  url: string;
  location: string;
  onOffline: string;
  categories: Categories;
  stacks: Stacks;
  prerequisites: string;
  cost: number;
  costType: string;
  period: number;
  dates: datesType;
  company: Company;
  clicks: number;
  bookmarks: number;
  createdAt: number;
  modifiedAt: bigint;
  tested: boolean;
  recommended: boolean;
  registerOpen: boolean;
}

type Categories = {
  super: string[];
  sub: string[];
};

type Stacks = {
  languages: string[];
  frameworks: string[];
};

type datesType = {
  registrationStartDate: string;
  registrationEndDate: string;
  courseStartDate: string;
  courseEndDate: string;
};
