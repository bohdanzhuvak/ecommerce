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

  const clients = clientsQuery.data;

  if (!clients) return null;

  return (
    <Table
      data={clients}
      columns={[
        {
          title: 'Id',
          field: 'id',
          Cell({entry: {id}}) {
            return <Link to={paths.app.user.getHref(id)}>{id}</Link>;
          },
        },
        {
          title: 'Email',
          field: 'email',
          Cell({entry: {email}}) {
            return <span>{email}</span>;
          },
        },
        {
          title: 'Name',
          field: 'name',
          Cell({entry: {name}}) {
            return <span>{name}</span>;
          },
        },
        {
          title: 'Balance',
          field: 'balance',
          Cell({entry: {balance}}) {
            return <span>{balance}</span>;
          },
        }
      ]}
    />
  );
};
