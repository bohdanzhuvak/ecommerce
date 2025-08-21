import React, {useEffect} from 'react';
import {Navigate, useLocation, useNavigate, useSearchParams} from 'react-router';
import { useAuth } from '@/shared/lib/auth';
import {paths} from "@/config/paths.ts";

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
  const [searchParams] = useSearchParams();
  const redirectTo = searchParams.get('redirectTo');

  const navigate = useNavigate();

  useEffect(() => {
    if (redirectAuthenticated && state.isAuthenticated && state.user) {
      const targetPath = redirectTo || paths.home.getHref();
      navigate(targetPath, { replace: true });
    }
  }, [state.isAuthenticated, state.user, navigate, redirectTo, state.isLoading]);

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
