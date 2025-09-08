import { AuthError, AuthResponse, LoginParams, User } from './types';
import { API_URL, ERROR_MESSAGES } from './constants';

class AuthService {
  async login(credentials: LoginParams): Promise<AuthResponse> {
    try {
      const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        credentials: 'include',
        body: JSON.stringify({
          email: credentials.username,
          password: credentials.password,
        }),
      });

      if (!response.ok) {
        const errorText = await response.text();
        throw new Error(errorText || ERROR_MESSAGES.LOGIN_FAILED);
      }

      return response.json();
    } catch (error) {
      if (error instanceof Error) {
        throw new AuthError(error.message);
      }
      throw new AuthError(ERROR_MESSAGES.NETWORK_ERROR);
    }
  }

  async refreshToken(): Promise<AuthResponse> {
    try {
      const response = await fetch(`${API_URL}/auth/refresh`, {
        method: 'POST',
        credentials: 'include',
      });

      if (!response.ok) {
        throw new Error('Token refresh failed');
      }

      return response.json();
    } catch (error) {
      if (error instanceof Error) {
        throw new AuthError(error.message);
      }
      throw new AuthError(ERROR_MESSAGES.NETWORK_ERROR);
    }
  }

  async logout(): Promise<void> {
    try {
      await fetch(`${API_URL}/auth/logout`, {
        method: 'POST',
        credentials: 'include',
      });
    } catch (error) {
      console.error('Logout error:', error);
    }
  }

  async getCurrentUser(token: string): Promise<User> {
    try {
      const response = await fetch(`${API_URL}/auth/me`, {
        headers: { Authorization: `Bearer ${token}` },
      });

      if (!response.ok) {
        throw new Error('Failed to fetch user information');
      }

      return response.json();
    } catch (error) {
      if (error instanceof Error) {
        throw new AuthError(error.message);
      }
      throw new AuthError(ERROR_MESSAGES.NETWORK_ERROR);
    }
  }
}

export const authService = new AuthService();
