import {useMutation, useQueryClient} from '@tanstack/react-query';

import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';

import {getClientsQueryOptions} from './get-clients';

export type DeleteUserDTO = {
  userEmail: string;
  userRole: 'CLIENT' | 'EMPLOYEE';
};

export const deleteUser = ({userEmail, userRole}: DeleteUserDTO) => {
  return userRole == 'CLIENT' ? api.delete(`/clients/${userEmail}`) : api.delete(`/employees/${userEmail}`);
};

type UseDeleteUserOptions = {
  mutationConfig?: MutationConfig<typeof deleteUser>;
};

export const useDeleteUser = ({
                                mutationConfig,
                              }: UseDeleteUserOptions = {}) => {
  const queryClient = useQueryClient();

  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    onSuccess: (...args) => {
      queryClient.invalidateQueries({
        queryKey: getClientsQueryOptions().queryKey,
      });
      onSuccess?.(...args);
    },
    ...restConfig,
    mutationFn: deleteUser,
  });
};
