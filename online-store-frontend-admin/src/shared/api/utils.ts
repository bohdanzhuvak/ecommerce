import { stringify } from 'query-string';
import { SpringQueryParams } from '../../types/data-provider';

export const buildSpringPaginationQuery = (params: {
  pagination?: { page: number; perPage: number };
  sort?: { field: string; order: string };
  filter?: Record<string, any>;
}): string => {
  const { page, perPage } = params.pagination || { page: 1, perPage: 10 };
  const { field, order } = params.sort || { field: 'id', order: 'ASC' };

  const query: SpringQueryParams = {
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
