import Axios from 'axios';

import {env} from '@/config/env';
import {handleResponseError} from '@/shared/lib/auth/interceptors';
import {TokenManager} from '@/shared/lib/auth/core/token-manager';

export const api = Axios.create({
  baseURL: env.API_URL,
});

const tokenManager = TokenManager.getInstance();

api.interceptors.request.use(async (config) => {
  console.log(config.url);
  if (!config.url?.startsWith('/auth/')) {
    try {
      const token = await tokenManager.getToken();
      if (token) {
        config.headers.Authorization = `Bearer ${token}`;
      }

    } catch (error) {
      console.warn('Failed to get auth token:', error);
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
