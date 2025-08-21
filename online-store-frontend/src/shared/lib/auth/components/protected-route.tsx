import React, { useEffect } from 'react';
import { Navigate, useLocation, useNavigate } from 'react-router';
import { useAuth } from '@/shared/lib/auth';
import { paths } from '@/config/paths';

interface ProtectedRouteProps {
  children: React.ReactNode;
  requiredRoles?: string[];
  fallback?: React.ReactNode;
}

export const ProtectedRoute: React.FC<ProtectedRouteProps> = ({
  children,
  requiredRoles = [],
  fallback = null
}) => {
  const { state, canAccess, getRedirectPath } = useAuth();
  const location = useLocation();
  const navigate = useNavigate();

  useEffect(() => {
    if (!state.isLoading && !state.isAuthenticated) {
      const redirectPath = paths.auth.login.getHref(location.pathname);
      navigate(redirectPath, { replace: true });
    }
  }, [state.isAuthenticated, state.isLoading, location.pathname, navigate]);

  if (state.isLoading) {
    return fallback || (
      <div className="flex h-screen w-screen items-center justify-center">
        <div className="text-lg">Loading...</div>
      </div>
    );
  }

  if (!state.isAuthenticated || !state.user) {
    const redirectPath = paths.auth.login.getHref(location.pathname);
    return <Navigate to={redirectPath} replace />;
  }

  if (requiredRoles.length > 0 && !canAccess(requiredRoles)) {
    const redirectPath = getRedirectPath(location.pathname);
    return <Navigate to={redirectPath} replace />;
  }

  return <>{children}</>;
};

export const AdminRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => (
  <ProtectedRoute requiredRoles={['ADMIN']}>
    {children}
  </ProtectedRoute>
);

export const UserRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => (
  <ProtectedRoute requiredRoles={['USER']}>
    {children}
  </ProtectedRoute>
);

export const AuthenticatedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => (
  <ProtectedRoute>
    {children}
  </ProtectedRoute>
);
