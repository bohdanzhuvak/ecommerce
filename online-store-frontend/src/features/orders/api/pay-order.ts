import { useMutation, useQueryClient } from '@tanstack/react-query';
import { api } from '@/shared/lib/api-client';
import { MutationConfig } from '@/shared/lib/react-query';

export const payOrder = (orderId: number): Promise<void> => {
  return api.post(`/orders/${orderId}/pay`);
};

type UsePayOrderOptions = {
  mutationConfig?: MutationConfig<(orderId: number) => Promise<void>>;
};

export const usePayOrder = ({ mutationConfig }: UsePayOrderOptions) => {
  const queryClient = useQueryClient();
  const { onSuccess, ...restConfig } = mutationConfig || {};

  return useMutation({
    mutationFn: (orderId: number) => payOrder(orderId),
    onSuccess: (...args) => {
      queryClient.invalidateQueries({ queryKey: ['orders'] });
      queryClient.invalidateQueries({ queryKey: ['balance'] });
      onSuccess?.(...args);
    },
    ...restConfig,
  });
};
