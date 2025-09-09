import { stringify } from 'query-string';

export const buildSpringPaginationQuery = (params: any) => {
  const { page, perPage } = params.pagination || { page: 1, perPage: 10 };
  const { field, order } = params.sort || { field: 'id', order: 'ASC' };

  const query: Record<string, any> = {
    page: page - 1,
    size: perPage,
  };

  if (field && order) {
    query.sort = `${field},${order.toLowerCase()}`;
  }

  if (params.filter) {
    Object.entries(params.filter).forEach(([key, value]) => {
      if (value !== undefined && value !== null) {
        query[key] = value;
      }
    });
  }

  return stringify(query);
};

export const validateSpringResponse = (json: any) => {
  if (!json.content || typeof json.totalElements === 'undefined') {
    throw new Error(
      `Invalid response format. Expected Spring Page structure with 'content' and 'totalElements'.`,
    );
  }
};
