import { useLogin } from '../hooks/useLogin';
import { Outlet, useNavigate } from 'react-router-dom';

const PublicRoute = () => {
  const { isLogin } = useLogin();
  const navigate = useNavigate();

  if (isLogin) {
    /* todo 이미 로그인된 상태 설명하는 스낵바 추가 */
    navigate(-1);
  }
  return <Outlet />;
};

export default PublicRoute;
