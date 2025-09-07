import { stringify } from 'query-string';
import { DataProvider, fetchUtils } from 'ra-core';

const getToken = (): string | null => {
  return localStorage.getItem('accessToken');
};

const httpClientWithAuth = (url: string, options: any = {}) => {
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

export default (
  apiUrl: string,
  httpClient = httpClientWithAuth,
): DataProvider => ({
  getList: async (resource, params) => {
    const { page, perPage } = params.pagination || { page: 1, perPage: 10 };
    const { field, order } = params.sort || { field: 'id', order: 'ASC' };

    // Spring Boot использует 0-based пагинацию
    const pageNumber = page - 1;
    const pageSize = perPage;

    const query: {
      page: number;
      size: number;
      sort?: string;
    } = {
      page: pageNumber,
      size: pageSize,
    };

    if (field && order) {
      query.sort = `${field},${order.toLowerCase()}`;
    }

    const url = `${apiUrl}/admin/${resource}?${stringify(query)}`;

    const { json } = await httpClient(url, { signal: params?.signal });

    if (!json.content || typeof json.totalElements === 'undefined') {
      throw new Error(
        `Invalid response format. Expected Spring Page structure with 'content' and 'totalElements' fields.`,
      );
    }

    return {
      data: json.content,
      total: json.totalElements,
    };
  },

  getOne: async (resource, params) => {
    const { json } = await httpClient(
      `${apiUrl}/admin/${resource}/${encodeURIComponent(params.id)}`,
      { signal: params?.signal },
    );
    return { data: json };
  },

  getMany: (resource, params) => {
    const query = {
      filter: JSON.stringify({ id: params.ids }),
    };
    const url = `${apiUrl}/admin/${resource}?${stringify(query)}`;
    return httpClient(url, { signal: params?.signal }).then(({ json }) => {
      if (json.content) {
        return { data: json.content };
      }
      return { data: json };
    });
  },

  getManyReference: (resource, params) => {
    const { page, perPage } = params.pagination;
    const { field, order } = params.sort;

    const pageNumber = page - 1;
    const pageSize = perPage;

    const query: {
      page: number;
      size: number;
      sort?: string;
    } = {
      page: pageNumber,
      size: pageSize,
    };

    if (field && order) {
      query.sort = `${field},${order.toLowerCase()}`;
    }

    const url = `${apiUrl}/admin/${resource}?${stringify(query)}`;

    return httpClient(url, { signal: params?.signal }).then(({ json }) => {
      if (!json.content || typeof json.totalElements === 'undefined') {
        throw new Error(
          `Invalid response format. Expected Spring Page structure with 'content' and 'totalElements' fields.`,
        );
      }
      return {
        data: json.content,
        total: json.totalElements,
      };
    });
  },

  update: (resource, params) =>
    httpClient(`${apiUrl}/admin/${resource}/${encodeURIComponent(params.id)}`, {
      method: 'PUT',
      body: JSON.stringify(params.data),
    }).then(({ json }) => ({ data: json })),

  updateMany: (resource, params) =>
    Promise.all(
      params.ids.map((id) =>
        httpClient(`${apiUrl}/admin/${resource}/${encodeURIComponent(id)}`, {
          method: 'PUT',
          body: JSON.stringify(params.data),
        }),
      ),
    ).then((responses) => ({
      data: responses.map(({ json }) => json.id),
    })),

  create: (resource, params) =>
    httpClient(`${apiUrl}/admin/${resource}`, {
      method: 'POST',
      body: JSON.stringify(params.data),
    }).then(({ json }) => ({ data: json })),

  delete: (resource, params) =>
    httpClient(`${apiUrl}/admin/${resource}/${encodeURIComponent(params.id)}`, {
      method: 'DELETE',
    }).then(({ json }) => ({ data: json })),

  deleteMany: (resource, params) =>
    Promise.all(
      params.ids.map((id) =>
        httpClient(`${apiUrl}/admin/${resource}/${encodeURIComponent(id)}`, {
          method: 'DELETE',
        }),
      ),
    ).then((responses) => ({
      data: responses.map(({ json }) => json.id),
    })),
});
