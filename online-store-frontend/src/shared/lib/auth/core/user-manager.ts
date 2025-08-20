import { EventEmitter } from './event-emitter';
import { api } from '@/shared/lib/api-client';
import { User, AuthResponse } from '@/shared/types/api';

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

  public async login(credentials: LoginCredentials): Promise<{ token: string; user: User }> {
    try {
      const response: AuthResponse = await api.post('/auth/login', credentials, { 
        withCredentials: true 
      });
      
      // For now, we'll create a mock user since AuthResponse doesn't include user data
      // In a real app, the backend should return user data with the token
      const user: User = {
        id: 1, // This should come from backend
        username: credentials.email.split('@')[0], // Temporary
        email: credentials.email,
        role: response.role
      };
      
      this.cacheUser(user);
      this.emit('userLoaded', user);
      return { token: response.token, user };
    } catch (error) {
      this.emit('userError', this.extractErrorMessage(error));
      throw error;
    }
  }

  public async register(credentials: RegisterCredentials): Promise<{ token: string; user: User }> {
    try {
      const response: AuthResponse = await api.post('/auth/register', credentials, { 
        withCredentials: true 
      });
      
      // Create user from credentials and response
      const user: User = {
        id: 1, // This should come from backend
        username: credentials.name,
        email: credentials.email,
        role: response.role
      };
      
      this.cacheUser(user);
      this.emit('userLoaded', user);
      return { token: response.token, user };
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
      // Try to call logout endpoint if it exists
      await api.post('/auth/logout');
    } catch (error) {
      // Backend might not have logout endpoint, which is fine
      console.warn('Logout endpoint not available:', error);
    } finally {
      this.clearUserCache();
      this.emit('userLoggedOut');
    }
  }

  public clearUserCache(): void {
    this.userCache = null;
    this.cacheExpiry = 0;
  }

  private async loadUserFromToken(token: string): Promise<User> {
    // Set token in headers temporarily for this request
    const response = await api.get('/users/me', {
      headers: { Authorization: `Bearer ${token}` }
    });
    return response as unknown as User;
  }

  private async fetchUserFromAPI(): Promise<User> {
    const response = await api.get('/users/me');
    return response as unknown as User;
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
