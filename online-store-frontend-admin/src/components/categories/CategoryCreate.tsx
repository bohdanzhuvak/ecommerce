import { Create, required, SimpleForm, TextInput } from 'react-admin';

export const CategoryCreate = () => (
  <Create>
    <SimpleForm>
      <TextInput source="name" label="Name" validate={[required()]} />
    </SimpleForm>
  </Create>
);
