import { EventEmitter } from './event-emitter';
import { TokenManager } from './token-manager';
import { UserManager } from './user-manager';
import { AuthEvents, AuthState, User } from '@/shared/types';
import { getUserProfile } from '@/shared/api';
import { AxiosError } from 'axios';

export class AuthManager extends EventEmitter {
  private static instance: AuthManager;
  private tokenManager: TokenManager;
  private userManager: UserManager;
  private _state: AuthState = {
    isAuthenticated: false,
    user: null,
    isLoading: false,
    error: null,
  };
  private queryClient: any = null;

  private constructor() {
    super();
    this.tokenManager = TokenManager.getInstance();
    this.userManager = new UserManager();

    this.setupEventListeners();
  }

  public static getInstance(): AuthManager {
    if (!AuthManager.instance) {
      AuthManager.instance = new AuthManager();
    }
    return AuthManager.instance;
  }

  public setQueryClient(queryClient: any): void {
    this.queryClient = queryClient;
  }

  private setupEventListeners(): void {
    this.tokenManager.on('tokenExpired', () => {
      this.handleTokenExpired();
    });

    this.tokenManager.on('tokenRefreshed', (token: string) => {
      this.handleTokenRefreshed(token);
    });

    this.userManager.on('userLoaded', (user: User) => {
      this.handleUserLoaded(user);
    });

    this.userManager.on('userError', (error: string) => {
      this.handleUserError(error);
    });
  }

  public get state(): AuthState {
    return { ...this._state };
  }

  public async initialize(): Promise<void> {
    try {
      this._state.isLoading = true;
      this.emit(AuthEvents.STATE_CHANGED, this._state);

      const token = await this.tokenManager.getToken();
      if (token) {
        let user = this.userManager.getCachedUser();

        // If cache is empty but token exists, fetch user from server
        // This happens after page refresh when memory cache is cleared
        if (!user) {
          try {
            const userData = await getUserProfile();

            // Convert UserResponse to User type
            user = {
              id: userData.id || '',
              email: userData.email || '',
              role: (userData.role || 'USER') as 'ADMIN' | 'USER',
            };

            // Cache the user for subsequent requests
            this.userManager.setCachedUser(user);
          } catch (error) {
            // If fetching user fails, token is likely invalid or expired
            if (error instanceof AxiosError) {
              if (
                error.response?.status === 401 ||
                error.response?.status === 403
              ) {
                // Token is definitely invalid
                this.tokenManager.clearToken();
                this._state.error = 'Session expired. Please log in again.';
              } else if (!error.response) {
                // Network error - don't clear token immediately
                this._state.error =
                  'Failed to load user data. Please check your connection.';
              } else {
                // Other error - clear token to be safe
                this.tokenManager.clearToken();
                this._state.error = 'Failed to load user data.';
              }
            } else {
              this.tokenManager.clearToken();
              this._state.error = 'An unexpected error occurred.';
            }
            throw error;
          }
        }

        // Check if user is admin
        if (user.role === 'ADMIN') {
          this.tokenManager.clearToken();
          this._state.user = null;
          this._state.isAuthenticated = false;
          this._state.error =
            'Administrators cannot log into the client application. Please use the control panel instead.';
          this.emit(AuthEvents.STATE_CHANGED, this._state);
          throw new Error(this._state.error);
        }

        this._state.user = user;
        this._state.isAuthenticated = true;
      }
    } catch (error) {
      this._state.error =
        error instanceof Error ? error.message : 'Unknown error';
      this.tokenManager.clearToken();
    } finally {
      this._state.isLoading = false;
      this.emit(AuthEvents.STATE_CHANGED, this._state);
    }
  }

