import React, { createContext, useContext, useEffect, useState, ReactNode } from 'react';
import { useQueryClient } from '@tanstack/react-query';
import { AuthManager } from '@/shared/lib/auth';
import { AuthContextValue, AuthState, LoginInput, RegisterInput } from '../types';

const AuthContext = createContext<AuthContextValue | undefined>(undefined);

interface AuthProviderProps {
  children: ReactNode;
}

export const AuthProvider: React.FC<AuthProviderProps> = ({ children }) => {
  const [authManager] = useState(() => AuthManager.getInstance());
  const queryClient = useQueryClient();
  const [state, setState] = useState<AuthState>({
    isAuthenticated: false,
    user: null,
    isLoading: true,
    error: null
  });

  useEffect(() => {
    if (queryClient && typeof queryClient.clear === 'function') {
      authManager.setQueryClient(queryClient);
    }
  }, [authManager, queryClient]);

  useEffect(() => {
    const handleStateChange = (newState: AuthState) => {
      setState(newState);
    };

    const handleLoginSuccess = () => {
      // State will be updated via stateChanged event
    };

    const handleLogoutSuccess = () => {
      // State will be updated via stateChanged event
    };

    const handleTokenExpired = () => {
      // State will be updated via stateChanged event
    };

    authManager.on('stateChanged', handleStateChange);
    authManager.on('loginSuccess', handleLoginSuccess);
    authManager.on('logoutSuccess', handleLogoutSuccess);
    authManager.on('tokenExpired', handleTokenExpired);

    authManager.initialize();

    return () => {
      authManager.off('stateChanged', handleStateChange);
      authManager.off('loginSuccess', handleLoginSuccess);
      authManager.off('logoutSuccess', handleLogoutSuccess);
      authManager.off('tokenExpired', handleTokenExpired);
    };
  }, [authManager]);

  const login = async (credentials: LoginInput) => {
    return await authManager.login(credentials);
  };

  const register = async (credentials: RegisterInput) => {
    return await authManager.register(credentials);
  };

  const logout = async () => {
    await authManager.logout();
  };

  const canAccess = (requiredRoles: string[] = []) => {
    return authManager.canAccess(requiredRoles);
  };

  const getRedirectPath = (originalPath: string) => {
    return authManager.getRedirectPath(originalPath);
  };

  const value: AuthContextValue = {
    state,
    login,
    register,
    logout,
    canAccess,
    getRedirectPath
  };

  return (
    <AuthContext.Provider value={value}>
      {children}
    </AuthContext.Provider>
  );
};

export const useAuth = (): AuthContextValue => {
  const context = useContext(AuthContext);
  if (context === undefined) {
    throw new Error('useAuth must be used within an AuthProvider');
  }
  return context;
};
