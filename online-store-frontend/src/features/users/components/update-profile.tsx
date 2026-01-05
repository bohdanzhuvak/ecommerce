import { Pen } from 'lucide-react';
import { useState } from 'react';

import { Button } from '@/shared/components/ui/button';
import { Form, FormDrawer, Input } from '@/shared/components/ui/form';
import { useNotifications } from '@/shared/components/ui/notifications';
import { useUpdateUserProfile } from '@/shared/api/generated/users-customer/users-customer';
import { updateProfileInputSchema } from '@/features/users/api.types.ts';

export const UpdateProfile = ({
  currentFirstName,
  currentLastName,
}: {
  currentFirstName?: string;
  currentLastName?: string;
}) => {
  const { addNotification } = useNotifications();
  const [apiError, setApiError] = useState<string | null>(null);
  const updateProfileMutation = useUpdateUserProfile({
    mutation: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Profile Updated',
        });
        setApiError(null);
      },
      onError: (error: any) => {
        const message =
          error?.response?.data?.message || 'Failed to update profile';
        setApiError(message);
        addNotification({
          type: 'error',
          title: 'Update failed',
        });
      },
    },
  });

  return (
    <FormDrawer
      isDone={updateProfileMutation.isSuccess}
      triggerButton={
        <Button icon={<Pen className="size-4" />} size="sm">
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
          updateProfileMutation.mutate({
            data: values,
          });
        }}
        options={{
          defaultValues: {
            firstName: currentFirstName ?? '',
            lastName: currentLastName ?? '',
          },
        }}
        schema={updateProfileInputSchema}
      >
        {({ register, formState }) => (
          <>
            {apiError && (
              <div className="text-red-500 text-sm mb-2">{apiError}</div>
            )}
            <Input
              label="First Name"
              error={formState.errors['firstName']}
              registration={register('firstName')}
            />
            <Input
              label="Last Name"
              error={formState.errors['lastName']}
              registration={register('lastName')}
            />
          </>
        )}
      </Form>
    </FormDrawer>
  );
};
