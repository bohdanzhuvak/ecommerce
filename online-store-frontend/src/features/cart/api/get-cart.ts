import {queryOptions, useQuery} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {QueryConfig} from '@/shared/lib/react-query';
import {Cart} from '../api.types';

export const getCart = (): Promise<Cart> => {
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
