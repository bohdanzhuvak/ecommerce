import {queryOptions, useQuery} from '@tanstack/react-query';

import {api} from '@/shared/lib/api-client';
import {QueryConfig} from '@/shared/lib/react-query';

export type AdminUserDTO = {
  id: number;
  username: string;
  email: string;
  role: string;
};

export const getUsers = (): Promise<{ content: AdminUserDTO[] }> => {
  return api.get(`/admin/users`);
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
