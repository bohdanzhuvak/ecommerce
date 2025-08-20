import {Outlet} from 'react-router';

import {UserLayout} from '@/shared/components/layouts';

export const UserRoot = () => {
  return (
    <UserLayout>
      <Outlet/>
    </UserLayout>
  );
};

export const UserRootErrorBoundary = () => {
  return <div>Something went wrong!</div>;
};
