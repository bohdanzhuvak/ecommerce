import {useMutation} from '@tanstack/react-query';
import {z} from 'zod';

import {api} from '@/shared/lib/api-client';
import {useUser} from '@/shared/lib/auth/auth';
import {MutationConfig} from '@/shared/lib/react-query';

export const updateProfileInputSchema = z.object({
  email: z.string().email('Invalid email').min(1, 'Required'),
  username: z.string().min(1, 'Required'),
});

export type UpdateProfileInput = z.infer<typeof updateProfileInputSchema>;

export const updateProfile = ({data}: { data: UpdateProfileInput }) => {
  return api.put(`/users/me`, data);
};

type UseUpdateProfileOptions = {
  mutationConfig?: MutationConfig<typeof updateProfile>;
};

export const useUpdateProfile = ({
  mutationConfig,
}: UseUpdateProfileOptions = {}) => {
  const {refetch: refetchUser, data: currentUser} = useUser();

  const {onSuccess, ...restConfig} = mutationConfig || {};

  return useMutation({
    onSuccess: (_data, variables, context) => {
      const prevEmail = currentUser?.email;
      const newEmail = variables?.data?.email;
      if (!(prevEmail && newEmail && prevEmail !== newEmail)) {
        refetchUser();
      }
      onSuccess?.(_data, variables, context);
    },
    ...restConfig,
    mutationFn: updateProfile,
  });
};
