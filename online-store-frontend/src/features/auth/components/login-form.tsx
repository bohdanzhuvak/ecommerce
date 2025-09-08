import React, { useEffect, useState } from 'react';
import { useNavigate, useSearchParams } from 'react-router';
import { paths } from '@/config/paths';
import { Button } from '@/shared/components/ui/button';
import { Form, Input } from '@/shared/components/ui/form';
import { useAuth } from '@/shared/lib/auth';
import { loginInputSchema } from '@/shared/types';

export const LoginForm: React.FC = () => {
  const [error, setError] = useState<string | undefined>(undefined);
  const [isSubmitting, setIsSubmitting] = useState(false);
  const { login, state } = useAuth();
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
      setIsSubmitting(true);
      setError(undefined);
      await login(values);
    } catch (err: any) {
      setError(err.message || 'An unknown error occurred.');
    } finally {
      setIsSubmitting(false);
    }
  };

  useEffect(() => {
    if (state.error) {
      setError(state.error);
    } else {
      setError(undefined);
    }
  }, [state.error]);

  if (state.isAuthenticated) {
    return null;
  }

  return (
    <div className="w-full max-w-md mx-auto">
      <Form
        onSubmit={handleSubmit}
        error={error || state.error || undefined}
        schema={loginInputSchema}
      >
        {({ register, formState }) => (
          <>
            <Input
              type="email"
              label="Email"
              error={formState.errors['email']}
              registration={register('email')}
              disabled={isSubmitting}
            />
            <Input
              type="password"
              label="Password"
              error={formState.errors['password']}
              registration={register('password')}
              disabled={isSubmitting}
            />
            <div className="mt-6">
              <Button
                isLoading={isSubmitting}
                type="submit"
                className="w-full"
                disabled={isSubmitting}
              >
                {isSubmitting ? 'Signing in...' : 'Sign in'}
              </Button>
            </div>
          </>
        )}
      </Form>

      <div className="mt-4 text-center">
        <p className="text-sm text-gray-600">
          Don't have an account?{' '}
          <a
            href={paths.auth.register.getHref(redirectTo)}
            className="font-medium text-blue-600 hover:text-blue-500"
          >
            Sign up
          </a>
        </p>
      </div>
    </div>
  );
};
