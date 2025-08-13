import {api} from '@/shared/lib/api-client';
import {queryOptions, useQuery} from '@tanstack/react-query';
import type {ProductDTO} from './list-products';
import {QueryConfig} from '@/shared/lib/react-query';

export const getProduct = (id: number | string): Promise<ProductDTO> => {
  return api.get(`/products/${id}`);
};

export const getProductQueryOptions = (id: number | string) =>
  queryOptions({
    queryKey: ['product', id],
    queryFn: () => getProduct(id),
  });

export const useProduct = (
  id: number | string,
  opts?: QueryConfig<typeof getProductQueryOptions>,
) =>
  useQuery({
    ...getProductQueryOptions(id),
    ...opts,
  });


