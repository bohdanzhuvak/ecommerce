import { AuthError, AuthResponse, LoginParams, User } from './types.ts';
import { API_URL, ERROR_MESSAGES } from './constants.ts';

class AuthService {
  async login(credentials: LoginParams): Promise<AuthResponse> {
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
      throw new AuthError(errorText || ERROR_MESSAGES.LOGIN_FAILED);
    }

    return response.json();
  }

  async refreshToken(): Promise<AuthResponse> {
    const response = await fetch(`${API_URL}/auth/refresh`, {
      method: 'POST',
      credentials: 'include',
    });

    if (!response.ok) {
      throw new AuthError('Token refresh failed');
    }

    return response.json();
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
    const response = await fetch(`${API_URL}/auth/me`, {
      headers: { Authorization: `Bearer ${token}` },
    });

    if (!response.ok) {
      throw new AuthError('Failed to fetch user information');
    }

    return response.json();
  }
}

export const authService = new AuthService();
