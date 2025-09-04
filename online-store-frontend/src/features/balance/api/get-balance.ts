import {api} from '@/shared/lib/api-client';
import {BalanceResponse} from '@/shared/types';
import {MutationConfig, QueryConfig} from "@/shared/lib/react-query.ts";
import {queryOptions, useQuery} from "@tanstack/react-query";

export const getBalance = (): Promise<BalanceResponse> => {
  return api.get('/users/balance');
};

export const getBalanceQueryOptions = () => {
  return queryOptions(
    {
      queryKey: ['balance'],
      queryFn: () => getBalance(),
    }
  )
}

type UseBalanceOptions = {
  queryConfig?: QueryConfig<typeof getBalanceQueryOptions>;
}

export const useBalance = ({queryConfig}: UseBalanceOptions = {}) => {
  return useQuery(
    {
      ...getBalanceQueryOptions(),
      ...queryConfig,
    }
  )
}
