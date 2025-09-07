import {
  Datagrid,
  DateField,
  FunctionField,
  List,
  NumberField,
  ShowButton,
  TextField,
} from 'react-admin';

export const OrderList = () => (
  <List>
    <Datagrid>
      <TextField source="id" />
      <TextField source="userId" label="User ID" />
      <DateField source="createdAt" label="Created At" />
      <NumberField source="totalPrice" label="Total Price" />
      <TextField source="status" />
      <FunctionField
        label="Delivery Address"
        render={(record: any) => {
          if (!record.deliveryAddress) return 'No address';
          const addr = record.deliveryAddress;
          return `${addr.street}, ${addr.city}, ${addr.postalCode}, ${addr.country}`;
        }}
      />
      <ShowButton />
    </Datagrid>
  </List>
);
