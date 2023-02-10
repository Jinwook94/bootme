import axios from 'axios';

const fetcher = axios.create({
  baseURL: process.env.REACT_APP_API_URL,
  withCredentials: true,
});

axios.defaults.withCredentials = true;

export default fetcher;
