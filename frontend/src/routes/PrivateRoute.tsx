import { useLogin } from '../hooks/useLogin';
import { Outlet, Navigate } from 'react-router-dom';
import PATH from '../constants/path';

const PrivateRoute = () => {
  const { isLogin } = useLogin();

  if (!isLogin) {
    // todo: 로그인이 필요한 기능임을 설명하는 스낵바 추가
    return <Navigate to={PATH.LOGIN} />;
  }
  return <Outlet />;
};

export default PrivateRoute;
