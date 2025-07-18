import {useMutation} from '@tanstack/react-query';
import {z} from 'zod';

import {api} from '@/shared/lib/api-client';
import {useUser} from '@/shared/lib/auth/auth';
import {MutationConfig} from '@/shared/lib/react-query';

export const updateProfileInputSchema = z.object({
  email: z.string().min(1, 'Required'),
  password: z.string().min(6, 'Password must be at least 6 characters long').optional().or(z.literal('')),
  name: z.string().min(1, 'Required'),
  phone: z.string().optional(),
  birthDate: z
    .string()
    .optional()
    .refine((val) => {
      if (!val) return true;
      // Проверяем формат yyyy-mm-dd
      return /^\d{4}-\d{2}-\d{2}$/.test(val);
    }, 'Invalid date format'),
});

export type UpdateProfileInput = z.infer<typeof updateProfileInputSchema>;

export const updateProfile = ({data}: { data: UpdateProfileInput }) => {
  return api.put(`/auth/me/update`, data);
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
