import React, { createContext, useContext, useEffect, useState } from 'react';

const NotificationContext = createContext<NotificationContextProps>({
  notifications: [],
  setNotifications: () => {},
});

export const NotificationProvider = ({ children }: NotificationProviderProps) => {
  const [notifications, setNotifications] = useState<Notification[]>([]);
  useEffect(() => {
    setNotifications(JSON.parse(localStorage.getItem('Notifications') || '[]'));
  }, [localStorage.getItem('Notifications')]);

  return (
    <NotificationContext.Provider
      value={{
        notifications,
        setNotifications,
      }}
    >
      {children}
    </NotificationContext.Provider>
  );
};

export const useNotification = () => useContext(NotificationContext);

interface NotificationContextProps {
  notifications: Notification[];
  setNotifications: React.Dispatch<Notification[]>;
}

interface NotificationProviderProps {
  children: React.ReactNode;
}

interface Notification {
  notificationId: number;
  event: string;
  bookmarkCourseId: number;
  memberId: number;
  courseId: number;
  courseTitle: string;
  createdAt: number;
}
