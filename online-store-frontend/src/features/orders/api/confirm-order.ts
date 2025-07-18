import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';
import {OrderDTO} from './get-orders';

export const confirmOrder = (orderId: number): Promise<OrderDTO> => {
  return api.put(`/orders/${orderId}/confirm`);
};

type UseConfirmOrderOptions = {
  mutationConfig?: MutationConfig<(orderId: number) => Promise<OrderDTO>>;
};

export const useConfirmOrder = ({mutationConfig}: UseConfirmOrderOptions = {}) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: (orderId: number) => confirmOrder(orderId),
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['orders', 'unconfirmed']});
      queryClient.invalidateQueries({queryKey: ['orders', 'employee']});
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
