import {
  ArrayInput,
  Create,
  minValue,
  NumberInput,
  ReferenceInput,
  required,
  SelectInput,
  SimpleForm,
  SimpleFormIterator,
  TextInput,
} from 'react-admin';

export const ProductCreate = () => (
  <Create>
    <SimpleForm>
      <TextInput source="name" label="Name" validate={[required()]} />
      <TextInput source="description" label="Description" multiline rows={3} />
      <NumberInput
        source="price"
        label="Price"
        validate={[required(), minValue(0)]}
        step="0.01"
      />
      <NumberInput
        source="stock"
        label="Stock"
        validate={[required(), minValue(0)]}
      />
      <ReferenceInput source="categoryId" reference="categories">
        <SelectInput optionText="name" />
      </ReferenceInput>
      <ArrayInput source="imageUrls" label="Image URLs">
        <SimpleFormIterator>
          <TextInput source="" label="URL" />
        </SimpleFormIterator>
      </ArrayInput>
    </SimpleForm>
  </Create>
);
