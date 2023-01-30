import React, { createContext, useContext, useState } from 'react';

const LoginContext = createContext<LoginContextProps>({
  isLoginModal: false,
  handleLoginModal: () => {},
});

export const LoginProvider = ({ children }: LoginProviderProps) => {
  const [isLoginModal, setIsLoginModal] = useState(false);
  const handleLoginModal = () => {
    isLoginModal ? setIsLoginModal(false) : setIsLoginModal(true);
  };

  return (
    <LoginContext.Provider
      value={{
        isLoginModal,
        handleLoginModal,
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
}

interface LoginProviderProps {
  children: React.ReactNode;
}

// import { useState } from 'react';
//
// const useLogin = () => {
//   const [isLoginModal, setIsLoginModal] = useState(false);
//   const handleLoginModal = () => {
//     isLoginModal ? setIsLoginModal(false) : setIsLoginModal(true);
//     console.log(isLoginModal);
//   };
//   return { isLoginModal, handleLoginModal };
// };
//
// export default useLogin;
