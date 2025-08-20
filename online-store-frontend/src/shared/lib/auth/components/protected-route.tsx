import React, { useEffect } from 'react';
import { Navigate, useLocation } from 'react-router';
import { useAuth } from '../context/auth-context';
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

  // Show loading state
  if (state.isLoading) {
    return fallback || (
      <div className="flex h-screen w-screen items-center justify-center">
        <div className="text-lg">Loading...</div>
      </div>
    );
  }

  // Check if user is authenticated
  if (!state.isAuthenticated || !state.user) {
    const redirectPath = paths.auth.login.getHref(location.pathname);
    return <Navigate to={redirectPath} replace />;
  }

  // Check if user has required roles
  if (requiredRoles.length > 0 && !canAccess(requiredRoles)) {
    const redirectPath = getRedirectPath(location.pathname);
    return <Navigate to={redirectPath} replace />;
  }

  return <>{children}</>;
};

// Convenience components for common use cases
export const AdminRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => (
  <ProtectedRoute requiredRoles={['ADMIN']}>
    {children}
  </ProtectedRoute>
);

export const UserRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => (
  <ProtectedRoute requiredRoles={['USER', 'ADMIN']}>
    {children}
  </ProtectedRoute>
);

export const AuthenticatedRoute: React.FC<{ children: React.ReactNode }> = ({ children }) => (
  <ProtectedRoute>
    {children}
  </ProtectedRoute>
);
