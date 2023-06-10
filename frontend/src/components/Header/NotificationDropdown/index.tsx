import { Popover } from 'antd';
import { ItemWrapper, NoResult, NotificationDate, NotificationItemWrapper, Title, Wrapper } from './style';
import React, { useEffect } from 'react';
import { NotificationActiveIcon, NotificationIcon } from '../../../constants/icons';
import { NotificationTypes, useNotification } from '../../../hooks/useNotification';
import { getTimeSince } from '../../../utils/timeUtils';

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
  const timeSinceNotification = getTimeSince(createdAt);

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

export default NotificationDropdown;
