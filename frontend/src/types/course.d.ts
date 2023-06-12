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
  superCategories: string[];
  subCategories: string[];
  languages: string[];
  frameworks: string[];
  cost: number;
  period: number;
  dates: datesType;
  company: Company;
  detail: string;
  clicks: number;
  bookmarks: number;
  createdAt: number;
  modifiedAt: bigint;
  free: boolean;
  kdt: boolean;
  online: boolean;
  tested: boolean;
  prerequisiteRequired: boolean;
  recommended: boolean;
  registerOpen: boolean;
}

type datesType = {
  registrationStartDate: string;
  registrationEndDate: string;
  courseStartDate: string;
  courseEndDate: string;
};
