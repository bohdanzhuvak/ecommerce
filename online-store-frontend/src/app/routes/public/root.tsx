import {Outlet} from 'react-router';

import {PublicLayout, UserLayout} from '@/shared/components/layouts';
import {useUser} from "@/shared/lib/auth";

export const PublicRoot = () => {
  const user = useUser();

  if (!user.data) {
    return (
      <PublicLayout>
        <Outlet/>
      </PublicLayout>
    );
  } else {
    return (
      <UserLayout>
        <Outlet/>
      </UserLayout>
    );
  }
};

export const PublicRootErrorBoundary = () => {
  return <div>Something went wrong!</div>;
};
