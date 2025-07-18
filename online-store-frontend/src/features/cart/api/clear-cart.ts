import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';
import {CartDTO} from './get-cart';

export const clearCart = ({clientEmail}: { clientEmail: string }): Promise<CartDTO> => {
  return api.delete(`/cart/${clientEmail}/clear`);
};

type UseClearCartOptions = {
  clientEmail: string;
  mutationConfig?: MutationConfig<typeof clearCart>;
};

export const useClearCart = ({clientEmail, mutationConfig}: UseClearCartOptions) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: () => clearCart({clientEmail: clientEmail}),
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['cart', clientEmail]});
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
