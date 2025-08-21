import {Outlet} from 'react-router';
import {AuthLayout} from "@/shared/components/layouts";

export const AuthRoot = () => {
  return (
    <AuthLayout>
      <Outlet/>
    </AuthLayout>
  )
};

export const AuthRootErrorBoundary = () => {
  return <div>Something went wrong!</div>;
};
