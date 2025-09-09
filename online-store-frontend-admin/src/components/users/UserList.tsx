import { Datagrid, Link, List, TextField, useRecordContext } from 'react-admin';
import { Button } from '@mui/material';

const OrdersButton = () => {
  const record = useRecordContext();
  if (!record) return null;

  return (
    <Button component={Link} to={`/users/${record.id}/orders`} color="primary">
      Orders
    </Button>
  );
};

export const UserList = () => (
  <List>
    <Datagrid rowClick={false}>
      <TextField source="id" />
      <TextField source="username" />
      <TextField source="email" />
      <TextField source="role" />
      <OrdersButton />
    </Datagrid>
  </List>
);
