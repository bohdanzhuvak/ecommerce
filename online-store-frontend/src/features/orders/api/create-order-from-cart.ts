import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';
import {OrderDTO} from './get-orders';

export interface CreateOrderFromCartRequest {
  clientEmail: string;
}

export const createOrderFromCart = ({clientEmail}: CreateOrderFromCartRequest): Promise<OrderDTO> => {
  return api.post(`/orders/from-cart/${clientEmail}`);
};

type UseCreateOrderFromCartOptions = {
  clientEmail: string;
  mutationConfig?: MutationConfig<(employeeEmail?: string) => Promise<OrderDTO>>;
};

export const useCreateOrderFromCart = ({clientEmail, mutationConfig}: UseCreateOrderFromCartOptions) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: () => createOrderFromCart({clientEmail}),
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['orders', clientEmail]});
      queryClient.invalidateQueries({queryKey: ['cart', clientEmail]});
      queryClient.invalidateQueries({queryKey: ['authenticated-user']});
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
