import { Button } from '@/shared/components/ui/button';
import { ConfirmationDialog } from '@/shared/components/ui/dialog';
import { useNotifications } from '@/shared/components/ui/notifications';

import { useAuth } from '@/shared/lib/auth';
import { useDeleteUserProfile } from '@/shared/api';

export const DeleteUser = () => {
  const auth = useAuth();
  const user = auth.state.user;
  const { addNotification } = useNotifications();
  const deleteUserMutation = useDeleteUserProfile({
    mutation: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'User Deleted',
        });
        auth.logout();
      },
    },
  });

  if (user == null) return null;

  return (
    <ConfirmationDialog
      icon="danger"
      title="Delete User"
      body="Are you sure you want to delete this user?"
      triggerButton={<Button variant="destructive">Delete</Button>}
      confirmButton={
        <Button
          isLoading={deleteUserMutation.isPending}
          type="button"
          variant="destructive"
          onClick={() => deleteUserMutation.mutate()}
        >
          Delete User
        </Button>
      }
    />
  );
};
