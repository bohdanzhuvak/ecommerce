import {
  BooleanField,
  Datagrid,
  FunctionField,
  List,
  NumberField,
  ReferenceField,
  TextField,
} from 'react-admin';

interface ProductRecord {
  id: string;
  name: string;
  description: string;
  price?: {
    amount: number;
    currency: string;
  };
  stock: number;
  categoryId: string;
  active: boolean;
}

export const ProductList = () => (
  <List>
    <Datagrid rowClick="edit">
      <TextField source="id" />
      <TextField source="name" />
      <TextField source="description" />
      <FunctionField<ProductRecord>
        label="Price"
        render={(record) =>
          record?.price
            ? `${parseFloat(record.price.amount.toString()).toFixed(2)} ${record.price.currency}`
            : '-'
        }
      />
      <NumberField source="stock" />
      <ReferenceField source="categoryId" reference="categories" link={false}>
        <TextField source="name" />
      </ReferenceField>
      <BooleanField source="active" />
    </Datagrid>
  </List>
);
