import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';
import {CartDTO} from './get-cart';

export const removeFromCart = ({clientEmail, bookName}: {
  clientEmail: string;
  bookName: string
}): Promise<CartDTO> => {
  return api.delete(`/cart/${clientEmail}/remove/${bookName}`);
};

type UseRemoveFromCartOptions = {
  clientEmail: string;
  mutationConfig?: MutationConfig<typeof removeFromCart>;
};

export const useRemoveFromCart = ({clientEmail, mutationConfig}: UseRemoveFromCartOptions) => {
  const queryClient = useQueryClient();

  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: removeFromCart,
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['cart', clientEmail]});
      onSuccess?.(...args);
    },
    ...restConfig
  });
};
