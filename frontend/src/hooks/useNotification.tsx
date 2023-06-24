import React, { createContext, useContext, useEffect, useState } from 'react';
import { fetcher } from '../api/fetcher';
import { EVENT_SOURCE_TYPE } from '../constants/others';
import { useLogin } from './useLogin';

const NotificationContext = createContext<NotificationContextProps>({
  notifications: [],
  setNotifications: () => {},
  isAllChecked: true,
  setIsAllChecked: () => {},
  fetchNotifications: () => {},
  updateNotifications: () => Promise.resolve(),
});

export const NotificationProvider = ({ children }: NotificationProviderProps) => {
  const { isLogin } = useLogin();
  const [notifications, setNotifications] = useState<NotificationTypes[]>([]);
  const [isAllChecked, setIsAllChecked] = useState<boolean>(true);

  const fetchNotifications = (memberId: number) => {
    fetcher
      .get(`/notifications/${memberId}`)
      .then(r => {
        setNotifications(r.data);
      })
      .catch(error => {
        console.log(error);
      });
  };

  const updateNotifications = (memberId: number) => {
    return fetcher
      .put(`/notifications/${memberId}/checked`)
      .then(r => {
        setNotifications(r.data);
        setIsAllChecked(true);
      })
      .catch(error => {
        console.log(error);
      });
  };

  useEffect(() => {
    if (!isLogin) {
      return;
    }

    const memberId = Number(localStorage.getItem('memberId'));
    const url = process.env.SERVER_URL + 'connect';
    let eventSource = new EventSource(url);

    eventSource.addEventListener(EVENT_SOURCE_TYPE.CONNECT, e => {
      const { data: receivedConnectData } = e;
      console.log(receivedConnectData); // "connect"
    });

    eventSource.addEventListener(EVENT_SOURCE_TYPE.NEW_NOTIFICATION, function (event) {
      if (event.data === EVENT_SOURCE_TYPE.NEW_NOTIFICATION) {
        fetchNotifications(memberId);
      }
    });

    eventSource.onerror = function (err) {
      console.error('EventSource failed:', err);
      if (eventSource.readyState === EventSource.CLOSED) {
        eventSource = new EventSource(url);
      }
    };

    return () => {
      eventSource.close();
    };
  }, []);

  useEffect(() => {
    // 알림 중 checked 값이 false 인 것이 하나라도 있으면 => isAllChecked = false
    const isAllChecked = !notifications.some(notification => !notification.checked);
    setIsAllChecked(isAllChecked);
  }, [notifications]);

  return (
    <NotificationContext.Provider
      value={{
        notifications,
        setNotifications,
        isAllChecked,
        setIsAllChecked,
        fetchNotifications,
        updateNotifications,
      }}
    >
      {children}
    </NotificationContext.Provider>
  );
};

export const useNotification = () => useContext(NotificationContext);

interface NotificationContextProps {
  notifications: NotificationTypes[];
  setNotifications: React.Dispatch<NotificationTypes[]>;
  isAllChecked: boolean;
  setIsAllChecked: React.Dispatch<boolean>;
  fetchNotifications: (memberId: number) => void;
  updateNotifications: (memberId: number) => Promise<void>;
}

interface NotificationProviderProps {
  children: React.ReactNode;
}

export interface NotificationTypes {
  notificationId: number;
  memberId: number;
  event: string;
  message: string;
  checked: boolean;
  createdAt: number;
}
