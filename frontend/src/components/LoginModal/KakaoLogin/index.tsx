import { useEffect } from 'react';
import axios from 'axios';
import qs from 'qs';
import LoginButton from '../LoginButton';

const KakaoLogin = () => {
  const REST_API_KEY = process.env.REACT_APP_KAKAO_API_KEY;
  const REDIRECT_URI = process.env.REACT_APP_REDIRECT_URI;
  const STATE = process.env.REACT_KAKAO_STATE;
  const CLIENT_SECRET = process.env.REACT_KAKAO_CLIENT_SECRET;

  const KAKAO_AUTH_URL = `https://kauth.kakao.com/oauth/authorize?client_id=${REST_API_KEY}&redirect_uri=${REDIRECT_URI}&response_type=code&state=${STATE}`;
  const code = new URL(window.location.href).searchParams.get('code');

  const getToken = async () => {
    const payload = qs.stringify({
      grant_type: 'authorization_code',
      client_id: REST_API_KEY,
      redirect_uri: REDIRECT_URI,
      code: code,
      client_secret: CLIENT_SECRET,
    });
    try {
      const res = await axios.post('https://kauth.kakao.com/oauth/token', payload);

      window.Kakao.init(REST_API_KEY);
      window.Kakao.Auth.setAccessToken(res.data.access_token);
      const data = await window.Kakao.API.request({
        url: '/v2/user/me',
      });

      console.log(
        '< Kakao Login > \n\nAuthorization Code: ' +
          code +
          '\n\nAccess Token: ' +
          res.data.access_token +
          '\n\nUser ID: ' +
          data.id +
          '\n\nUser Nickname: ' +
          data.properties.nickname
      );
      console.log('---- 카카오 로그인 응답 ----');
      console.log(res);
      console.log('-----------------------');
      const idToken = res.data.id_token;
      sendIdTokenToServer(idToken);
    } catch (err) {
      console.log(err);
    }
  };

  const sendIdTokenToServer = (idToken: string) => {
    axios
      .post('http://localhost:8080/login', null, {
        headers: {
          Authorization: 'Bearer ' + idToken,
        },
      })
      .then(response => {
        console.log('==== Axios response ====');
        console.log(response);
      })
      .catch(error => {
        console.log(error);
      });
  };

  useEffect(() => {
    if (code) {
      getToken();
    }
  }, []);

  const handleClick = () => {
    window.location.href = KAKAO_AUTH_URL;
  };

  return (
    <>
      <div onClick={handleClick}>
        <LoginButton client={'kakao'} />
      </div>
    </>
  );
};

export default KakaoLogin;
