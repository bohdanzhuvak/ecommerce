import {api} from '@/shared/lib/api-client';
import {queryOptions, useQuery} from '@tanstack/react-query';
import {QueryConfig} from '@/shared/lib/react-query';

export interface ProductDTO {
  id: number;
  name: string;
  description: string;
  price: number;
  stock: number;
  categoryName: string;
  imageUrls: string[];
}

export interface Page<T> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
}

export const getProducts = (page = 0, size = 12): Promise<Page<ProductDTO>> => {
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


