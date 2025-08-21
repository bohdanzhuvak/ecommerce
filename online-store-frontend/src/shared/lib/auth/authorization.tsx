import * as React from 'react';
import {ROLES} from "@/shared/types/api.ts";
import {useAuth} from "@/shared/lib/auth/context/auth-context.tsx";

type RoleTypes = keyof typeof ROLES;

export const useAuthorization = () => {
  const user = useAuth().state.user;

  if (!user) {
    throw Error('User does not exist!');
  }

  const checkAccess = React.useCallback(
    ({allowedRoles}: { allowedRoles: RoleTypes[] }) => {
      if (allowedRoles && allowedRoles.length > 0 && user) {
        return allowedRoles?.includes(user.role as any);
      }

      return true;
    },
    [user],
  );

  return {checkAccess, role: user.role};
};

type AuthorizationProps = {
  forbiddenFallback?: React.ReactNode;
  children: React.ReactNode;
} & (
  | {
  allowedRoles: RoleTypes[];
  policyCheck?: never;
}
  | {
  allowedRoles?: never;
  policyCheck: boolean;
}
  );

export const Authorization = ({
                                policyCheck,
                                allowedRoles,
                                forbiddenFallback = null,
                                children,
                              }: AuthorizationProps) => {
  const {checkAccess} = useAuthorization();

  let canAccess = false;

  if (allowedRoles) {
    canAccess = checkAccess({allowedRoles});
  }

  if (typeof policyCheck !== 'undefined') {
    canAccess = policyCheck;
  }

  return <>{canAccess ? children : forbiddenFallback}</>;
};
