import React from 'react';
import { Navigate, useLocation } from 'react-router';
import { useAuth } from '@/shared/lib/auth';

interface PublicRouteProps {
  children: React.ReactNode;
  redirectAuthenticated?: boolean;
  fallback?: React.ReactNode;
}

export const PublicRoute: React.FC<PublicRouteProps> = ({
  children,
  redirectAuthenticated = true,
  fallback = null
}) => {
  const { state, getRedirectPath } = useAuth();
  const location = useLocation();

  if (state.isLoading) {
    return fallback || (
      <div className="flex h-screen w-screen items-center justify-center">
        <div className="text-lg">Loading...</div>
      </div>
    );
  }

  if (redirectAuthenticated && state.isAuthenticated && state.user) {
    const redirectPath = getRedirectPath(location.pathname);
    return <Navigate to={redirectPath} replace />;
  }

  return <>{children}</>;
};

export const AuthRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => (
  <PublicRoute redirectAuthenticated={true}>
    {children}
  </PublicRoute>
);

export const LandingRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => (
  <PublicRoute redirectAuthenticated={false}>
    {children}
  </PublicRoute>
);
