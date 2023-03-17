import React, { createContext, useContext, useEffect, useState } from 'react';
import { fetcher } from '../api/fetcher';
import { SIGN_UP } from '../constants/webhook';

const NotificationContext = createContext<NotificationContextProps>({
  notifications: [],
  setNotifications: () => {},
  isAllChecked: true,
  setIsAllChecked: () => {},
  getNotifications: () => {},
  sendNotification: () => {},
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

  const sendNotification = (memberId: number, event: string) => {
    fetcher.post(`/notifications/${memberId}?event=${event}`).catch(error => {
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
    const isNewMember: boolean = localStorage.getItem('IsNewMember') === 'true';
    const memberId = Number(localStorage.getItem('MemberId'));
    if (isNewMember) {
      sendNotification(memberId, SIGN_UP);
      localStorage.removeItem('IsNewMember');
    }
  }, [localStorage.getItem('IsNewMember')]);

  return (
    <NotificationContext.Provider
      value={{
        notifications,
        setNotifications,
        isAllChecked,
        setIsAllChecked,
        getNotifications,
        sendNotification,
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
  sendNotification: (memberId: number, event: string) => void;
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
