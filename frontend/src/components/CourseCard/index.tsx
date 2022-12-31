import {
  Wrapper,
  ItemHeader,
  ItemBody,
  CourseTitleWrapper,
  CourseTitle,
  CompanyLogo,
  CompanyName,
  CourseInfo,
  ItemWrapper,
  CourseTags,
  TagItem,
  Bookmark,
} from './style';

import './style.css';
import BookmarkIcon from '../../assets/bookmark.svg';

const CourseCard = ({ title, url, company, tags, dates, period, cost }: CourseCardProps) => {
  const categoryTags = ['백엔드', '프론트엔드', '웹', '앱'];

  return (
    <Wrapper>
      <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
      />

      <ItemHeader>
        <a href={url} target="_blank" rel="noreferrer">
          <CompanyLogo src={company.logoUrl} alt={company.name} />
        </a>
      </ItemHeader>
      <ItemBody>
        <CourseTitleWrapper>
          <CourseTitle as="a" href={url} target="_blank">
            {title}
          </CourseTitle>
        </CourseTitleWrapper>
        <CompanyName href={company.url} target="_blank">
          {company.name}
        </CompanyName>
        <CourseInfo>
          <ItemWrapper>
            <span
              className="material-symbols-outlined"
              style={{ fontSize: '.9rem', verticalAlign: 'sub', marginRight: '2px', transform: 'translateY(-0.8px)' }}
            >
              schedule
            </span>
            <span>개강 {dates.courseStartDate}</span>
          </ItemWrapper>
          <ItemWrapper>
            <span
              className="material-symbols-outlined"
              style={{ fontSize: '1rem', verticalAlign: 'sub', marginRight: '2px' }}
            >
              calendar_month
            </span>
            <span>{period}</span>
          </ItemWrapper>
          <ItemWrapper>
            <span
              className="material-symbols-outlined"
              style={{ fontSize: '1rem', verticalAlign: 'sub', marginRight: '2px' }}
            >
              paid
            </span>
            <span>{cost === 0 ? '무료' : cost}</span>
          </ItemWrapper>
        </CourseInfo>
        <CourseTags>
          {tags?.map((tag: string) => (
            <TagItem
              key={tag}
              style={
                categoryTags.includes(tag)
                  ? { backgroundColor: '#e6f7ff', color: '#1c1c1c' }
                  : { backgroundColor: '#e9ecf3', color: '#44576c' }
              }
            >
              {tag}
            </TagItem>
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
  | 'costType'
  | 'isRecommended'
  | 'isTested'
  | 'isRegisterOpen'
  | 'location'
>;

export default CourseCard;
