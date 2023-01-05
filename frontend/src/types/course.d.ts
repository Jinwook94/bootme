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
  isRecommended: boolean;
  isTested: boolean;
  isRegisterOpen: boolean;
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
