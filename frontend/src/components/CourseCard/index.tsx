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
import { COURSE_CLICKED } from '../../constants/webhook';
import { useBookmarks } from '../../hooks/useBookmarks';
import { appendUtmParams } from '../../api/fetcher';
import PATH from '../../constants/path';
import { Link } from 'react-router-dom';
import { createStyles, Paper, rem } from '@mantine/core';
import { useState } from 'react';
import { BOOKMARK_TYPE } from '../../constants/others';

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
  bookmarked,
}: CourseCardProps) => {
  const weeks = Math.round(period / 7);
  const months = Math.round(period / 30);
  const { sendWebhookNoti } = useWebhook();
  const { handleBookmarkClick } = useBookmarks();
  const [bookmarkedState, setBookmarkedState] = useState(bookmarked);

  const useStyles = createStyles(theme => ({
    card: {
      position: 'relative',
      cursor: 'pointer',
      overflow: 'hidden',
      transition: 'transform 150ms ease, box-shadow 100ms ease',

      '&:hover': {
        boxShadow: theme.shadows.md,
        transform: 'scale(1.02)',
      },

      '&::before': {
        content: '""',
        position: 'absolute',
        top: 0,
        bottom: 0,
        left: 0,
        width: rem(6),
      },
    },
  }));
  const { classes } = useStyles();

  return (
    <div style={{ display: 'flex', width: '100%', margin: '0 auto' }}>
      <Paper style={{ width: '100%' }} withBorder radius="md" className={classes.card}>
        <Wrapper>
          <link
            rel="stylesheet"
            href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
          />
          <ItemHeader>
            <Link to={`${PATH.COURSE.DETAIL}/${id}`}>
              <CompanyLogo src={company.logoUrl} alt={company.name} />
            </Link>
          </ItemHeader>
          <ItemBody>
            <CourseTitleWrapper>
              <Link to={`${PATH.COURSE.DETAIL}/${id}`} style={{ width: 'inherit' }}>
                <CourseTitle>{title}</CourseTitle>
              </Link>
            </CourseTitleWrapper>
            <CompanyNameWrapper>
              <CompanyName
                href={appendUtmParams(company.serviceUrl)}
                target="_blank"
                onClick={() => sendWebhookNoti(COURSE_CLICKED, id)}
              >
                {company.serviceName}
              </CompanyName>
            </CompanyNameWrapper>
            <Link to={`${PATH.COURSE.DETAIL}/${id}`}>
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
            </Link>
          </ItemBody>
          <Bookmark onClick={() => handleBookmarkClick(id, BOOKMARK_TYPE.COURSE, bookmarkedState, setBookmarkedState)}>
            {bookmarkedState ? <HeartFilled /> : <HeartOutlined />}
          </Bookmark>
        </Wrapper>
      </Paper>
    </div>
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
