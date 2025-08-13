import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';
import {CartDTO} from './get-cart';

export const removeFromCart = ({productId}: { productId: number }): Promise<CartDTO> => {
  return api.put(`/cart/update`, {productId: productId, quantity: 0});
};

type UseRemoveFromCartOptions = {
  mutationConfig?: MutationConfig<typeof removeFromCart>;
};

export const useRemoveFromCart = ({mutationConfig}: UseRemoveFromCartOptions) => {
  const queryClient = useQueryClient();

  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: removeFromCart,
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['cart']});
      onSuccess?.(...args);
    },
    ...restConfig
  });
};
