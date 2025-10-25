import {
  ArrayInput,
  Edit,
  minValue,
  NumberInput,
  ReferenceInput,
  required,
  SelectInput,
  SimpleForm,
  SimpleFormIterator,
  TextInput,
} from 'react-admin';

export const ProductEdit = () => (
  <Edit>
    <SimpleForm>
      <TextInput source="name" validate={[required()]} />
      <TextInput source="description" multiline rows={3} />
      <NumberInput
        source="price.amount"
        validate={[required(), minValue(0)]}
        step="0.01"
      />
      <SelectInput
        source="price.currency"
        label="Currency"
        choices={[
          { id: 'USD', name: 'USD' },
          { id: 'EUR', name: 'EUR' },
          { id: 'UAH', name: 'UAH' },
        ]}
        validate={[required()]}
      />
      <NumberInput source="stock" validate={[required(), minValue(0)]} />
      <ReferenceInput source="categoryId" reference="categories">
        <SelectInput optionText="name" />
      </ReferenceInput>
      <ArrayInput source="images" label="Image URLs">
        <SimpleFormIterator>
          <TextInput source="" label="URL" />
        </SimpleFormIterator>
      </ArrayInput>
    </SimpleForm>
  </Edit>
);
