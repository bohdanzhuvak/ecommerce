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
      <SelectInput
        source="currency"
        label="Currency"
        choices={[
          { id: 'USD', name: 'USD' },
          { id: 'EUR', name: 'EUR' },
          { id: 'UAH', name: 'UAH' },
        ]}
        defaultValue="USD"
        validate={[required()]}
      />
      <NumberInput
        source="stock"
        label="Stock"
        validate={[required(), minValue(0)]}
      />
      <ReferenceInput source="categoryId" reference="categories">
        <SelectInput optionText="name" validate={[required()]} />
      </ReferenceInput>
      <ArrayInput source="images" label="Image URLs">
        <SimpleFormIterator>
          <TextInput source="" label="URL" />
        </SimpleFormIterator>
      </ArrayInput>
    </SimpleForm>
  </Create>
);
