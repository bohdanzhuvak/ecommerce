import { Datagrid, List, TextField } from 'react-admin';

export const ProductList = () => (
  <List>
    <Datagrid>
      <TextField source="id" />
      <TextField source="name" />
      <TextField source="description" />
      <TextField source="price" />
      <TextField source="stock" />
      <TextField source="categoryName" />
    </Datagrid>
  </List>
);
