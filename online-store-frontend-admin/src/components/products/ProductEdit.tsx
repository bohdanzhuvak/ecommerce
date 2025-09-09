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
        source="price"
        validate={[required(), minValue(0)]}
        step="0.01"
      />
      <NumberInput source="stock" validate={[required(), minValue(0)]} />
      <ReferenceInput source="categoryId" reference="categories">
        <SelectInput optionText="name" />
      </ReferenceInput>
      <ArrayInput source="imageUrls">
        <SimpleFormIterator>
          <TextInput source="" label="URL" />
        </SimpleFormIterator>
      </ArrayInput>
    </SimpleForm>
  </Edit>
);
