import { EventEmitter } from './event-emitter';
import { api } from '@/shared/lib/api-client';

export interface TokenData {
  token: string;
  expiresAt: number;
}

export class TokenManager extends EventEmitter {
  private static readonly TOKEN_KEY = 'auth_token';
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

  public setToken(token: string): void {
    const expiresAt = this.calculateExpiryTime(token);
    const tokenData: TokenData = { token, expiresAt };
    this.saveTokenToStorage(tokenData);
    this.emit('tokenSet', token);
  }

  public clearToken(): void {
    localStorage.removeItem(TokenManager.TOKEN_KEY);
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
      const response = await api.post('/auth/refresh');
      // Handle both direct response and wrapped response
      const newToken = response.token || response.data?.token || response;
      
      this.setToken(newToken);
      this.emit('tokenRefreshed', newToken);
      
      return newToken;
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

  private calculateExpiryTime(token: string): number {
    try {
      const payload = JSON.parse(atob(token.split('.')[1]));
      return payload.exp * 1000; // Convert to milliseconds
    } catch {
      // If we can't decode the token, assume it expires in 1 hour
      return Date.now() + 60 * 60 * 1000;
    }
  }

  public destroy(): void {
    this.removeAllListeners();
  }
}
