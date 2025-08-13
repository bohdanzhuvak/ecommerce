import {queryOptions, useQuery} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {QueryConfig} from '@/shared/lib/react-query';

export interface CartItemDTO {
  productId: number;
  productName: string;
  quantity: number;
  price: number;
}

export interface CartDTO {
  items: CartItemDTO[];
  totalPrice: number;
}

export const getCart = (): Promise<CartDTO> => {
  return api.get(`/cart`);
};

export const getCartQueryOptions = () => {
  return queryOptions({
    queryKey: ['cart'],
    queryFn: () => getCart(),
  });
};

type UseCartOptions = {
  queryConfig?: QueryConfig<typeof getCartQueryOptions>;
};

export const useCart = ({queryConfig}: UseCartOptions) => {
  return useQuery({
    ...getCartQueryOptions(),
    ...queryConfig,
  });
};
