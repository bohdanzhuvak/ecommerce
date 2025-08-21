import {Link, useNavigate, useSearchParams} from 'react-router';

import {paths} from '@/config/paths';
import {Button} from '@/shared/components/ui/button';
import {Form, Input} from '@/shared/components/ui/form';
import {registerInputSchema} from '@/shared/types';
import {useEffect, useState} from 'react';
import {RegisterFormProps} from '../api.types';
import {useAuth} from "@/shared/lib/auth";

export const RegisterForm = ({onSuccess}: RegisterFormProps) => {
  const [error, setError] = useState<string | undefined>(undefined);
  const { register, state } = useAuth();
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const redirectTo = searchParams.get('redirectTo');

  useEffect(() => {
    if (state.isAuthenticated && state.user) {
      const targetPath = redirectTo || paths.home.getHref();
      navigate(targetPath, { replace: true });
    }
  }, [state.isAuthenticated, state.user, navigate, redirectTo]);

  const handleSubmit = async (values: any) => {
    try {
      setError(undefined);
      await register(values);
      if (onSuccess) {
        onSuccess();
      }
    } catch (err: any) {
      setError(err.message || 'An unknown error occurred.');
    }
  };

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
              error={formState.errors['username']}
              registration={register('username')}
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
                isLoading={state.isLoading}
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
