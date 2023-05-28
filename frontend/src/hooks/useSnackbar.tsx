import React, { createContext, useContext, useState } from 'react';

const SnackbarContext = createContext<SnackbarContextProps>({
  isVisible: false,
  message: '',
  displayIcon: '',
  showSnackbar: () => {},
  hideSnackbar: () => {},
});

export const SnackbarProvider = ({ children }: SnackbarProviderProps) => {
  const [isVisible, setIsVisible] = useState(false);
  const [message, setMessage] = useState('');
  const [displayIcon, setDisplayIcon] = useState('');
  let timer: NodeJS.Timeout;

  const showSnackbar = (message: string, displayIcon: string) => {
    setMessage(message);
    setDisplayIcon(displayIcon);
    setIsVisible(true);

    timer = setTimeout(() => {
      hideSnackbar();
    }, 1500);
  };

  const hideSnackbar = () => {
    setMessage('');
    setIsVisible(false);
    clearTimeout(timer);
  };
  return (
    <SnackbarContext.Provider value={{ isVisible, message, displayIcon, showSnackbar, hideSnackbar }}>
      {children}
    </SnackbarContext.Provider>
  );
};

export const useSnackbar = () => useContext(SnackbarContext);

interface SnackbarContextProps {
  isVisible: boolean;
  message: string;
  displayIcon: string;
  showSnackbar: (message: string, displayIcon: string) => void;
  hideSnackbar: () => void;
}

interface SnackbarProviderProps {
  children: React.ReactNode;
}
