import {useNotifications} from '@/shared/components/ui/notifications';

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
  
  // Handle 401 errors (unauthorized)
  if (error.response?.status === 401) {
    // Clear auth data and redirect to login
    localStorage.removeItem('auth_token');
    localStorage.removeItem('auth_refresh_token');
    
    // Show notification
    useNotifications.getState().addNotification({
      type: 'error',
      title: 'Session expired',
      message: 'Your session has expired. Please log in to continue.',
    });
    
    // Redirect to login
    window.location.href = '/auth/login';
  } else {
    // Show regular error notification
    useNotifications.getState().addNotification({
      type: 'error',
      title: 'Error',
      message,
    });
  }

  return Promise.reject(error);
};
