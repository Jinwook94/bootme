import axios from 'axios';

export const fetcher = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  withCredentials: true,
});

export const noCredentialsFetcher = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  withCredentials: false,
});

axios.defaults.withCredentials = true;
