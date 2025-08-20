import {QueryClient} from '@tanstack/react-query';

import {getClientsQueryOptions} from '@/features/users/api/get-users.ts';
import {ClientsList} from '@/features/users/components/clients-list';
import {ContentLayout} from '@/shared/components/layouts';

export const usersLoader = (queryClient: QueryClient) => async () => {
  const query = getClientsQueryOptions();

  return (
    queryClient.getQueryData(query.queryKey) ??
    (await queryClient.fetchQuery(query))
  );
};

export const UsersRoute = () => {
  return (
    <ContentLayout title="Clients">
      <ClientsList/>
    </ContentLayout>
  );
};
