import {useNotifications} from '@/shared/components/ui/notifications';

export const handleResponseError = async (error: any) => {
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
