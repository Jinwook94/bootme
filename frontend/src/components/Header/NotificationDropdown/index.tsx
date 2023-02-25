import { Popover } from 'antd';
import { ItemWrapper, NoResult, NotificationDate, NotificationItemWrapper, Title, Wrapper } from './style';
import React, { useEffect } from 'react';
import { NotificationActiveIcon, NotificationIcon } from '../../../constants/icons';
import { useNotification } from '../../../hooks/useNotification';

const NotificationDropdown = () => {
  const { notifications, isAllChecked, getNotifications, updateNotifications } = useNotification();
  const recentNotifications = notifications.sort((a, b) => b.createdAt - a.createdAt).slice(0, 3);
  const memberId = Number(localStorage.getItem('MemberId'));

  useEffect(() => {
    getNotifications(memberId);
  }, []);

  return (
    <div onClick={() => updateNotifications(memberId)}>
      <Popover content={content} trigger="click" placement="bottomRight">
        {isAllChecked ? <NotificationIcon /> : <NotificationActiveIcon />}
        <div style={{ width: 0, height: 0 }} />
      </Popover>
    </div>
  );

  function content() {
    return (
      <Wrapper>
        <Title>알림</Title>
        {notifications.length === 0 ? (
          <NoResult>새로운 알림이 없습니다.</NoResult>
        ) : (
          recentNotifications.map(({ notificationId, event, courseTitle, createdAt }: Notification) => (
            <NotificationItem key={notificationId} event={event} courseTitle={courseTitle} createdAt={createdAt} />
          ))
        )}
      </Wrapper>
    );
  }
};

const NotificationItem = ({ event, courseTitle, createdAt }: Notification) => {
  let message = '';
  switch (event) {
    case 'registrationStart':
      message = `북마크하신 코스 ${courseTitle}의 접수가 시작되었어요. 놓치지 마시고 신청하세요 😄`;
      break;
    case 'registrationEndInThreeDays':
      message = `북마크하신 코스 ${courseTitle}의 접수 마감이 3일 남았어요. 놓치지 마시고 신청하세요 😊`;
      break;
    case 'registrationEnd':
      message = `북마크하신 코스 ${courseTitle}의 접수 마감일이에요. 놓치지 마시고 신청하세요 ☺️`;
      break;
  }
  const timeSinceNotification = getTimeSinceNotification(createdAt);

  // 코스 타이틀만 굵은 글씨로 표시하기 위해 인덱싱
  const index = message.indexOf(courseTitle);
  const beforeCourseTitle = message.substring(0, index);
  const afterCourseTitle = message.substring(index + courseTitle.length);

  return (
    <NotificationItemWrapper>
      <ItemWrapper>
        <div style={{ marginBottom: '6px' }}>
          {beforeCourseTitle}
          <strong>{courseTitle}</strong>
          {afterCourseTitle}
        </div>
        <NotificationDate>{timeSinceNotification}</NotificationDate>
      </ItemWrapper>
    </NotificationItemWrapper>
  );
};

const getTimeSinceNotification = (createdAt: number) => {
  const SECOND = 1000;
  const MINUTE = 60 * SECOND;
  const HOUR = 60 * MINUTE;
  const DAY = 24 * HOUR;
  const MONTH = 30 * DAY;
  const now = Date.now();

  const timeDiff = now - createdAt;

  if (timeDiff < HOUR) {
    const minutes = Math.floor(timeDiff / MINUTE);
    return `${minutes}분 전`;
  } else if (timeDiff < DAY) {
    const hours = Math.floor(timeDiff / HOUR);
    return `${hours}시간 전`;
  } else if (timeDiff < 60 * DAY) {
    const days = Math.floor(timeDiff / DAY);
    return `${days}일 전`;
  } else {
    const months = Math.floor(timeDiff / MONTH);
    return `${months}개월 전`;
  }
};

export default NotificationDropdown;

interface Notification {
  notificationId?: number;
  event: string;
  bookmarkCourseId?: number;
  memberId?: number;
  courseId?: number;
  courseTitle: string;
  checked?: boolean;
  createdAt: number;
}
