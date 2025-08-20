import {LoginForm} from "@/features/auth/components/login-form.tsx";
import {ContentLayout} from "@/shared/components/layouts";

export const LoginRoute = () => {

  return (
    <ContentLayout title="Log in to your account">
      <LoginForm/>
    </ContentLayout>
  );
};
