import {Spinner} from '@/shared/components/ui/spinner';
import {Table} from '@/shared/components/ui/table';

import {useClients} from '../api/get-users.ts';
import {Link} from '@/shared/components/ui/link';
import {paths} from '@/config/paths';

export const ClientsList = () => {
  const clientsQuery = useClients();

  if (clientsQuery.isLoading) {
    return (
      <div className="flex h-48 w-full items-center justify-center">
        <Spinner size="lg"/>
      </div>
    );
  }

  const clients = clientsQuery.data?.content;

  if (!clients) return null;

  return (
    <Table
      // Table requires BaseEntity, but admin users don't match; cast for display-only usage
      data={clients as unknown as any[]}
      columns={[
        {
          title: 'Id',
          field: 'id',
          Cell({entry: {id}}: any) {
            return <Link to={paths.app.user.getHref(String(id))}>{id}</Link>;
          },
        },
        {
          title: 'Email',
          field: 'email',
          Cell({entry: {email}}: any) {
            return <span>{email}</span>;
          },
        },
        {
          title: 'Username',
          field: 'username',
          Cell({entry: {username}}: any) {
            return <span>{username}</span>;
          },
        },
        {
          title: 'Role',
          field: 'role',
          Cell({entry: {role}}: any) {
            return <span>{role}</span>;
          },
        }
      ]}
    />
  );
};
