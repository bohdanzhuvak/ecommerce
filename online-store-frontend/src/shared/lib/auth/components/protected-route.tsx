import React, { useEffect } from 'react';
import { Navigate, useLocation, useNavigate } from 'react-router';
import { useAuth } from '@/shared/lib/auth';
import { paths } from '@/config/paths';

interface ProtectedRouteProps {
  children: React.ReactNode;
  fallback?: React.ReactNode;
}

export const ProtectedRoute: React.FC<ProtectedRouteProps> = ({
  children,
  fallback = null,
}) => {
  const { state, getRedirectPath } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    if (!state.isLoading && !state.isAuthenticated) {
      const redirectPath = paths.auth.login.getHref(location.pathname);
      navigate(redirectPath, { replace: true });
    }
  }, [state.isAuthenticated, state.isLoading, location.pathname, navigate]);

  if (state.isLoading) {
    return (
      fallback || (
        <div className="flex h-screen w-screen items-center justify-center">
          <div className="text-lg">Loading...</div>
        </div>
      )
    );
  }

  if (!state.isAuthenticated || !state.user) {
    const redirectPath = paths.auth.login.getHref(location.pathname);
    return <Navigate to={redirectPath} replace />;
  }

  return <>{children}</>;
};
