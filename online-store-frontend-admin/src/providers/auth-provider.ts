import { AuthProvider } from 'react-admin';

const API_URL = 'http://localhost:8080/api/v1';

interface LoginParams {
  username: string;
  password: string;
}

interface AuthResponse {
  token: string;
  role: string;
  user: {
    id: number;
    username: string;
    email: string;
    role: string;
  };
  tokenInfo: {
    expiresAt: number;
    refreshExpiresAt: number;
    tokenType: string;
  };
}

interface UserInfo {
  id: number;
  username: string;
  email: string;
  role: string;
}

const getToken = (): string | null => {
  return localStorage.getItem('accessToken');
};

const setToken = (token: string): void => {
  localStorage.setItem('accessToken', token);
};

const removeToken = (): void => {
  localStorage.removeItem('accessToken');
};

const getUserInfo = (): UserInfo | null => {
  const userInfo = localStorage.getItem('userInfo');
  return userInfo ? JSON.parse(userInfo) : null;
};

const setUserInfo = (userInfo: UserInfo): void => {
  localStorage.setItem('userInfo', JSON.stringify(userInfo));
};

const removeUserInfo = (): void => {
  localStorage.removeItem('userInfo');
};

const isTokenExpired = (): boolean => {
  const token = getToken();
  if (!token) return true;

  try {
    const payload = JSON.parse(atob(token.split('.')[1]));
    const currentTime = Date.now() / 1000;
    return payload.exp < currentTime;
  } catch {
    return true;
  }
};

const refreshToken = async (): Promise<boolean> => {
  try {
    const response = await fetch(`${API_URL}/auth/refresh`, {
      method: 'POST',
      credentials: 'include',
    });

    if (response.ok) {
      const data: AuthResponse = await response.json();
      setToken(data.token);
      setUserInfo(data.user);
      return true;
    }
    return false;
  } catch {
    return false;
  }
};

export const authProvider: AuthProvider = {
  async login(params: LoginParams) {
    try {
      const response = await fetch(`${API_URL}/auth/login`, {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        credentials: 'include',
        body: JSON.stringify({
          email: params.username,
          password: params.password,
        }),
      });

      if (response.ok) {
        const data: AuthResponse = await response.json();

        if (data.role !== 'ADMIN') {
          throw new Error('Доступ запрещен. Требуются права администратора.');
        }

        setToken(data.token);
        setUserInfo(data.user);

        return Promise.resolve();
      } else {
        const error = await response.text();
        throw new Error(error || 'Ошибка входа в систему');
      }
    } catch (error) {
      throw new Error(
        error instanceof Error ? error.message : 'Ошибка входа в систему',
      );
    }
  },

  async checkError(error) {
    const status = error.status;
    if (status === 401 || status === 403) {
      removeToken();
      removeUserInfo();
      return Promise.reject();
    }
    return Promise.resolve();
  },

  // Проверка аутентификации
  async checkAuth() {
    const token = getToken();
    if (!token || isTokenExpired()) {
      // Пытаемся обновить токен
      const refreshed = await refreshToken();
      if (!refreshed) {
        removeToken();
        removeUserInfo();
        return Promise.reject();
      }
    }
    return Promise.resolve();
  },

  async logout() {
    try {
      await fetch(`${API_URL}/auth/logout`, {
        method: 'POST',
        credentials: 'include',
      });
    } catch (error) {
      console.error('Ошибка при выходе:', error);
    } finally {
      removeToken();
      removeUserInfo();
    }
    return Promise.resolve();
  },

  async getIdentity() {
    const userInfo = getUserInfo();
    if (userInfo) {
      return Promise.resolve(userInfo);
    }

    try {
      const token = getToken();
      if (!token) {
        throw new Error('Токен не найден');
      }

      const response = await fetch(`${API_URL}/auth/me`, {
        headers: {
          Authorization: `Bearer ${token}`,
        },
      });

      if (response.ok) {
        const userInfo: UserInfo = await response.json();
        setUserInfo(userInfo);
        return Promise.resolve(userInfo);
      } else {
        throw new Error('Ошибка получения информации о пользователе');
      }
    } catch (error) {
      removeToken();
      removeUserInfo();
      return Promise.reject(error);
    }
  },

  async handleCallback() {
    return Promise.resolve();
  },

  async canAccess(params) {
    const userInfo = getUserInfo();
    if (!userInfo) {
      return Promise.resolve(false);
    }

    if (userInfo.role !== 'ADMIN') {
      return Promise.resolve(false);
    }

    return Promise.resolve(true);
  },

  async getPermissions() {
    const userInfo = getUserInfo();
    if (!userInfo) {
      return Promise.resolve({});
    }

    return Promise.resolve({
      role: userInfo.role,
      canAccess: userInfo.role === 'ADMIN',
    });
  },
};
