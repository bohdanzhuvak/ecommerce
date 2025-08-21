import {AuthLayout} from '@/shared/components/layouts/auth-layout';
import {LoginForm} from "@/features/auth/components/login-form.tsx";

export const LoginRoute = () => {

  return (
    <AuthLayout title="Log in to your account">
      <LoginForm/>
    </AuthLayout>
  );
};
