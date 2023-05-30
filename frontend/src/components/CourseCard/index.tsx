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
  CompanyNameWrapper,
} from './style';

import './style.css';
import { HeartFilled, HeartOutlined } from '@ant-design/icons';
import DateFormatter from './dateFormatter';
import useWebhook from '../../hooks/useWebhook';
import { COURSE_BOOKMARKED, COURSE_CLICKED } from '../../constants/webhook';
import { useBookmarks } from '../../hooks/useBookmarks';
import { appendUtmParams } from '../../api/fetcher';
import PATH from '../../constants/path';
import { Link } from 'react-router-dom';

const CourseCard = ({
  id,
  title,
  company,
  superCategories,
  subCategories,
  languages,
  frameworks,
  dates,
  period,
  cost,
  free,
  kdt,
}: CourseCardProps) => {
  const weeks = Math.round(period / 7);
  const months = Math.round(period / 30);
  const { sendWebhookNoti } = useWebhook();
  const { isBookmarked, handleBookmark } = useBookmarks();

  return (
    <Wrapper>
      <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
      />

      <ItemHeader>
        <Link to={`${PATH.COURSE}/${id}`}>
          <CompanyLogo src={company.logoUrl} alt={company.name} />
        </Link>
      </ItemHeader>
      <ItemBody>
        <CourseTitleWrapper>
          <Link to={`${PATH.COURSE}/${id}`}>
            <CourseTitle>{title}</CourseTitle>
          </Link>
        </CourseTitleWrapper>
        <CompanyNameWrapper>
          <CompanyName
            href={appendUtmParams(company.url)}
            target="_blank"
            onClick={() => sendWebhookNoti(COURSE_CLICKED, id)}
          >
            {company.name}
          </CompanyName>
        </CompanyNameWrapper>
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
            <span>{free && !kdt ? '무료' : free && kdt ? '무료(국비)' : !free ? `${cost}만원` : ''}</span>
          </ItemWrapper>
        </CourseInfo>
        <CourseTags>
          {superCategories?.map((tag: string, index: number) => (
            <TagItem key={index} style={{ backgroundColor: '#e6f7ff', color: '#1c1c1c' }}>
              {tag}
            </TagItem>
          ))}
          {subCategories?.map((tag: string, index: number) => (
            <TagItem key={index} style={{ backgroundColor: '#e6f7ff', color: '#1c1c1c' }}>
              {tag}
            </TagItem>
          ))}
          {languages?.map((tag: string, index: number) => (
            <TagItem key={index} style={{ backgroundColor: '#e9ecf3', color: '#44576c' }}>
              {tag}
            </TagItem>
          ))}
          {frameworks?.map((tag: string, index: number) => (
            <TagItem key={index} style={{ backgroundColor: '#e9ecf3', color: '#44576c' }}>
              {tag}
            </TagItem>
          ))}
        </CourseTags>
      </ItemBody>
      <Bookmark
        onClick={() => {
          handleBookmark(id);
          sendWebhookNoti(COURSE_CLICKED, id);
          sendWebhookNoti(COURSE_BOOKMARKED, id);
        }}
      >
        {isBookmarked[id] ? <HeartFilled /> : <HeartOutlined />}
      </Bookmark>
    </Wrapper>
  );
};

export type CourseCardProps = Omit<
  Course,
  | 'name'
  | 'generation'
  | 'prerequisiteRequired'
  | 'recommended'
  | 'tested'
  | 'registerOpen'
  | 'location'
  | 'online'
  | 'clicks'
  | 'bookmarks'
  | 'createdAt'
  | 'modifiedAt'
  | 'detail'
>;

export default CourseCard;
