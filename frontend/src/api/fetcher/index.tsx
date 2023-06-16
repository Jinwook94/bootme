import axios from 'axios';
import { UTM_PARAMS } from '../../constants/others';

export const fetcher = axios.create({
  baseURL: process.env.SERVER_URL,
  withCredentials: true,
});

export const noCredentialsFetcher = axios.create({
  baseURL: process.env.SERVER_URL,
  withCredentials: false,
});

axios.defaults.withCredentials = true;

fetcher.interceptors.response.use(
  response => {
    if (response.headers['login'] === 'false') {
      clearLocalStorageExceptLastLocation();
    }
    return response;
  },
  error => {
    return Promise.reject(error);
  }
);

function clearLocalStorageExceptLastLocation() {
  const lastLocation = localStorage.getItem('lastLocation');
  localStorage.clear();
  if (lastLocation) {
    localStorage.setItem('lastLocation', lastLocation);
  }
}

export function appendUtmParams(url: string | undefined) {
  if (!url) {
    return '';
  }
  const separator = url.includes('?') ? '&' : '?';
  return `${url}${separator}${UTM_PARAMS}`;
}
