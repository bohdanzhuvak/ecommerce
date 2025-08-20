import React from 'react';
import { useAuth } from './context/auth-context';
import { LegacyAuthHook, LoginInput, RegisterInput } from './types';

// Legacy hooks that maintain the old API
export const useUser = (): LegacyAuthHook => {
  const auth = useAuth();
  
  return {
    data: auth.state.user,
    isLoading: auth.state.isLoading,
    error: auth.state.error,
    mutate: async (credentials: LoginInput | RegisterInput) => {
      if ('name' in credentials) {
        await auth.register(credentials);
      } else {
        await auth.login(credentials);
      }
    },
    isPending: auth.state.isLoading,
    refetch: async () => {
      // This would reload user data in a real implementation
      console.log('Refetch not implemented in new architecture');
    }
  };
};

export const useLogin = (): LegacyAuthHook => {
  const auth = useAuth();
  
  return {
    data: auth.state.user,
    isLoading: auth.state.isLoading,
    error: auth.state.error,
    mutate: async (credentials: any) => {
      if ('name' in credentials) {
        await auth.register(credentials);
      } else {
        await auth.login(credentials);
      }
    },
    isPending: auth.state.isLoading,
    refetch: async () => {
      console.log('Refetch not implemented in new architecture');
    }
  };
};

export const useRegister = (): LegacyAuthHook => {
  const auth = useAuth();
  
  return {
    data: auth.state.user,
    isLoading: auth.state.isLoading,
    error: auth.state.error,
    mutate: async (credentials: any) => {
      if ('name' in credentials) {
        await auth.register(credentials);
      } else {
        await auth.login(credentials);
      }
    },
    isPending: auth.state.isLoading,
    refetch: async () => {
      console.log('Refetch not implemented in new architecture');
    }
  };
};

export const useLogout = (): LegacyAuthHook => {
  const auth = useAuth();
  
  return {
    data: auth.state.user,
    isLoading: auth.state.isLoading,
    error: auth.state.error,
    mutate: async () => {
      await auth.logout();
    },
    isPending: auth.state.isLoading,
    refetch: async () => {
      console.log('Refetch not implemented in new architecture');
    }
  };
};

// Legacy AuthLoader component
export const AuthLoader: React.FC<{ children: React.ReactNode; renderLoading?: () => React.ReactNode }> = ({ 
  children, 
  renderLoading 
}) => {
  const auth = useAuth();
  
  if (auth.state.isLoading) {
    return renderLoading ? renderLoading() : (
      <div className="flex h-screen w-screen items-center justify-center">
        <div className="text-lg">Loading...</div>
      </div>
    );
  }
  
  return <>{children}</>;
};
