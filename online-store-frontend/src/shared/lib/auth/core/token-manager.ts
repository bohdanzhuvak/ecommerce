import { EventEmitter } from './event-emitter';
import { env } from '@/config/env';

export interface TokenData {
  token: string;
  refreshToken: string;
  expiresAt: number;
  refreshExpiresAt: number;
}

export class TokenManager extends EventEmitter {
  private static readonly TOKEN_KEY = 'auth_token';
  private static readonly REFRESH_TOKEN_KEY = 'auth_refresh_token';
  private static readonly REFRESH_THRESHOLD = 5 * 60 * 1000; // 5 minutes before expiry
  private refreshPromise: Promise<string> | null = null;

  constructor() {
    super();
  }

  public getToken(): string | null {
    const tokenData = this.getTokenFromStorage();
    if (!tokenData) return null;

    if (this.isTokenExpired(tokenData)) {
      this.clearToken();
      return null;
    }

    // Check if token needs refresh
    if (this.shouldRefreshToken(tokenData)) {
      this.refreshTokenAsync();
    }

    return tokenData.token;
  }

  public getRefreshToken(): string | null {
    const tokenData = this.getTokenFromStorage();
    return tokenData?.refreshToken || null;
  }

  public setToken(token: string, refreshToken: string, expiresAt: number, refreshExpiresAt: number): void {
    const tokenData: TokenData = {
      token,
      refreshToken,
      expiresAt,
      refreshExpiresAt
    };
    this.saveTokenToStorage(tokenData);
    this.emit('tokenSet', token);
  }

  public clearToken(): void {
    localStorage.removeItem(TokenManager.TOKEN_KEY);
    localStorage.removeItem(TokenManager.REFRESH_TOKEN_KEY);
    this.emit('tokenCleared');
  }

  public async refreshToken(): Promise<string> {
    if (this.refreshPromise) {
      return this.refreshPromise;
    }

    this.refreshPromise = this.performTokenRefresh();
    try {
      const newToken = await this.refreshPromise;
      return newToken;
    } finally {
      this.refreshPromise = null;
    }
  }

  private async performTokenRefresh(): Promise<string> {
    try {
      const refreshToken = this.getRefreshToken();
      if (!refreshToken) {
        throw new Error('No refresh token available');
      }

      const response = await fetch(`${env.API_URL}/auth/refresh`, {
        method: 'POST',
        credentials: 'include', // Include cookies
        headers: {
          'Content-Type': 'application/json',
        },
      });

      if (!response.ok) {
        throw new Error('Failed to refresh token');
      }

      const data = await response.json();

      // Update tokens
      this.setToken(
        data.token,
        data.refreshToken,
        data.tokenInfo.expiresAt,
        data.tokenInfo.refreshExpiresAt
      );

      this.emit('tokenRefreshed', data.token);
      return data.token;
    } catch (error) {
      this.clearToken();
      this.emit('tokenExpired');
      throw new Error('Failed to refresh token');
    }
  }

  private refreshTokenAsync(): void {
    // Don't await, let it run in background
    this.refreshToken().catch(error => {
      console.warn('Background token refresh failed:', error);
    });
  }

  private getTokenFromStorage(): TokenData | null {
    try {
      const stored = localStorage.getItem(TokenManager.TOKEN_KEY);
      return stored ? JSON.parse(stored) : null;
    } catch {
      return null;
    }
  }

  private saveTokenToStorage(tokenData: TokenData): void {
    localStorage.setItem(TokenManager.TOKEN_KEY, JSON.stringify(tokenData));
  }

  private isTokenExpired(tokenData: TokenData): boolean {
    return Date.now() >= tokenData.expiresAt;
  }

  private shouldRefreshToken(tokenData: TokenData): boolean {
    return Date.now() >= (tokenData.expiresAt - TokenManager.REFRESH_THRESHOLD);
  }

  public destroy(): void {
    this.removeAllListeners();
  }
}
