import {useNavigate, useSearchParams} from 'react-router';

import {paths} from '@/config/paths';
import {RegisterForm} from '@/features/auth/components/register-form';
import {ContentLayout} from "@/shared/components/layouts";

export const RegisterRoute = () => {
  const navigate = useNavigate();
  const [searchParams] = useSearchParams();
  const redirectTo = searchParams.get('redirectTo');

  return (
    <ContentLayout title="Register your account">
      <RegisterForm
        onSuccess={() =>
          navigate(
            `${redirectTo ? `${redirectTo}` : paths.home.getHref()}`,
            {
              replace: true,
            },
          )
        }
      />
    </ContentLayout>
  );
};
