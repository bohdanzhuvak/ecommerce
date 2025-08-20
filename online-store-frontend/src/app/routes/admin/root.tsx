import {Outlet} from 'react-router';

import {AdminLayout} from '@/shared/components/layouts';

export const AdminRoot = () => {
  return (
    <AdminLayout>
      <Outlet/>
    </AdminLayout>
  );
};

export const AdminRootErrorBoundary = () => {
  return <div>Something went wrong!</div>;
};
