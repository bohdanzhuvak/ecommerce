import {queryOptions, useQuery} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {QueryConfig} from '@/shared/lib/react-query';
import {OrderDTO} from './get-orders';

export const getUnconfirmedOrders = (): Promise<OrderDTO[]> => {
  return api.get('/orders/unconfirmed');
};

export const getUnconfirmedOrdersQueryOptions = () => {
  return queryOptions({
    queryKey: ['orders', 'unconfirmed'],
    queryFn: getUnconfirmedOrders,
  });
};

type UseUnconfirmedOrdersOptions = {
  queryConfig?: QueryConfig<typeof getUnconfirmedOrdersQueryOptions>;
};

export const useUnconfirmedOrders = ({queryConfig}: UseUnconfirmedOrdersOptions = {}) => {
  return useQuery({
    ...getUnconfirmedOrdersQueryOptions(),
    ...queryConfig,
  });
};
