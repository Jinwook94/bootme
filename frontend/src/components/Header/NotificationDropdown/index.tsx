import { Popover } from 'antd';
import { ItemWrapper, NoResult, NotificationDate, NotificationItemWrapper, Title, Wrapper } from './style';
import React, { useEffect } from 'react';
import { NotificationActiveIcon, NotificationIcon } from '../../../constants/icons';
import { NotificationTypes, useNotification } from '../../../hooks/useNotification';

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
          recentNotifications.map(({ notificationId, message, createdAt }: NotificationTypes) => (
            <NotificationItem key={notificationId} message={message} createdAt={createdAt} />
          ))
        )}
      </Wrapper>
    );
  }
};

const NotificationItem = ({ createdAt, message }: Pick<NotificationTypes, 'message' | 'createdAt'>) => {
  const timeSinceNotification = getTimeSinceNotification(createdAt);

  // Find the part of the message to be bolded and wrap it in a <span> tag with a fontWeight style
  const boldedMessage = message.replace(/\*\*(.*?)\*\*/g, '<span style="font-weight: 600">$1</span>');

  return (
    <NotificationItemWrapper>
      <ItemWrapper>
        <div style={{ marginBottom: '6px' }} dangerouslySetInnerHTML={{ __html: boldedMessage }} />
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
