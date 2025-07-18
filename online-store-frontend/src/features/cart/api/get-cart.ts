import {queryOptions, useQuery} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {QueryConfig} from '@/shared/lib/react-query';

export interface CartItemDTO {
  id: number;
  book: {
    name: string;
    author: string;
    price: number;
    ageGroup: string;
    language: string;
  };
  quantity: number;
}

export interface CartDTO {
  id: number;
  clientEmail: string;
  items: CartItemDTO[];
}

export const getCart = ({clientEmail}: { clientEmail: string }): Promise<CartDTO> => {
  return api.get(`/cart/${clientEmail}`);
};

export const getCartQueryOptions = (clientEmail: string) => {
  return queryOptions({
    queryKey: ['cart', clientEmail],
    queryFn: () => getCart({clientEmail: clientEmail}),
    enabled: !!clientEmail,
  });
};

type UseCartOptions = {
  clientEmail: string;
  queryConfig?: QueryConfig<typeof getCartQueryOptions>;
};

export const useCart = ({clientEmail, queryConfig}: UseCartOptions) => {
  return useQuery({
    ...getCartQueryOptions(clientEmail),
    ...queryConfig,
  });
};
