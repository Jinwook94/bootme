import axios from 'axios';

export const fetcher = axios.create({
  baseURL: process.env.SERVER_URL,
  withCredentials: true,
});

export const noCredentialsFetcher = axios.create({
  baseURL: process.env.SERVER_URL,
  withCredentials: false,
});

axios.defaults.withCredentials = true;
