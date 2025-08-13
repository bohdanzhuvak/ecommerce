import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';
import {OrderDTO} from './get-orders';

export const createOrderFromCart = (): Promise<OrderDTO> => {
  return api.post(`/orders`);
};

type UseCreateOrderFromCartOptions = {
  mutationConfig?: MutationConfig<() => Promise<OrderDTO>>;
};

export const useCreateOrderFromCart = ({mutationConfig}: UseCreateOrderFromCartOptions = {}) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: () => createOrderFromCart(),
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['orders']});
      queryClient.invalidateQueries({queryKey: ['cart']});
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
