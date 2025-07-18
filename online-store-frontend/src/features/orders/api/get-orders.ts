import {queryOptions, useQuery} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {QueryConfig} from '@/shared/lib/react-query';

export interface OrderBookItemDTO {
  bookName: string;
  quantity: number;
}

export interface OrderDTO {
  id: number;
  clientEmail: string;
  employeeEmail?: string;
  orderDate: string;
  price: number;
  bookItems: OrderBookItemDTO[];
}

export const getOrders = ({clientEmail}: { clientEmail: string }): Promise<OrderDTO[]> => {
  return api.get(`/orders/client/${clientEmail}`);
};

export const getOrdersQueryOptions = (clientEmail: string) => {
  return queryOptions({
    queryKey: ['orders', clientEmail],
    queryFn: () => getOrders({clientEmail}),
    enabled: !!clientEmail,
  });
};

type UseOrdersOptions = {
  clientEmail: string;
  queryConfig?: QueryConfig<typeof getOrdersQueryOptions>;
};

export const useOrders = ({clientEmail, queryConfig}: UseOrdersOptions) => {
  return useQuery({
    ...getOrdersQueryOptions(clientEmail),
    ...queryConfig,
  });
};
