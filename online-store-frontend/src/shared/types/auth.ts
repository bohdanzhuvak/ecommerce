import { z } from 'zod';
import { User } from './entities';

export const loginInputSchema = z.object({
  email: z.string().email(),
  password: z.string().min(6),
});

export const registerInputSchema = z.object({
  email: z.string().email(),
  password: z.string().min(6),
  firstName: z.string().min(3).max(50),
  lastName: z.string().min(3).max(50),
});

export type LoginInput = z.infer<typeof loginInputSchema>;
export type RegisterInput = z.infer<typeof registerInputSchema>;

export interface AuthState {
  isAuthenticated: boolean;
  user: User | null;
  isLoading: boolean;
  error: string | null;
}

export interface AuthResponse {
  token: string;
  user: User;
  tokenInfo: TokenInfo;
}

export interface TokenInfo {
  accessExpiresAt: number;
  refreshExpiresAt: number;
}

export const AuthEvents = {
  STATE_CHANGED: 'stateChanged',
  LOGIN_SUCCESS: 'loginSuccess',
  LOGIN_ERROR: 'loginError',
  REGISTER_SUCCESS: 'registerSuccess',
  REGISTER_ERROR: 'registerError',
  LOGOUT_SUCCESS: 'logoutSuccess',
  TOKEN_EXPIRED: 'tokenExpired',
  TOKEN_REFRESHED: 'tokenRefreshed',
  USER_LOADED: 'userLoaded',
  USER_ERROR: 'userError',
} as const;

export interface AuthContextValue {
  state: AuthState;
  login: (credentials: LoginInput) => Promise<User>;
  register: (credentials: RegisterInput) => Promise<User>;
  logout: () => Promise<void>;
  canAccess: (requiredRoles?: string[]) => boolean;
  getRedirectPath: (originalPath: string) => string;
}
