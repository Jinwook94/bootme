import {
  Wrapper,
  ItemHeader,
  ItemBody,
  CourseTitleWrapper,
  CourseTitle,
  CompanyLogo,
  CompanyName,
  CompanyLink,
  CourseInfo,
  Experience,
  Location,
  CourseTags,
  TagItem,
  Bookmark,
} from './style';

import ExperienceIcon from '../../assets/experience.svg';
import LocationIcon from '../../assets/location.svg';
import BookmarkIcon from '../../assets/bookmark.svg';

const CourseCard = ({ title, url, company, tags, location }: CourseCardProps) => {
  return (
    <Wrapper>
      <ItemHeader>
        <a>
          <CompanyLogo src={company.logoUrl} alt={company.name} className="company-logo" />
        </a>
      </ItemHeader>
      <ItemBody>
        <CourseTitleWrapper>
          <CourseTitle>
            <a href={url}> {title}</a>
          </CourseTitle>
        </CourseTitleWrapper>
        <CompanyName>
          <CompanyLink>{company.name}</CompanyLink>
        </CompanyName>
        <CourseInfo>
          <Experience>
            <ExperienceIcon />
            경력무관
          </Experience>
          <Location>
            <LocationIcon />
            {location}
          </Location>
        </CourseInfo>
        <CourseTags>
          {tags?.map((tag: string) => (
            <TagItem key={tag}>{tag}</TagItem>
          ))}
        </CourseTags>
      </ItemBody>

      <div className={'Bookmark'}>
        <Bookmark>
          <BookmarkIcon />
        </Bookmark>
      </div>
    </Wrapper>
  );
};

export type CourseCardProps = Omit<
  Course,
  | 'name'
  | 'generation'
  | 'onOffline'
  | 'prerequisites'
  | 'cost'
  | 'costType'
  | 'period'
  | 'dates'
  | 'isRecommended'
  | 'isTested'
  | 'isRegisterOpen'
>;

export default CourseCard;
