import {api} from '@/shared/lib/api-client';
import {queryOptions, useQuery} from '@tanstack/react-query';
import {QueryConfig} from '@/shared/lib/react-query';
import {Product} from '../api.types';
import {Page} from '@/shared/types/api';

export const getProducts = (page = 0, size = 12): Promise<Page<Product>> => {
  return api.get(`/products`, {params: {page, size}});
};

export const getProductsQueryOptions = (page = 0, size = 12) =>
  queryOptions({
    queryKey: ['products', page, size],
    queryFn: () => getProducts(page, size),
  });

export const useProducts = (
  page = 0,
  size = 12,
  opts?: QueryConfig<typeof getProductsQueryOptions>,
) =>
  useQuery({
    ...getProductsQueryOptions(page, size),
    ...opts,
  });


