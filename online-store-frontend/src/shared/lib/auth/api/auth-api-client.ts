import Axios, { AxiosInstance, AxiosRequestConfig, AxiosResponse } from 'axios';
import { env } from '@/config/env';
import { TokenManager } from '../core/token-manager';

export class AuthApiClient {
  private axiosInstance: AxiosInstance;
  private tokenManager: TokenManager;

  constructor() {
    this.tokenManager = new TokenManager();
    this.axiosInstance = Axios.create({
      baseURL: env.API_URL,
      timeout: 10000,
    });

    this.setupInterceptors();
  }

  private setupInterceptors(): void {
    // Request interceptor - add auth token
    this.axiosInstance.interceptors.request.use(
      async (config) => {
        const token = this.tokenManager.getToken();
        if (token) {
          config.headers.Authorization = `Bearer ${token}`;
        }
        return config;
      },
      (error) => Promise.reject(error)
    );

    // Response interceptor - handle auth errors
    this.axiosInstance.interceptors.response.use(
      (response: AxiosResponse) => response.data,
      async (error) => {
        if (error.response?.status === 401) {
          // Token expired or invalid - clear token
          this.tokenManager.clearToken();
        }
        return Promise.reject(error);
      }
    );
  }

  public async get<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return this.axiosInstance.get(url, config);
  }

  public async post<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.axiosInstance.post(url, data, config);
  }

  public async put<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.axiosInstance.put(url, data, config);
  }

  public async delete<T = any>(url: string, config?: AxiosRequestConfig): Promise<T> {
    return this.axiosInstance.delete(url, config);
  }

  public async patch<T = any>(url: string, data?: any, config?: AxiosRequestConfig): Promise<T> {
    return this.axiosInstance.patch(url, data, config);
  }

  // Special method for auth endpoints that don't need tokens
  public async authRequest<T = any>(
    method: 'get' | 'post' | 'put' | 'delete' | 'patch',
    url: string,
    data?: any,
    config?: AxiosRequestConfig
  ): Promise<T> {
    const authConfig = {
      ...config,
      headers: {
        ...config?.headers,
        'Content-Type': 'application/json',
      },
    };

    switch (method) {
      case 'get':
        return this.axiosInstance.get(url, authConfig);
      case 'post':
        return this.axiosInstance.post(url, data, authConfig);
      case 'put':
        return this.axiosInstance.put(url, data, authConfig);
      case 'delete':
        return this.axiosInstance.delete(url, authConfig);
      case 'patch':
        return this.axiosInstance.patch(url, data, authConfig);
      default:
        throw new Error(`Unsupported HTTP method: ${method}`);
    }
  }

  // Method to sync token with AuthManager
  public setToken(token: string): void {
    this.tokenManager.setToken(token);
  }

  public clearToken(): void {
    this.tokenManager.clearToken();
  }
}

// Export singleton instance
export const authApiClient = new AuthApiClient();
