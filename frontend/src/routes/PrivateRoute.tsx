import { useLogin } from '../hooks/useLogin';
import { Outlet, Navigate } from 'react-router-dom';
import PATH from '../constants/path';

const PrivateRoute = () => {
  const { isLogin } = useLogin();

  if (!isLogin) {
    return <Navigate to={PATH.LOGIN} />;
  }
  return <Outlet />;
};

export default PrivateRoute;
