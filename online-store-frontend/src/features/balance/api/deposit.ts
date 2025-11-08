import {
  getGetBalanceQueryKey,
  useDeposit,
} from '@/shared/api/generated/balance-customer/balance-customer';
import { useQueryClient } from '@tanstack/react-query';

type UseDepositToBalanceOptions = {
  mutationConfig?: {
    onSuccess?: () => void;
    onError?: (error: unknown) => void;
  };
};

/**
 * Hook to deposit funds to user's balance
 * UserId is automatically extracted from JWT token on the backend
 */
export const useDepositToBalance = ({
  mutationConfig,
}: UseDepositToBalanceOptions = {}) => {
  const queryClient = useQueryClient();

  return useDeposit({
    mutation: {
      onSuccess: () => {
        // Invalidate balance query to refetch
        queryClient.invalidateQueries({
          queryKey: getGetBalanceQueryKey(),
        });
        mutationConfig?.onSuccess?.();
      },
      onError: (error) => {
        mutationConfig?.onError?.(error);
      },
    },
  });
};
