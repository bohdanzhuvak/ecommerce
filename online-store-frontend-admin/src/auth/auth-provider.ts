import { AuthProvider } from 'react-admin';
import { authService } from './auth-service';
import { tokenManager } from './token-manager';
import { LoginParams, Permission } from './types';
import { ERROR_MESSAGES, USER_ROLES } from './constants';

export const authProvider: AuthProvider = {
  async login(params: LoginParams) {
    try {
      const data = await authService.login(params);

      if (data.role !== USER_ROLES.ADMIN) {
        throw new Error(ERROR_MESSAGES.ACCESS_DENIED);
      }

      tokenManager.setToken(data.token);
      tokenManager.setUserInfo(data.user);
    } catch (error) {
      if (error instanceof Error) {
        throw error;
      }
      throw new Error(ERROR_MESSAGES.LOGIN_FAILED);
    }
  },

  async checkError(error) {
    const status = error.status;
    if (status === 401 || status === 403) {
      tokenManager.clearAll();
      return Promise.reject();
    }
    return Promise.resolve();
  },

  async checkAuth() {
    if (!tokenManager.getToken() || tokenManager.isTokenExpired()) {
      try {
        const data = await authService.refreshToken();
        tokenManager.setToken(data.token);
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
