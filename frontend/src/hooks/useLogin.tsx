import React, { createContext, useContext, useState } from 'react';
import { useNavigate } from 'react-router-dom';
import { fetcher, noCredentialsFetcher } from '../api/fetcher';
import PATH from '../constants/path';

const LoginContext = createContext<LoginContextProps>({
  isLogin: false,
  setIsLogin: () => {},
  isLoginModal: false,
  handleLoginModal: () => {},
  sendIdTokenToServer: () => Promise.resolve(),
  handleLogOut: () => {},
  handleLoginSuccess: () => {},
});

export const LoginProvider = ({ children }: LoginProviderProps) => {
  const [isLogin, setIsLogin] = useState(false);
  const navigate = useNavigate();

  const handleLoginSuccess = async (oAuthProvider: string) => {
    await setIsLogin(true);
    if (oAuthProvider == 'google') {
      navigate(PATH.HOME);
      setIsLoginModal(false);
    }
    if (oAuthProvider == 'kakao') navigate(PATH.HOME);
    if (oAuthProvider == 'naver') navigate(PATH.HOME);
  };

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
      });
  };

  const handleLogOut = () => {
    fetcher
      .post('/logout', null, {})
      .then(() => {
        setIsLogin(false);
        localStorage.removeItem('MemberId');
        localStorage.removeItem('NickName');
        localStorage.removeItem('ProfileImage');
        navigate(PATH.HOME);
        window.location.reload();
      })
      .catch(error => {
        console.log(error);
      });
  };

  fetcher.interceptors.response.use(response => {
    const loginHeaderValue: boolean = response.headers['login'] === 'true';
    setIsLogin(loginHeaderValue);
    return response;
  });

  noCredentialsFetcher.interceptors.response.use(response => {
    const loginHeaderValue: boolean = response.headers['login'] === 'true';
    setIsLogin(loginHeaderValue);
    return response;
  });

  return (
    <LoginContext.Provider
      value={{
        isLogin,
        setIsLogin,
        isLoginModal,
        handleLoginModal,
        sendIdTokenToServer,
        handleLogOut,
        handleLoginSuccess,
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
  handleLogOut: () => void;
  handleLoginSuccess: (oAuthProvider: string) => void;
}

interface LoginProviderProps {
  children: React.ReactNode;
}
