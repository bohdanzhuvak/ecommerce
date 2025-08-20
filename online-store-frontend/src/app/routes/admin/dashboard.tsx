import {ContentLayout} from '@/shared/components/layouts';
import {useAuth} from "@/shared/lib/auth";

export const DashboardRoute = () => {
  const user = useAuth().state.user;
  return (
    <ContentLayout title="Dashboard">
      <h1 className="text-xl">
        Welcome, <b>{user?.email || 'User'}</b>
      </h1>
      <h4 className="my-3">
        Your role is: <b>{user?.role || 'Unknown'}</b>
      </h4>
    </ContentLayout>
  );
};
