import { fetchUtils } from 'ra-core';
import { HttpClient, HttpClientOptions } from '../../types/data-provider';

const getToken = (): string | null => localStorage.getItem('accessToken');

export const httpClientWithAuth: HttpClient = <T = any>(
  url: string,
  options: HttpClientOptions = {},
) => {
  const token = getToken();
  const headers = new Headers(options.headers || {});

  if (token) {
    headers.set('Authorization', `Bearer ${token}`);
  }

  return fetchUtils.fetchJson(url, {
    ...options,
    headers,
  }) as Promise<{ json: T; status: number; headers: Headers }>;
};
