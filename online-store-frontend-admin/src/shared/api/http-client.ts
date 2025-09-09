import { fetchUtils } from 'ra-core';

const getToken = (): string | null => localStorage.getItem('accessToken');

export const httpClientWithAuth = (url: string, options: any = {}) => {
  const token = getToken();
  const headers = new Headers(options.headers || {});

  if (token) {
    headers.set('Authorization', `Bearer ${token}`);
  }

  return fetchUtils.fetchJson(url, {
    ...options,
    headers,
  });
};
