import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';
import {Cart, AddToCartRequest} from '../api.types';

export const addToCart = ({data}: { data: AddToCartRequest }): Promise<Cart> => {
  return api.post(`/cart/add`, data);
};

type UseAddToCartOptions = {
  mutationConfig?: MutationConfig<typeof addToCart>;
};

export const useAddToCart = ({mutationConfig}: UseAddToCartOptions) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: ({data}: { data: AddToCartRequest }) => addToCart({data}),
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['cart']});
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
