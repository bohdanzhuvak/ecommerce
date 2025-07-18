import {Pen} from 'lucide-react';
import {useState} from 'react';

import {Button} from '@/shared/components/ui/button';
import {Form, FormDrawer, Input} from '@/shared/components/ui/form';
import {useNotifications} from '@/shared/components/ui/notifications';
import {useLogout, useUser} from '@/shared/lib/auth/auth';

import {UpdateProfileInput, updateProfileInputSchema, useUpdateProfile,} from '../api/update-profile';
import {Authorization} from "@/shared/lib/auth/authorization";

export const UpdateProfile = () => {
  const user = useUser();
  const logout = useLogout();
  const {addNotification} = useNotifications();
  const [apiError, setApiError] = useState<string | null>(null);
  const updateProfileMutation = useUpdateProfile({
    mutationConfig: {
      onSuccess: (_data, variables) => {
        const prevEmail = user.data?.email;
        const newEmail = variables.data.email;
        if (prevEmail && newEmail && prevEmail !== newEmail) {
          addNotification({
            type: 'success',
            title: 'Email changed',
          });
          logout.mutate(undefined);
        } else {
          addNotification({
            type: 'success',
            title: 'Profile Updated',
          });
        }
        setApiError(null);
      },
      onError: (error: any) => {
        const message = error?.response?.data?.message || 'Failed to update profile';
        setApiError(message);
        addNotification({
          type: 'error',
          title: 'Update failed'
        });
      },
    },
  });

  return (
    <FormDrawer
      isDone={updateProfileMutation.isSuccess}
      triggerButton={
        <Button icon={<Pen className="size-4"/>} size="sm">
          Update Profile
        </Button>
      }
      title="Update Profile"
      submitButton={
        <Button
          form="update-profile"
          type="submit"
          size="sm"
          isLoading={updateProfileMutation.isPending}
        >
          Submit
        </Button>
      }
    >
      <Form
        id="update-profile"
        onSubmit={(values) => {
          const data: UpdateProfileInput = {...values};
          if (!data.password) {
            delete data.password;
          }
          updateProfileMutation.mutate({data});
        }}
        options={{
          defaultValues: {
            email: user.data?.email ?? '',
            name: user.data?.name ?? '',
            phone: user.data?.phone ?? '',
            birthDate: user.data?.birthDate ? new Date(user.data.birthDate).toISOString().split('T')[0] : '',
            password: '',
          },
        }}
        schema={updateProfileInputSchema}
      >
        {({register, formState}) => (
          <>
            {apiError && (
              <div className="text-red-500 text-sm mb-2">{apiError}</div>
            )}
            <Input
              label="Email"
              error={formState.errors['email']}
              registration={register('email')}
            />
            <Input
              label="Name"
              error={formState.errors['name']}
              registration={register('name')}
            />
            <Input
              label="New Password"
              type="password"
              error={formState.errors['password']}
              registration={register('password')}
              autoComplete="new-password"
              placeholder="Leave blank to keep current password"
            />
            <Authorization allowedRoles={['EMPLOYEE']}>
              <Input
                label="Phone"
                error={formState.errors['phone']}
                registration={register('phone')}
              />
              <Input
                label="Birth Date"
                type="date"
                error={formState.errors['birthDate']}
                registration={register('birthDate')}
              />
            </Authorization>
          </>
        )}
      </Form>
    </FormDrawer>
  );
};
