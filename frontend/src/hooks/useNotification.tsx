import React, { createContext, useContext, useEffect, useState } from 'react';
import { fetcher } from '../api/fetcher';

const NotificationContext = createContext<NotificationContextProps>({
  notifications: [],
  setNotifications: () => {},
  isAllChecked: true,
  setIsAllChecked: () => {},
  getNotifications: () => {},
  updateNotifications: () => Promise.resolve(),
});

export const NotificationProvider = ({ children }: NotificationProviderProps) => {
  const [notifications, setNotifications] = useState<NotificationTypes[]>([]);
  const [isAllChecked, setIsAllChecked] = useState<boolean>(true);

  useEffect(() => {
    // 알림 중 checked 값이 false 인 것이 하나라도 있으면 => isAllChecked = false
    const isAllChecked = !notifications.some(notification => !notification.checked);
    setIsAllChecked(isAllChecked);
  }, [notifications]);

  const getNotifications = (memberId: number) => {
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

  return (
    <NotificationContext.Provider
      value={{
        notifications,
        setNotifications,
        isAllChecked,
        setIsAllChecked,
        getNotifications,
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
  getNotifications: (memberId: number) => void;
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
