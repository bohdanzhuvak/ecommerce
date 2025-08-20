import {Navigate, useLocation} from 'react-router';

import {paths} from '@/config/paths';
import {useUser} from '@/shared/lib/auth/auth';

export const AdminRoute = ({children}: { children: React.ReactNode }) => {
  const user = useUser();
  const location = useLocation();

  if (!user.data) {
    return <Navigate to={paths.auth.login.getHref(location.pathname)} replace/>;
  }
  if (user.data.role == 'USER') {
    return <Navigate to={paths.home.getHref()} replace/>;
  }
  return children;
};

export const UserRoute = ({children}: { children: React.ReactNode }) => {
  const user = useUser();
  const location = useLocation();

  if (!user.data) {
    return <Navigate to={paths.auth.login.getHref(location.pathname)} replace/>;
  }
  if (user.data.role == 'ADMIN') {
    return <Navigate to={paths.admin.dashboard.getHref()} replace/>;
  }
  return children;
};
