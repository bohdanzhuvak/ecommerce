import {InternalAxiosRequestConfig} from 'axios';

import {useNotifications} from '@/shared/components/ui/notifications';
import {addAuthToken} from '@/shared/lib/auth/token-utils';

export const authRequestInterceptor = async (
  config: InternalAxiosRequestConfig,
) => {
  if (config.headers) {
    config.headers.Accept = 'application/json';
  }
  await addAuthToken(config);
  return config;
};

export const handleResponseError = (error: any) => {
  let message: string;
  if (error.response?.data) {
    if (typeof error.response.data === 'string') {
      message = error.response.data;
    } else if (typeof error.response.data.message === 'string') {
      message = error.response.data.message;
    } else {
      message = JSON.stringify(error.response.data);
    }
  } else {
    message = error.message || 'Unknown error';
  }
  useNotifications.getState().addNotification({
    type: 'error',
    title: 'Error',
    message,
  });

  return Promise.reject(error);
};
