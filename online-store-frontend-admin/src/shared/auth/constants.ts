export const API_URL = 'http://localhost:8080/api/v1';

export const STORAGE_KEYS = {
  ACCESS_TOKEN: 'accessToken',
  USER_INFO: 'userInfo',
  TOKEN_EXPIRY: 'tokenExpiry',
  REFRESH_EXPIRY: 'refreshExpiry',
} as const;

export const USER_ROLES = {
  ADMIN: 'ADMIN',
  USER: 'USER',
} as const;

export const ERROR_MESSAGES = {
  LOGIN_FAILED: 'Login failed. Please check your credentials.',
  ACCESS_DENIED: 'Access denied. Administrator privileges required.',
  TOKEN_EXPIRED: 'Your session has expired. Please log in again.',
  NETWORK_ERROR: 'Network error. Please check your connection.',
  UNEXPECTED_ERROR: 'An unexpected error occurred.',
} as const;
