import React, { useEffect } from 'react';
import { Navigate, useLocation } from 'react-router';
import { useAuth } from '@/shared/lib/auth';
import { paths } from '@/config/paths';

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

  // Show loading state
  if (state.isLoading) {
    return fallback || (
      <div className="flex h-screen w-screen items-center justify-center">
        <div className="text-lg">Loading...</div>
      </div>
    );
  }

  // If user is authenticated and we should redirect them
  if (redirectAuthenticated && state.isAuthenticated && state.user) {
    const redirectPath = getRedirectPath(location.pathname);
    return <Navigate to={redirectPath} replace />;
  }

  return <>{children}</>;
};

// Special component for auth pages (login/register) that redirects authenticated users
export const AuthRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => (
  <PublicRoute redirectAuthenticated={true}>
    {children}
  </PublicRoute>
);

// Component for truly public pages (like landing page) that don't redirect
export const LandingRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => (
  <PublicRoute redirectAuthenticated={false}>
    {children}
  </PublicRoute>
);
