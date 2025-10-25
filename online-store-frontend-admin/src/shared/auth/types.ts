export interface LoginParams {
  username: string;
  password: string;
}

export interface User {
  id: string;
  email: string;
  role: string;
}

export interface TokenInfo {
  expiresAt: number;
  refreshExpiresAt: number;
  tokenType: string;
}

export interface AuthResponse {
  token: string;
  user: User;
  tokenInfo: TokenInfo;
}

export class AuthError extends Error {
  public status?: number;
  public code?: string;

  constructor(message: string, status?: number, code?: string) {
    super(message);
    this.status = status;
    this.code = code;
    this.name = 'AuthError';

    Object.setPrototypeOf(this, AuthError.prototype);
  }
}

export interface Permission {
  role: string;
  canAccess: boolean;
}
