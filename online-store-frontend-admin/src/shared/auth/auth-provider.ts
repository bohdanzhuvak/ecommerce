import { AuthProvider } from 'react-admin';
import { authService } from './auth-service.ts';
import { tokenManager } from './token-manager.ts';
import { LoginParams, Permission } from './types.ts';
import { ERROR_MESSAGES, USER_ROLES } from './constants.ts';

export const authProvider: AuthProvider = {
  async login(params: LoginParams) {
    const data = await authService.login(params);

    if (data.user.role !== USER_ROLES.ADMIN) {
      throw new Error(ERROR_MESSAGES.ACCESS_DENIED);
    }

    tokenManager.setToken(
      data.token,
      data.tokenInfo.expiresAt,
      data.tokenInfo.refreshExpiresAt,
    );
    tokenManager.setUserInfo(data.user);
  },

  async checkError(error) {
    const status = error.status;

    if (status === 401 || status === 403) {
      const token = tokenManager.getToken();
      if (
        !token ||
        tokenManager.isTokenExpired() ||
        tokenManager.isRefreshTokenExpired()
      ) {
        tokenManager.clearAll();
        return Promise.reject();
      } else {
        return Promise.resolve();
      }
    }
    return Promise.resolve();
  },

  async checkAuth() {
    const token = tokenManager.getToken();

    if (!token) {
      tokenManager.clearAll();
      return Promise.reject({ message: ERROR_MESSAGES.TOKEN_EXPIRED });
    }

    const isTokenExpired = tokenManager.isTokenExpired();
    const isRefreshTokenExpired = tokenManager.isRefreshTokenExpired();

    if (isRefreshTokenExpired) {
      tokenManager.clearAll();
      return Promise.reject({ message: ERROR_MESSAGES.TOKEN_EXPIRED });
    }

    if (isTokenExpired) {
      try {
        const data = await authService.refreshToken();
        tokenManager.setToken(
          data.token,
          data.tokenInfo.expiresAt,
          data.tokenInfo.refreshExpiresAt,
        );
        tokenManager.setUserInfo(data.user);
      } catch {
        tokenManager.clearAll();
        return Promise.reject({ message: ERROR_MESSAGES.TOKEN_EXPIRED });
      }
    }

    return Promise.resolve();
  },

  async logout() {
    await authService.logout();
    tokenManager.clearAll();
    return Promise.resolve();
  },

  async getIdentity() {
    let userInfo = tokenManager.getUserInfo();

    if (!userInfo) {
      const token = tokenManager.getToken();
      if (!token) {
        throw new Error('No token found');
      }

      userInfo = await authService.getCurrentUser(token);
      tokenManager.setUserInfo(userInfo);
    }

    return Promise.resolve(userInfo);
  },

  async getPermissions(): Promise<Permission> {
    const userInfo = tokenManager.getUserInfo();

    if (!userInfo) {
      return Promise.resolve({ role: '', canAccess: false });
    }

    return Promise.resolve({
      role: userInfo.role,
      canAccess: userInfo.role === USER_ROLES.ADMIN,
    });
  },

  async handleCallback() {
    return Promise.resolve();
  },
};
