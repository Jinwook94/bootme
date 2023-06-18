import React, { createContext, useContext, useEffect, useState } from 'react';
import { fetcher } from '../api/fetcher';
import { useSnackbar } from './useSnackbar';
import SNACKBAR_MESSAGE, { CHECK } from '../constants/snackbar';
import { useLocation, useNavigate } from 'react-router-dom';
import PATH from '../constants/path';

const LoginContext = createContext<LoginContextProps>({
  isLogin: false,
  setIsLogin: () => {},
  isLoginModal: false,
  handleLoginModal: () => {},
  sendIdTokenToServer: () => Promise.resolve(),
  handleNaverLogin: () => Promise.resolve(),
  handleLoginSuccess: () => {},
  handleLogOut: () => {},
});

export const LoginProvider = ({ children }: LoginProviderProps) => {
  const { showSnackbar } = useSnackbar();
  const navigate = useNavigate();
  const location = useLocation();
  const memberId = localStorage.getItem('memberId');
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
        storeUserInfoToLocalStorage(r.data);
      })
      .catch(error => {
        return Promise.reject(error);
      });
  };

  const handleNaverLogin = (naverOauthUrl: string) => {
    return fetcher
      .post(`/login/naver`, { url: naverOauthUrl })
      .then(r => {
        storeUserInfoToLocalStorage(r.data);
      })
      .catch(error => {
        console.error('네이버 엑세스 토큰 요청 실패', error);
        throw error;
      });
  };

  const storeUserInfoToLocalStorage = (userInfo: LoginResponse) => {
    for (const key in userInfo) {
      localStorage.setItem(key, (userInfo as any)[key]);
    }
  };

  const handleLoginSuccess = async () => {
    await setIsLogin(true);
    setIsLoginModal(false);
    showSnackbar(SNACKBAR_MESSAGE.SUCCESS_LOGIN, CHECK);

    const lastLocation = localStorage.getItem('lastLocation');
    navigate(lastLocation || PATH.HOME);
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

  useEffect(() => {
    if (
      location.pathname !== PATH.LOGIN &&
      location.pathname !== PATH.OAUTH.NAVER &&
      location.pathname !== PATH.OAUTH.KAKAO &&
      location.pathname !== PATH.POST.WRITE
    ) {
      localStorage.setItem('lastLocation', location.pathname);
    }
  }, [location]);

  return (
    <LoginContext.Provider
      value={{
        isLogin,
        setIsLogin,
        isLoginModal,
        handleLoginModal,
        sendIdTokenToServer,
        handleNaverLogin,
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
  handleNaverLogin: (naverOauthUrl: string) => Promise<void>;
  handleLoginSuccess: () => void;
  handleLogOut: () => void;
}

interface LoginProviderProps {
  children: React.ReactNode;
}

interface LoginResponse {
  memberId: number;
  email: string;
  nickName: string;
  profileImage: string;
}
