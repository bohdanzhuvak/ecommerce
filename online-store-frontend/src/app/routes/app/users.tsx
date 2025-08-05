import {QueryClient} from '@tanstack/react-query';

import {getClientsQueryOptions} from '@/features/users/api/get-users.ts';
import {ClientsList} from '@/features/users/components/clients-list';
import {ContentLayout} from '@/shared/components/layouts';
import {Authorization, ROLES} from '@/shared/lib/auth/authorization';

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
      <Authorization
        forbiddenFallback={<div>Only employees can view this.</div>}
        allowedRoles={[ROLES.EMPLOYEE]}
      >
        <ClientsList/>
      </Authorization>
    </ContentLayout>
  );
};
