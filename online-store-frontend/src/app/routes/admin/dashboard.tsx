import {ContentLayout} from '@/shared/components/layouts';
import {useUser} from '@/shared/lib/auth/auth';

export const DashboardRoute = () => {
  const user = useUser();
  return (
    <ContentLayout title="Dashboard">
      <h1 className="text-xl">
        Welcome, <b>{user.data?.email || 'User'}</b>
      </h1>
      <h4 className="my-3">
        Your role is: <b>{user.data?.role || 'Unknown'}</b>
      </h4>
    </ContentLayout>
  );
};
