import {api} from '@/shared/lib/api-client';
import {AdminOrder} from '../api.types';
import {Page} from '@/shared/types';
import {queryOptions, useQuery} from '@tanstack/react-query';
import {QueryConfig} from '@/shared/lib/react-query';

export const getAdminOrders = (page = 0, size = 12, sort = "asc"): Promise<Page<AdminOrder>> => {
  return api.get(`/admin/orders`, {params: {page, size, sort}});
};

export const getAdminOrdersQueryOptions = (page = 0, size = 12, sort = "asc") => {
  return queryOptions({
    queryKey: ['admin', 'orders', page, size, sort],
    queryFn: () => getAdminOrders(page, size, sort),
  });
};

type UseAdminOrdersOptions = {
  page?: number;
  size?: number;
  sort?: string;
  queryConfig?: QueryConfig<typeof getAdminOrdersQueryOptions>;
};

export const useAdminOrders = ({page = 0, size = 12, sort = "asc", queryConfig}: UseAdminOrdersOptions) => {
  return useQuery({
    ...getAdminOrdersQueryOptions(page, size, sort),
    ...queryConfig,
  });
};

