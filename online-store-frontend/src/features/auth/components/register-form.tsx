import {Link, useSearchParams} from 'react-router';

import {paths} from '@/config/paths';
import {Button} from '@/shared/components/ui/button';
import {Form, Input} from '@/shared/components/ui/form';
import {useRegister} from '@/shared/lib/auth/auth';
import {registerInputSchema} from '@/shared/lib/auth/types';
import {useState} from 'react';
import {RegisterFormProps} from '../api.types';

export const RegisterForm = ({onSuccess}: RegisterFormProps) => {
  const [error, setError] = useState<string | undefined>(undefined);
  const registering = useRegister();

  const handleSubmit = async (values: any) => {
    try {
      setError(undefined);
      await registering.mutate(values);
      if (onSuccess) {
        onSuccess();
      }
    } catch (err: any) {
      setError(err.message || 'An unknown error occurred.');
    }
  };

  const [searchParams] = useSearchParams();
  const redirectTo = searchParams.get('redirectTo');

  return (
    <div>
      <Form
        onSubmit={handleSubmit}
        error={error}
        schema={registerInputSchema}
      >
        {({register, formState}) => (
          <>
            <Input
              type="text"
              label="Name"
              error={formState.errors['name']}
              registration={register('name')}
            />
            <Input
              type="email"
              label="Email"
              error={formState.errors['email']}
              registration={register('email')}
            />
            <Input
              type="password"
              label="Password"
              error={formState.errors['password']}
              registration={register('password')}
            />
            <div>
              <Button
                isLoading={registering.isPending}
                type="submit"
                className="w-full"
              >
                Register
              </Button>
            </div>
          </>
        )}
      </Form>
      <div className="mt-2 flex items-center justify-end">
        <div className="text-sm">
          <Link
            to={paths.auth.login.getHref(redirectTo)}
            className="font-medium text-blue-600 hover:text-blue-500"
          >
            Already have an account? Sign in
          </Link>
        </div>
      </div>
    </div>
  );
};
