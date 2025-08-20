import { EventEmitter } from './event-emitter';
import { User, AuthResponse } from '../types';
import { env } from '@/config/env';

export interface LoginCredentials {
  email: string;
  password: string;
}

export interface RegisterCredentials {
  email: string;
  password: string;
  name: string;
}

export class UserManager extends EventEmitter {
  private userCache: User | null = null;
  private cacheExpiry: number = 0;
  private static readonly CACHE_DURATION = 5 * 60 * 1000; // 5 minutes

  constructor() {
    super();
  }

  public async login(credentials: LoginCredentials): Promise<{ token: string; refreshToken: string; user: User; expiresAt: number; refreshExpiresAt: number }> {
    try {
      const response = await fetch(`${env.API_URL}/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify(credentials),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Login failed');
      }

      const data: AuthResponse = await response.json();

      // Create user from response
      const user: User = {
        id: data.user.id,
        username: data.user.username,
        email: data.user.email,
        role: data.user.role
      };

      this.cacheUser(user);
      this.emit('userLoaded', user);

      return {
        token: data.token,
        refreshToken: data.refreshToken,
        user,
        expiresAt: data.tokenInfo.expiresAt,
        refreshExpiresAt: data.tokenInfo.refreshExpiresAt
      };
    } catch (error) {
      this.emit('userError', this.extractErrorMessage(error));
      throw error;
    }
  }

  public async register(credentials: RegisterCredentials): Promise<{ token: string; refreshToken: string; user: User; expiresAt: number; refreshExpiresAt: number }> {
    try {
      const response = await fetch(`${env.API_URL}/auth/register`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify(credentials),
      });

      if (!response.ok) {
        const errorData = await response.json();
        throw new Error(errorData.message || 'Registration failed');
      }

      const data: AuthResponse = await response.json();

      // Create user from response
      const user: User = {
        id: data.user.id,
        username: data.user.username,
        email: data.user.email,
        role: data.user.role
      };

      this.cacheUser(user);
      this.emit('userLoaded', user);

      return {
        token: data.token,
        refreshToken: data.refreshToken,
        user,
        expiresAt: data.tokenInfo.expiresAt,
        refreshExpiresAt: data.tokenInfo.refreshExpiresAt
      };
    } catch (error) {
      this.emit('userError', this.extractErrorMessage(error));
      throw error;
    }
  }

  public async loadUser(): Promise<User | null> {
    try {
      // Check cache first
      if (this.isUserCacheValid()) {
        return this.userCache;
      }

      const user = await this.fetchUserFromAPI();
      if (user) {
        this.cacheUser(user);
        this.emit('userLoaded', user);
      }

      return user;
    } catch (error) {
      this.emit('userError', this.extractErrorMessage(error));
      return null;
    }
  }

  public async logout(): Promise<void> {
    try {
      // Call logout endpoint
      await fetch(`${env.API_URL}/auth/logout`, {
        method: 'POST',
        credentials: 'include',
      });
    } catch (error) {
      // Continue with logout even if API call fails
      console.warn('Logout API call failed:', error);
    } finally {
      this.clearUserCache();
      this.emit('userLoggedOut');
    }
  }

  public clearUserCache(): void {
    this.userCache = null;
    this.cacheExpiry = 0;
  }

  private async fetchUserFromAPI(): Promise<User> {
    const response = await fetch(`${env.API_URL}/auth/me`, {
      credentials: 'include',
    });

    if (!response.ok) {
      throw new Error('Failed to fetch user');
    }

    const data = await response.json();
    return data as User;
  }

  private cacheUser(user: User): void {
    this.userCache = user;
    this.cacheExpiry = Date.now() + UserManager.CACHE_DURATION;
  }

  private isUserCacheValid(): boolean {
    return this.userCache !== null && Date.now() < this.cacheExpiry;
  }

  private extractErrorMessage(error: any): string {
    if (error.response?.data) {
      if (typeof error.response.data === 'string') {
        return error.response.data;
      } else if (typeof error.response.data.message === 'string') {
        return error.response.data.message;
      } else {
        return JSON.stringify(error.response.data);
      }
    }
    return error.message || 'Unknown error occurred';
  }

  public destroy(): void {
    this.removeAllListeners();
    this.clearUserCache();
  }
}
