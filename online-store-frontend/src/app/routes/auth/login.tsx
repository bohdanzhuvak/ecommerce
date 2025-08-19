import {useNavigate, useSearchParams} from 'react-router';

import {paths} from '@/config/paths';
import {LoginForm} from '@/features/auth/components/login-form';
import {AuthLayout} from '@/shared/components/layouts/auth-layout';

export const LoginRoute = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const redirectTo = searchParams.get('redirectTo');

  return (
    <AuthLayout title="Log in to your account">
      <LoginForm
        onSuccess={() => {
          navigate(
            `${redirectTo ? `${redirectTo}` : paths.app.products.getHref()}`,
            {
              replace: true,
            },
          );
        }}
      />
    </AuthLayout>
  );
};
