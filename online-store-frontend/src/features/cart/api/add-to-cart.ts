import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';
import {CartDTO} from './get-cart';

export interface AddToCartRequest {
  bookName: string;
  quantity: number;
}

export const addToCart = ({clientEmail, data}: { clientEmail: string; data: AddToCartRequest }): Promise<CartDTO> => {
  return api.post(`/cart/${clientEmail}/add`, data);
};

type UseAddToCartOptions = {
  clientEmail: string;
  mutationConfig?: MutationConfig<typeof addToCart>;
};

export const useAddToCart = ({clientEmail, mutationConfig}: UseAddToCartOptions) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: ({data}: { data: AddToCartRequest }) => addToCart({clientEmail: clientEmail, data}),
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['cart', clientEmail]});
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
