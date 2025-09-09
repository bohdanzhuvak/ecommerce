import { User } from './types.ts';
import { STORAGE_KEYS } from './constants.ts';

interface TokenData {
  token: string;
  expiresAt: number;
  refreshExpiresAt: number;
}

class TokenManager {
  getToken(): string | null {
    const tokenData = this.getTokenData();
    return tokenData?.token || null;
  }

  setToken(token: string, expiresAt: number, refreshExpiresAt: number): void {
    const tokenData: TokenData = {
      token,
      expiresAt,
      refreshExpiresAt,
    };
    localStorage.setItem(STORAGE_KEYS.ACCESS_TOKEN, JSON.stringify(tokenData));
  }

  isTokenExpired(): boolean {
    const tokenData = this.getTokenData();
    if (!tokenData) return true;

    return Date.now() >= tokenData.expiresAt;
  }

  removeToken(): void {
    localStorage.removeItem(STORAGE_KEYS.ACCESS_TOKEN);
  }

  getUserInfo(): User | null {
    const userInfo = localStorage.getItem(STORAGE_KEYS.USER_INFO);
    return userInfo ? JSON.parse(userInfo) : null;
  }

  setUserInfo(userInfo: User): void {
    localStorage.setItem(STORAGE_KEYS.USER_INFO, JSON.stringify(userInfo));
  }

  removeUserInfo(): void {
    localStorage.removeItem(STORAGE_KEYS.USER_INFO);
  }

  isRefreshTokenExpired(): boolean {
    const tokenData = this.getTokenData();
    if (!tokenData) return true;

    return Date.now() >= tokenData.refreshExpiresAt;
  }

  private getTokenData(): TokenData | null {
    try {
      const stored = localStorage.getItem(STORAGE_KEYS.ACCESS_TOKEN);
      return stored ? JSON.parse(stored) : null;
    } catch {
      return null;
    }
  }

  clearAll(): void {
    this.removeToken();
    this.removeUserInfo();
  }
}

export const tokenManager = new TokenManager();
