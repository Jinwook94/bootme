import axios from 'axios';

const API_URL = 'http://localhost:8080';

const fetcher = axios.create();
fetcher.defaults.baseURL = API_URL;

export default fetcher;
