import Axios, { AxiosError, AxiosRequestConfig } from 'axios';

import { env } from '@/config/env';
import { handleResponseError } from '@/shared/lib/auth/interceptors';
import { TokenManager } from '@/shared/lib/auth/core/token-manager';

/**
 * Main Axios instance for API requests
 * Used by both manual API functions and Orval-generated hooks
 */
export const api = Axios.create({
  baseURL: env.API_URL,
});

const tokenManager = TokenManager.getInstance();

// Request interceptor: Add JWT token to all non-auth requests
api.interceptors.request.use(async (config) => {
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

// Response interceptor: Extract data and handle errors
api.interceptors.response.use(
  (response) => response.data,
  (error: AxiosError) => handleResponseError(error),
);

/**
 * Orval mutator function
 * This is the custom instance used by all generated API hooks
 *
 * Note: api already extracts data in its response interceptor,
 * so we don't need to extract it again here
 */
export const customInstance = <T>(
  config: AxiosRequestConfig,
  options?: AxiosRequestConfig,
): Promise<T> => {
  const source = Axios.CancelToken.source();

  const promise = api({
    ...config,
    ...options,
    cancelToken: source.token,
  }) as Promise<T>;

  // @ts-ignore - Add cancel method for React Query compatibility
  promise.cancel = () => {
    source.cancel('Query was cancelled');
  };

  return promise;
};

export default customInstance;
