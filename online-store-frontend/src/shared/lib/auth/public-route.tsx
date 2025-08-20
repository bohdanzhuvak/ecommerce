import {useEffect} from 'react';
import {useNavigate} from 'react-router';

import {paths} from '@/config/paths';
import {useUser} from './legacy-compatibility';
import {ROLES} from '@/shared/types/api';

type PublicRouteProps = {
  children: React.ReactNode;
};

export const PublicRoute = ({children}: PublicRouteProps) => {
  const navigate = useNavigate();
  const user = useUser();

  useEffect(() => {
    if (user.data) {
      if (user.data.role === ROLES.ADMIN) {
        navigate(paths.admin.root.getHref());
      } else {
        navigate(paths.app.root.getHref());
      }
    }
  }, [user.data, navigate]);

  return <>{children}</>;
};
