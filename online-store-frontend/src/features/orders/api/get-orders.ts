import {queryOptions, useQuery} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {QueryConfig} from '@/shared/lib/react-query';
import {Order} from '../api.types';

export const getOrders = (): Promise<Order[]> => {
  return api.get(`/orders`);
};

export const getOrdersQueryOptions = () => {
  return queryOptions({
    queryKey: ['orders'],
    queryFn: () => getOrders(),
  });
};

type UseOrdersOptions = {
  queryConfig?: QueryConfig<typeof getOrdersQueryOptions>;
};

export const useOrders = ({queryConfig}: UseOrdersOptions) => {
  return useQuery({
    ...getOrdersQueryOptions(),
    ...queryConfig,
  });
};
