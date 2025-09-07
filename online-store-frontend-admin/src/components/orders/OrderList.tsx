import {
  Datagrid,
  DateField,
  FunctionField,
  List,
  NumberField,
  ReferenceField,
  ShowButton,
  TextField,
} from 'react-admin';
import { useParams } from 'react-router';

export const OrderList = () => {
  const { id } = useParams();

  return (
    <List resource="orders" filter={{ userId: id }}>
      <Datagrid>
        <TextField source="id" />
        <ReferenceField source="userId" label="User ID" reference="users" />
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
};
