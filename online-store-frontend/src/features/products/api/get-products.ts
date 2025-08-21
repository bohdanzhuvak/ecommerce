import {api} from '@/shared/lib/api-client';
import {queryOptions, useQuery} from '@tanstack/react-query';
import {QueryConfig} from '@/shared/lib/react-query';
import {Product} from '../api.types';
import {Page} from '@/shared/types';

export const getProducts = (page = 0, size = 12, sort = "asc"): Promise<Page<Product>> => {
  return api.get(`/products`, {params: {page, size, sort}});
};

export const getProductsQueryOptions = (page = 0, size = 12, sort = "asc") =>
  queryOptions({
    queryKey: ['products', page, size, sort],
    queryFn: () => getProducts(page, size, sort),
  });

type UseProductsOptions = {
  page?: number;
  size?: number;
  sort?: string;
  opts?: QueryConfig<typeof getProductsQueryOptions>;
}

export const useProducts = ({
                              page = 0,
                              size = 12,
                              sort = "asc",
                              opts
                            }: UseProductsOptions) =>
  useQuery({
    ...getProductsQueryOptions(page, size, sort),
    ...opts,
  });


