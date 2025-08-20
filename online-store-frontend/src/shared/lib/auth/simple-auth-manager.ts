import { AuthState, User, AuthEvents } from './types';

export class SimpleAuthManager {
  private static instance: SimpleAuthManager;
  private _state: AuthState = {
    isAuthenticated: false,
    user: null,
    isLoading: false,
    error: null
  };

  private listeners: { [key: string]: Function[] } = {};

  private constructor() {}

  public static getInstance(): SimpleAuthManager {
    if (!SimpleAuthManager.instance) {
      SimpleAuthManager.instance = new SimpleAuthManager();
    }
    return SimpleAuthManager.instance;
  }

  public get state(): AuthState {
    return { ...this._state };
  }

  public on(event: string, listener: Function): void {
    if (!this.listeners[event]) {
      this.listeners[event] = [];
    }
    this.listeners[event].push(listener);
  }

  public off(event: string, listener?: Function): void {
    if (!this.listeners[event]) return;
    
    if (listener) {
      this.listeners[event] = this.listeners[event].filter(l => l !== listener);
    } else {
      delete this.listeners[event];
    }
  }

  private emit(event: string, ...args: any[]): void {
    if (!this.listeners[event]) return;
    
    this.listeners[event].forEach(listener => {
      try {
        listener(...args);
      } catch (error) {
        console.error(`Error in event listener for ${event}:`, error);
      }
    });
  }

  public async initialize(): Promise<void> {
    try {
      this._state.isLoading = true;
      this.emit(AuthEvents.STATE_CHANGED, this._state);

      // Check if user is already logged in (from localStorage)
      const token = localStorage.getItem('auth_token');
      if (token) {
        // Try to load user data
        try {
          const userData = localStorage.getItem('auth_user');
          if (userData) {
            const user = JSON.parse(userData);
            this._state.user = user;
            this._state.isAuthenticated = true;
          }
        } catch (error) {
          // Clear invalid data
          localStorage.removeItem('auth_token');
          localStorage.removeItem('auth_user');
        }
      }
    } catch (error) {
      this._state.error = error instanceof Error ? error.message : 'Unknown error';
    } finally {
      this._state.isLoading = false;
      this.emit(AuthEvents.STATE_CHANGED, this._state);
    }
  }

  public async login(credentials: { email: string; password: string }): Promise<User> {
    try {
      this._state.isLoading = true;
      this._state.error = null;
      this.emit(AuthEvents.STATE_CHANGED, this._state);

      // Mock login - replace with real API call
      const mockUser: User = {
        id: 1,
        username: credentials.email.split('@')[0],
        email: credentials.email,
        role: 'USER'
      };

      const mockToken = 'mock_token_' + Date.now();
      
      // Store in localStorage
      localStorage.setItem('auth_token', mockToken);
      localStorage.setItem('auth_user', JSON.stringify(mockUser));
      
      this._state.user = mockUser;
      this._state.isAuthenticated = true;
      this._state.error = null;
      
      this.emit(AuthEvents.LOGIN_SUCCESS, mockUser);
      this.emit(AuthEvents.STATE_CHANGED, this._state);
      
      return mockUser;
    } catch (error) {
      this._state.error = error instanceof Error ? error.message : 'Login failed';
      this.emit(AuthEvents.LOGIN_ERROR, this._state.error);
      this.emit(AuthEvents.STATE_CHANGED, this._state);
      throw error;
    } finally {
      this._state.isLoading = false;
      this.emit(AuthEvents.STATE_CHANGED, this._state);
    }
  }

  public async register(credentials: { email: string; password: string; name: string }): Promise<User> {
    try {
      this._state.isLoading = true;
      this._state.error = null;
      this.emit(AuthEvents.STATE_CHANGED, this._state);

      // Mock registration - replace with real API call
      const mockUser: User = {
        id: 1,
        username: credentials.name,
        email: credentials.email,
        role: 'USER'
      };

      const mockToken = 'mock_token_' + Date.now();
      
      // Store in localStorage
      localStorage.setItem('auth_token', mockToken);
      localStorage.setItem('auth_user', JSON.stringify(mockUser));
      
      this._state.user = mockUser;
      this._state.isAuthenticated = true;
      this._state.error = null;
      
      this.emit(AuthEvents.REGISTER_SUCCESS, mockUser);
      this.emit(AuthEvents.STATE_CHANGED, this._state);
      
      return mockUser;
    } catch (error) {
      this._state.error = error instanceof Error ? error.message : 'Registration failed';
      this.emit(AuthEvents.REGISTER_ERROR, this._state.error);
      this.emit(AuthEvents.STATE_CHANGED, this._state);
      throw error;
    } finally {
      this._state.isLoading = false;
      this.emit(AuthEvents.STATE_CHANGED, this._state);
    }
  }

  public async logout(): Promise<void> {
    // Clear localStorage
    localStorage.removeItem('auth_token');
    localStorage.removeItem('auth_user');
    
    this._state.user = null;
    this._state.isAuthenticated = false;
    this._state.error = null;
    
    this.emit(AuthEvents.LOGOUT_SUCCESS);
    this.emit(AuthEvents.STATE_CHANGED, this._state);
  }

  public canAccess(requiredRoles: string[] = []): boolean {
    if (!this._state.isAuthenticated || !this._state.user) {
      return false;
    }

    if (requiredRoles.length === 0) {
      return true;
    }

    return requiredRoles.includes(this._state.user.role);
  }

  public getRedirectPath(originalPath: string): string {
    // Simple redirect logic
    if (!this._state.isAuthenticated) {
      return `/auth/login?redirectTo=${encodeURIComponent(originalPath)}`;
    }

    if (this._state.user?.role === 'ADMIN' && originalPath.startsWith('/admin')) {
      return originalPath;
    }

    if (this._state.user?.role === 'USER' && !originalPath.startsWith('/admin')) {
      return originalPath;
    }

    // Redirect based on role
    if (this._state.user?.role === 'ADMIN') {
      return '/admin';
    } else {
      return '/';
    }
  }

  public removeAllListeners(): void {
    this.listeners = {};
  }

  public destroy(): void {
    this.removeAllListeners();
  }
}
