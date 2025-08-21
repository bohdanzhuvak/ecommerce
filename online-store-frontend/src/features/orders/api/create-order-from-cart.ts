import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';
import {Order} from '../api.types';

export const createOrderFromCart = (addressId: number): Promise<Order> => {
  return api.post(`/orders`, { deliveryAddressId: addressId });
};

type UseCreateOrderFromCartOptions = {
  mutationConfig?: MutationConfig<typeof createOrderFromCart>;
};

export const useCreateOrderFromCart = ({mutationConfig}: UseCreateOrderFromCartOptions) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: createOrderFromCart,
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['orders']});
      queryClient.invalidateQueries({queryKey: ['cart']});
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