  public async login(credentials: {
    email: string;
    password: string;
  }): Promise<User> {
    try {
      this._state.isLoading = true;
      this._state.error = null;
      this.emit(AuthEvents.STATE_CHANGED, this._state);

      const { token, user, expiresAt, refreshExpiresAt } =
        await this.userManager.login(credentials);

      if (user.role === 'ADMIN') {
        this._state.isLoading = false;
        this._state.isAuthenticated = false;
        this._state.user = null;
        this._state.error =
          'Administrators cannot log into the client application. Please use the control panel instead.';
        this.emit(AuthEvents.LOGIN_ERROR, this._state.error);
        this.emit(AuthEvents.STATE_CHANGED, this._state);
        throw new Error(this._state.error);
      }

      this.tokenManager.setToken(token, expiresAt, refreshExpiresAt);

      this._state.user = user;
      this._state.isAuthenticated = true;
      this._state.error = null;

      this.emit(AuthEvents.LOGIN_SUCCESS, user);
      this.emit(AuthEvents.STATE_CHANGED, this._state);

      return user;
    } catch (error) {
      this._state.error =
        error instanceof Error ? error.message : 'Login failed';
      this.emit(AuthEvents.LOGIN_ERROR, this._state.error);
      this.emit(AuthEvents.STATE_CHANGED, this._state);
      throw error;
    } finally {
      this._state.isLoading = false;
      this.emit(AuthEvents.STATE_CHANGED, this._state);
    }
  }

  public async register(credentials: {
    email: string;
    password: string;
    firstName: string;
    lastName: string;
  }): Promise<User> {
    try {
      this._state.isLoading = true;
      this._state.error = null;
      this.emit(AuthEvents.STATE_CHANGED, this._state);

      const { token, user, expiresAt, refreshExpiresAt } =
        await this.userManager.register(credentials);
      this.tokenManager.setToken(token, expiresAt, refreshExpiresAt);

      this._state.user = user;
      this._state.isAuthenticated = true;
      this._state.error = null;

      this.emit(AuthEvents.REGISTER_SUCCESS, user);
      this.emit(AuthEvents.STATE_CHANGED, this._state);

      return user;
    } catch (error) {
      this._state.error =
        error instanceof Error ? error.message : 'Registration failed';
      this.emit(AuthEvents.REGISTER_ERROR, this._state.error);
      this.emit(AuthEvents.STATE_CHANGED, this._state);
      throw error;
    } finally {
      this._state.isLoading = false;
      this.emit(AuthEvents.STATE_CHANGED, this._state);
    }
  }

  public async logout(): Promise<void> {
    try {
      await this.userManager.logout();
    } catch (error) {
      console.warn('Logout API call failed:', error);
    } finally {
      this.tokenManager.clearToken();
      this._state.user = null;
      this._state.isAuthenticated = false;
      this._state.error = null;

      if (this.queryClient && typeof this.queryClient.clear === 'function') {
        try {
          this.queryClient.clear();
        } catch (error) {
          console.warn('Failed to clear query cache:', error);
        }
      }

      this.emit(AuthEvents.LOGOUT_SUCCESS);
      this.emit(AuthEvents.STATE_CHANGED, this._state);
    }
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
    if (!this._state.isAuthenticated) {
      return `/auth/login?redirectTo=${encodeURIComponent(originalPath)}`;
    }

    return originalPath;
  }

  private handleTokenExpired(): void {
    this._state.isAuthenticated = false;
    this._state.user = null;
    this.emit(AuthEvents.TOKEN_EXPIRED);
    this.emit(AuthEvents.STATE_CHANGED, this._state);
  }

  private handleTokenRefreshed(token: string): void {
    this.emit(AuthEvents.TOKEN_REFRESHED, token);
  }

  private handleUserLoaded(user: User): void {
    this._state.user = user;
    this._state.isAuthenticated = true;
    this.emit(AuthEvents.USER_LOADED, user);
    this.emit(AuthEvents.STATE_CHANGED, this._state);
  }

  private handleUserError(error: string): void {
    this._state.error = error;
    this.emit(AuthEvents.USER_ERROR, error);
    this.emit(AuthEvents.STATE_CHANGED, this._state);
  }

  public destroy(): void {
    this.removeAllListeners();
    this.tokenManager.destroy();
    this.userManager.destroy();
  }
}
