import {
  Create,
  email,
  minLength,
  PasswordInput,
  required,
  SimpleForm,
  TextInput,
} from 'react-admin';

export const UserCreate = () => (
  <Create>
    <SimpleForm>
      <TextInput source="username" label="Username" validate={[required()]} />
      <TextInput
        source="email"
        label="Email"
        type="email"
        validate={[required(), email()]}
      />
      <PasswordInput
        source="password"
        label="Password"
        validate={[required(), minLength(6)]}
      />
      <TextInput source="firstName" label="First Name" />
      <TextInput source="lastName" label="Last Name" />
    </SimpleForm>
  </Create>
);
