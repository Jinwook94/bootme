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
import DateFormatter from './dateFormatter';
import useWebhook from '../../hooks/useWebhook';
import { COURSE_CLICKED } from '../../constants/webhook';

const CourseCard = ({
  id,
  title,
  url,
  company,
  categories,
  stacks,
  dates,
  period,
  cost,
  costType,
}: CourseCardProps) => {
  const weeks = Math.round(period / 7);
  const months = Math.round(period / 30);
  const { sendWebhookNoti } = useWebhook();

  return (
    <Wrapper>
      <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
      />

      <ItemHeader>
        <a href={url} target="_blank" rel="noreferrer">
          <CompanyLogo src={company.logoUrl} alt={company.name} onClick={() => sendWebhookNoti(COURSE_CLICKED, id)} />
        </a>
      </ItemHeader>
      <ItemBody>
        <CourseTitleWrapper>
          <CourseTitle as="a" href={url} target="_blank" onClick={() => sendWebhookNoti(COURSE_CLICKED, id)}>
            {title}
          </CourseTitle>
        </CourseTitleWrapper>
        <CompanyName href={company.url} target="_blank" onClick={() => sendWebhookNoti(COURSE_CLICKED, id)}>
          {company.name}
        </CompanyName>
        <CourseInfo>
          <ItemWrapper>
            <span
              className="material-symbols-outlined"
              style={{ fontSize: '1rem', verticalAlign: 'sub', marginRight: '2px' }}
            >
              notifications
            </span>
            <span>
              <DateFormatter date={dates.courseStartDate} /> 개강
            </span>
          </ItemWrapper>
          <ItemWrapper>
            <span
              className="material-symbols-outlined"
              style={{ fontSize: '1rem', verticalAlign: 'sub', marginRight: '2px' }}
            >
              calendar_month
            </span>
            {months >= 4 ? <span>{months}개월</span> : <span>{weeks}주</span>}
          </ItemWrapper>
          <ItemWrapper>
            <span
              className="material-symbols-outlined"
              style={{ fontSize: '1rem', verticalAlign: 'sub', marginRight: '2px' }}
            >
              credit_card
            </span>
            <span>{cost === 0 ? costType : cost + '만원'} </span>
          </ItemWrapper>
        </CourseInfo>
        <CourseTags>
          {categories['sub']?.map((tag: string) => (
            <TagItem key={tag} style={{ backgroundColor: '#e6f7ff', color: '#1c1c1c' }}>
              {tag}
            </TagItem>
          ))}
          {stacks['languages']?.map((tag: string) => (
            <TagItem key={tag} style={{ backgroundColor: '#e9ecf3', color: '#44576c' }}>
              {tag}
            </TagItem>
          ))}
          {stacks['frameworks']?.map((tag: string) => (
            <TagItem key={tag} style={{ backgroundColor: '#e9ecf3', color: '#44576c' }}>
              {tag}
            </TagItem>
          ))}
        </CourseTags>
      </ItemBody>
      <Bookmark onClick={() => sendWebhookNoti(COURSE_CLICKED, id)}>
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
  | 'recommended'
  | 'tested'
  | 'registerOpen'
  | 'location'
  | 'clicks'
  | 'bookmarks'
  | 'createdAt'
  | 'modifiedAt'
>;

export default CourseCard;
