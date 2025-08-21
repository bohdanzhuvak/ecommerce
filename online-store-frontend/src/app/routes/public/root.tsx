import {Outlet} from 'react-router';

import {PublicLayout, UserLayout} from '@/shared/components/layouts';
import {useAuth} from "@/shared/lib/auth";

export const PublicRoot = () => {
  const {state} = useAuth();
  const isAuthenticated = state.isAuthenticated;

  if (!isAuthenticated) {
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
