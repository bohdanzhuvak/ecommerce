import Axios from 'axios';

import {env} from '@/config/env';
import {handleResponseError} from '@/shared/lib/auth/interceptors';

export const api = Axios.create({
  baseURL: env.API_URL,
});

// Add auth token interceptor
api.interceptors.request.use(async (config) => {
  // Get token from localStorage
  const tokenData = localStorage.getItem('auth_token');
  if (tokenData) {
    try {
      const parsed = JSON.parse(tokenData);
      if (parsed.token) {
        config.headers.Authorization = `Bearer ${parsed.token}`;
      }
    } catch (error) {
      console.warn('Failed to parse auth token:', error);
    }
  }
  
  if (config.headers) {
    config.headers.Accept = 'application/json';
  }
  
  return config;
});

api.interceptors.response.use(
  (response) => response.data,
  (error) => handleResponseError(error),
);
//dev interceptor for mock data
/*api.interceptors.request.use(config=> {
  if (config.url && !config.url.endsWith('.json')){
    config.url += '.json'
  }
  return config;
})*/
