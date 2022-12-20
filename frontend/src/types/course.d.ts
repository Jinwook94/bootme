interface Course {
  id: number;
  title: string;
  name: string;
  generation: number;
  url: string;
  location: string;
  onOffline: string;
  tags: string[];
  prerequisites: string;
  cost: number;
  costType: string;
  period: string;
  dates: datesType;
  company: Company;
  isRecommended: boolean;
  isTested: boolean;
  isRegisterOpen: boolean;
}

type datesType = {
  registrationStartDate: string;
  registrationEndDate: string;
  courseStartDate: string;
  courseEndDate: string;
};
