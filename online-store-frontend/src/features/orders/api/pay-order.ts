import {useMutation, useQueryClient} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';

export const payOrder = (orderId: number): Promise<void> => {
  return api.post(`/orders/${orderId}/pay`);
};

type UsePayOrderOptions = {
  orderId: number;
  mutationConfig?: MutationConfig<(orderId: number) => Promise<void>>;
};

export const usePayOrder = ({mutationConfig, orderId}: UsePayOrderOptions) => {
  const queryClient = useQueryClient();
  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    mutationFn: payOrder,
    onSuccess: (...args) => {
      queryClient.invalidateQueries({ queryKey: ['orders'] });
      queryClient.invalidateQueries({queryKey: ['orders', orderId]});
      queryClient.invalidateQueries({ queryKey: ['balance'] });
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
