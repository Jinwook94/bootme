import {
  Wrapper,
  ItemHeader,
  ItemBody,
  CourseTitleWrapper,
  CourseTitle,
  CompanyLogo,
  CompanyName,
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
        <a href={url}>
          <CompanyLogo src={company.logoUrl} alt={company.name} />
        </a>
      </ItemHeader>
      <ItemBody>
        <CourseTitleWrapper>
          <CourseTitle as="a" href={url}>
            {title}
          </CourseTitle>
        </CourseTitleWrapper>
        <CompanyName href={company.url} target="_blank">
          {company.name}
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
      <Bookmark>
        <BookmarkIcon />
      </Bookmark>
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
