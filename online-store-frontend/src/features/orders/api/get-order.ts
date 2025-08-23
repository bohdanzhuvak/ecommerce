import { queryOptions, useQuery } from '@tanstack/react-query';
import { api } from '@/shared/lib/api-client';
import { QueryConfig } from '@/shared/lib/react-query';
import { Order } from '../api.types';

export const getOrder = (orderId: number): Promise<Order> => {
  return api.get(`/orders/${orderId}`);
};

export const getOrderQueryOptions = (orderId: number) =>
  queryOptions({
    queryKey: ['order', orderId],
    queryFn: () => getOrder(orderId),
  });

type UseOrderOptions = {
  queryConfig?: QueryConfig<typeof getOrderQueryOptions>;
};

export const useOrder = (orderId: number, { queryConfig }: UseOrderOptions = {}) => {
  return useQuery({
    ...getOrderQueryOptions(orderId),
    ...queryConfig,
  });
};
