import React, { createContext, useContext, useState } from 'react';
import { fetcher } from '../api/fetcher';
import { GOOGLE, NAVER, KAKAO } from '../constants/others';
import { useSnackbar } from './useSnackbar';
import SNACKBAR_MESSAGE, { CHECK } from '../constants/snackbar';
import { useNavigation } from './useNavigation';

const LoginContext = createContext<LoginContextProps>({
  isLogin: false,
  setIsLogin: () => {},
  isLoginModal: false,
  handleLoginModal: () => {},
  sendIdTokenToServer: () => Promise.resolve(),
  handleLoginSuccess: () => {},
  handleLogOut: () => {},
});

export const LoginProvider = ({ children }: LoginProviderProps) => {
  const { showSnackbar } = useSnackbar();
  const { goBack } = useNavigation();
  const memberId = localStorage.getItem('MemberId');
  const [isLogin, setIsLogin] = useState(() => {
    return !!memberId;
  });
  const [isLoginModal, setIsLoginModal] = useState(false);

  const handleLoginModal = () => {
    isLoginModal ? setIsLoginModal(false) : setIsLoginModal(true);
  };

  const sendIdTokenToServer = (idToken: string | undefined) => {
    return fetcher
      .post('/login', null, {
        headers: {
          Authorization: 'Bearer ' + idToken,
        },
      })
      .then(r => {
        const data = r.data.split(', ');
        data.forEach((item: string) => {
          const [key, value] = item.split('=');
          localStorage.setItem(key, value);
        });
      })
      .catch(error => {
        console.log(error);
        return Promise.reject(error);
      });
  };

  const handleLoginSuccess = async (oAuthProvider: string) => {
    await setIsLogin(true);
    if (oAuthProvider == GOOGLE) {
      setIsLoginModal(false);
      showSnackbar(SNACKBAR_MESSAGE.SUCCESS_LOGIN, CHECK);
      goBack();
    }
    if (oAuthProvider == NAVER) {
      showSnackbar(SNACKBAR_MESSAGE.SUCCESS_LOGIN, CHECK);
      goBack();
    }
    if (oAuthProvider == KAKAO) {
      showSnackbar(SNACKBAR_MESSAGE.SUCCESS_LOGIN, CHECK);
      goBack();
    }
  };

  const handleLogOut = () => {
    fetcher
      .post('/logout', null, {})
      .then(() => {
        setIsLogin(false);
        localStorage.removeItem('MemberId');
        localStorage.removeItem('NickName');
        localStorage.removeItem('ProfileImage');
        setTimeout(() => {
          window.location.reload();
        }, 500);
      })
      .catch(error => {
        console.log(error);
      });
  };

  return (
    <LoginContext.Provider
      value={{
        isLogin,
        setIsLogin,
        isLoginModal,
        handleLoginModal,
        sendIdTokenToServer,
        handleLoginSuccess,
        handleLogOut,
      }}
    >
      {children}
    </LoginContext.Provider>
  );
};

export const useLogin = () => useContext(LoginContext);

interface LoginContextProps {
  isLogin: boolean;
  setIsLogin: React.Dispatch<boolean>;
  isLoginModal: boolean;
  handleLoginModal: () => void;
  sendIdTokenToServer: (idToken: string | undefined) => Promise<void>;
  handleLoginSuccess: (oAuthProvider: string) => void;
  handleLogOut: () => void;
}

interface LoginProviderProps {
  children: React.ReactNode;
}
