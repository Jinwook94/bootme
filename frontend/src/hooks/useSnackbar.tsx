import React, { createContext, useContext, useState } from 'react';

const SnackbarContext = createContext<SnackbarContextProps>({
  isVisible: false,
  message: '',
  showSnackbar: () => {},
  hideSnackbar: () => {},
});

export const SnackbarProvider = ({ children }: SnackbarProviderProps) => {
  const [isVisible, setIsVisible] = useState(false);
  const [message, setMessage] = useState('');
  let timer: NodeJS.Timeout;

  const showSnackbar = (message: string) => {
    setMessage(message);
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
    <SnackbarContext.Provider value={{ isVisible, message, showSnackbar, hideSnackbar }}>
      {children}
    </SnackbarContext.Provider>
  );
};

export const useSnackbar = () => useContext(SnackbarContext);

interface SnackbarContextProps {
  isVisible: boolean;
  message: string;
  showSnackbar: (message: string) => void;
  hideSnackbar: () => void;
}

interface SnackbarProviderProps {
  children: React.ReactNode;
}
