import React, { createContext, useContext, useState } from 'react';
import fetcher from '../api/fetcher';

const LoginContext = createContext<LoginContextProps>({
  isLoginModal: false,
  handleLoginModal: () => {},
  sendIdTokenToServer: () => {},
});

export const LoginProvider = ({ children }: LoginProviderProps) => {
  const [isLoginModal, setIsLoginModal] = useState(false);
  const handleLoginModal = () => {
    isLoginModal ? setIsLoginModal(false) : setIsLoginModal(true);
  };

  const sendIdTokenToServer = (idToken: string | undefined) => {
    fetcher
      .post('/login', null, {
        headers: {
          Authorization: 'Bearer ' + idToken,
        },
      })
      .catch(error => {
        console.log(error);
      });
  };

  return (
    <LoginContext.Provider
      value={{
        isLoginModal,
        handleLoginModal,
        sendIdTokenToServer,
      }}
    >
      {children}
    </LoginContext.Provider>
  );
};

export const useLogin = () => useContext(LoginContext);

interface LoginContextProps {
  isLoginModal: boolean;
  handleLoginModal: () => void;
  sendIdTokenToServer: (idToken: string | undefined) => void;
}

interface LoginProviderProps {
  children: React.ReactNode;
}
