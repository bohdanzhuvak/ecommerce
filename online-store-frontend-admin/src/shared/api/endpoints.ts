const API_BASE = 'http://localhost:8080/api/v1/admin';

export const endpoints = {
  list: (resource: string, query: string) => `${API_BASE}/${resource}?${query}`,
  one: (resource: string, id: string | number) =>
    `${API_BASE}/${resource}/${encodeURIComponent(id)}`,
  create: (resource: string) => `${API_BASE}/${resource}`,
  update: (resource: string, id: string | number) =>
    `${API_BASE}/${resource}/${encodeURIComponent(id)}`,
  delete: (resource: string, id: string | number) =>
    `${API_BASE}/${resource}/${encodeURIComponent(id)}`,
};
