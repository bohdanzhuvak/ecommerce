import {AuthLayout} from '@/shared/components/layouts/auth-layout';
import {LoginFormV2} from "@/features/auth/components/login-form-v2.tsx";

export const LoginRoute = () => {

  return (
    <AuthLayout title="Log in to your account">
      <LoginFormV2/>
    </AuthLayout>
  );
};
