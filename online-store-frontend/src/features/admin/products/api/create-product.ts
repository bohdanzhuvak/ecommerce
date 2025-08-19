import {api} from '@/shared/lib/api-client';
import {CreateProductRequest} from '../api.types';
import {MutationConfig} from "@/shared/lib/react-query.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {getProductsQueryOptions} from "@/features/products/api/get-products.ts";

export const createProduct = (data: CreateProductRequest): Promise<void> => {
  return api.post(`/admin/products`, data);
};

type UseCreateProductOptions = {
  mutationConfig?: MutationConfig<typeof createProduct>;
};

export const useCreateProduct = ({mutationConfig}: UseCreateProductOptions) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: createProduct,
    onSuccess: (...args) => {
      queryClient.invalidateQueries({
        queryKey: getProductsQueryOptions().queryKey,
      });
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
