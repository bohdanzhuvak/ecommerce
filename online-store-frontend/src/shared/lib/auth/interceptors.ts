import { useNotifications } from '@/shared/components/ui/notifications';
import * as axios from 'axios';

export const handleResponseError = async (error: any) => {
  // Don't show notifications for cancelled requests
  // This happens when React Query cancels pending requests during unmount
  // or when using React StrictMode in development
  if (axios.isCancel(error)) {
    return Promise.reject(error);
  }

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
