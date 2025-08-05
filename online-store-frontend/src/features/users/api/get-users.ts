import {queryOptions, useQuery} from '@tanstack/react-query';

import {api} from '@/shared/lib/api-client';
import {QueryConfig} from '@/shared/lib/react-query';
import {User} from "./api.types.ts";

export const getUsers = (): Promise<User[]> => {
  return api.get(`/users`);
};

export const getClientsQueryOptions = () => {
  return queryOptions({
    queryKey: ['clients'],
    queryFn: getUsers,
  });
};

type UseClientsOptions = {
  queryConfig?: QueryConfig<typeof getClientsQueryOptions>;
};

export const useClients = ({queryConfig}: UseClientsOptions = {}) => {
  return useQuery({
    ...getClientsQueryOptions(),
    ...queryConfig,
  });
};
