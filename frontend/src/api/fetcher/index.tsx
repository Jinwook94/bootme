import axios from 'axios';

const fetcher = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  withCredentials: true,
});

axios.defaults.withCredentials = true;

/**
 * 서버 응답에 `Login` 헤더 있으면 `Login` 헤더를 localStorage 에 저장함
 * */
fetcher.interceptors.response.use(response => {
  const loginHeaderValue = response.headers['login'];
  if (loginHeaderValue) {
    localStorage.setItem('Login', loginHeaderValue);
  }
  return response;
});

export default fetcher;
