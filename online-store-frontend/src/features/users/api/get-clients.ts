import {queryOptions, useQuery} from '@tanstack/react-query';

import {api} from '@/shared/lib/api-client';
import {QueryConfig} from '@/shared/lib/react-query';
import {Client} from '@/shared/types/api';

export const getClients = (): Promise<Client[]> => {
  return api.get(`/clients`);
};

export const getClientsQueryOptions = () => {
  return queryOptions({
    queryKey: ['clients'],
    queryFn: getClients,
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
