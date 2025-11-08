import { EventEmitter } from './event-emitter';
import { env } from '@/config/env';

export interface TokenData {
  token: string;
  expiresAt: number;
  refreshExpiresAt: number;
}

export class TokenManager extends EventEmitter {
  private static readonly TOKEN_KEY = 'auth_token';
  private refreshPromise: Promise<string> | null = null;
  private static instance: TokenManager | null = null;

  constructor() {
    super();
  }

  public static getInstance(): TokenManager {
    if (!TokenManager.instance) {
      TokenManager.instance = new TokenManager();
    }
    return TokenManager.instance;
  }

  public static clearInstance(): void {
    if (TokenManager.instance) {
      TokenManager.instance.destroy();
      TokenManager.instance = null;
    }
  }

  public async getToken(): Promise<string | null> {
    const tokenData = this.getTokenFromStorage();
    if (!tokenData) return null;

    if (this.isTokenExpired(tokenData)) {
      try {
        return await this.refreshToken();
      } catch (error) {
        this.clearToken();
        return null;
      }
    }

    return tokenData.token;
  }

  public setToken(
    token: string,
    expiresAt: number,
    refreshExpiresAt: number,
  ): void {
    const tokenData: TokenData = {
      token,
      expiresAt,
      refreshExpiresAt,
    };
    this.saveTokenToStorage(tokenData);
    this.emit('tokenSet', token);
  }

  public clearToken(): void {
    localStorage.removeItem(TokenManager.TOKEN_KEY);
    this.emit('tokenCleared');

    TokenManager.clearInstance();
  }

  public async refreshToken(): Promise<string> {
    if (this.refreshPromise) {
      return this.refreshPromise;
    }

    this.refreshPromise = this.performTokenRefresh();
    try {
      return await this.refreshPromise;
    } finally {
      this.refreshPromise = null;
    }
  }

  private async performTokenRefresh(): Promise<string> {
    try {
      console.log('Starting token refresh...');
      const response = await fetch(`${env.API_URL}/api/v1/auth/refresh`, {
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
      console.log('Token refresh response:', data);

      const token = data.token;
      const expiresAt = data.tokenInfo?.accessExpiresAt || Date.now() + 900000; // 15 min default
      const refreshExpiresAt =
        data.tokenInfo?.refreshExpiresAt || Date.now() + 604800000; // 7 days default

      if (!token) {
        throw new Error('Invalid token response format');
      }

      console.log('Setting new token with expiresAt:', new Date(expiresAt));

      this.setToken(token, expiresAt, refreshExpiresAt);

      this.emit('tokenRefreshed', token);
      return token;
    } catch (error) {
      console.error('Token refresh failed:', error);
      this.clearToken();
      this.emit('tokenExpired');
      throw new Error('Failed to refresh token');
    }
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

  public destroy(): void {
    this.removeAllListeners();
  }
}
