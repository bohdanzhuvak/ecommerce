import {useMutation} from '@tanstack/react-query';
import {z} from 'zod';

import {api} from '@/shared/lib/api-client';
import {MutationConfig} from '@/shared/lib/react-query';
import {UpdateProfileRequest} from '../api.types';

export const updateProfileInputSchema = z.object({
  email: z.string().email('Invalid email').min(1, 'Required'),
  username: z.string().min(1, 'Required'),
});

export const updateProfile = ({data}: { data: UpdateProfileRequest }) => {
  return api.put(`/users/me`, data);
};

type UseUpdateProfileOptions = {
  mutationConfig?: MutationConfig<typeof updateProfile>;
};

export const useUpdateProfile = ({
  mutationConfig,
}: UseUpdateProfileOptions = {}) => {

  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    onSuccess: (_data, variables, context) => {
      onSuccess?.(_data, variables, context);
    },
    ...restConfig,
    mutationFn: updateProfile,
  });
};
