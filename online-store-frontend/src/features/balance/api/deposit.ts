import { api } from '@/shared/lib/api-client';
import {DepositRequest} from "../api.types.ts";
import {MutationConfig} from "@/shared/lib/react-query.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";

export const depositToBalance = (request: DepositRequest): Promise<void> => {
  return api.post('/users/balance/deposit', request);
};

type UseDepositToBalanceOptions = {
  mutationConfig?: MutationConfig<typeof depositToBalance>;
};

export const useDepositToBalance = ({mutationConfig}: UseDepositToBalanceOptions) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    mutationFn: depositToBalance,
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['balance']});
      onSuccess?.(...args);
    },
    ...restConfig,
  })
}
