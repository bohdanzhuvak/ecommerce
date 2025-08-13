import {queryOptions, useQuery} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {QueryConfig} from '@/shared/lib/react-query';

export interface OrderItemDTO {
  productId: number;
  productName: string;
  quantity: number;
  pricePerUnit: number;
}

export interface OrderDTO {
  id: number;
  createdAt: string;
  totalPrice: number;
  status: string;
  items: OrderItemDTO[];
}

export const getOrders = (): Promise<OrderDTO[]> => {
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
