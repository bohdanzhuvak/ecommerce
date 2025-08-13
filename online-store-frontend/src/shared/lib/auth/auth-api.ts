import {api} from '@/shared/lib/api-client';
import {LoginInput, RegisterInput} from '@/shared/lib/auth/types';
import {AuthResponse, User} from '@/shared/types/api';

export const getUser = (): Promise<User> => {
  return api.get('/users/me');
};

// Backend does not expose a logout endpoint. We just clear local auth state.
export const logout = async (): Promise<void> => {
  return Promise.resolve();
};

export const loginWithUsernameAndPassword = (
  data: LoginInput,
): Promise<AuthResponse> => {
  return api.post('/auth/login', data, {withCredentials: true});
};

export const registerWithUsernameAndPassword = (
  data: RegisterInput,
): Promise<AuthResponse> => {
  return api.post('/auth/register', data, {withCredentials: true});
};
