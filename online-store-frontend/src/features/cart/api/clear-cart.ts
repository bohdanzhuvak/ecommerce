import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';
import {Cart} from '../api.types';

export const clearCart = (): Promise<Cart> => {
  return api.delete(`/cart/clear`);
};

type UseClearCartOptions = {
  mutationConfig?: MutationConfig<typeof clearCart>;
};

export const useClearCart = ({mutationConfig}: UseClearCartOptions) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: () => clearCart(),
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['cart']});
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
