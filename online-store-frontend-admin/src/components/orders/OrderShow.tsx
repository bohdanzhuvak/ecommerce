import {
  ArrayField,
  Datagrid,
  DateField,
  FunctionField,
  NumberField,
  ReferenceField,
  Show,
  SimpleShowLayout,
  Tab,
  TabbedShowLayout,
  TextField,
} from 'react-admin';
import { Order, OrderItem } from '../../types/entities';

export const OrderShow = () => (
  <Show>
    <TabbedShowLayout>
      <Tab label="Order Details">
        <SimpleShowLayout>
          <TextField source="id" />
          <TextField source="userId" label="User ID" />
          <DateField source="createdAt" label="Created At" />
          <NumberField source="totalPrice" label="Total Price" />
          <TextField source="status" />
        </SimpleShowLayout>
      </Tab>

      <Tab label="Delivery Address">
        <FunctionField
          label="Full Address"
          render={(record: Order) => {
            if (!record.deliveryAddress) return 'No delivery address';
            const addr = record.deliveryAddress;
            return (
              <div>
                <div>
                  <strong>Street:</strong> {addr.street}
                </div>
                <div>
                  <strong>City:</strong> {addr.city}
                </div>
                <div>
                  <strong>Postal Code:</strong> {addr.postalCode}
                </div>
                <div>
                  <strong>Country:</strong> {addr.country}
                </div>
                <div>
                  <strong>Phone:</strong> {addr.phone}
                </div>
                <div>
                  <strong>Is Default:</strong> {addr.isDefault ? 'Yes' : 'No'}
                </div>
                <div>
                  <strong>Created At:</strong>{' '}
                  {new Date(addr.createdAt).toLocaleString()}
                </div>
              </div>
            );
          }}
        />
      </Tab>

      <Tab label="Order Items">
        <ArrayField source="items">
          <Datagrid rowClick={false}>
            <ReferenceField
              source="productId"
              label="Product ID"
              reference="products"
            />
            <NumberField source="quantity" />
            <NumberField source="pricePerUnit" label="Price Per Unit" />
            <FunctionField
              label="Total"
              render={(record: OrderItem) =>
                (record.quantity * record.pricePerUnit).toFixed(2)
              }
            />
          </Datagrid>
        </ArrayField>
      </Tab>
    </TabbedShowLayout>
  </Show>
);
