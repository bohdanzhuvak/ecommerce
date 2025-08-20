import {Button} from '@/shared/components/ui/button';
import {ConfirmationDialog} from '@/shared/components/ui/dialog';
import {useNotifications} from '@/shared/components/ui/notifications';
import {useLogout, useUser} from '@/shared/lib/auth/auth';

import {useDeleteUser} from '../api/delete-user';

type DeleteUserProps = {
  userEmail: string;
};

export const DeleteUser = ({userEmail}: DeleteUserProps) => {
  const user = useUser();
  const {addNotification} = useNotifications();
  const logout = useLogout();
  const deleteUserMutation = useDeleteUser({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'User Deleted',
        });
        logout.mutate({} as any);
      },
    },
  });

  if (user.data == null) return null;

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
          onClick={() => deleteUserMutation.mutate({userEmail: userEmail})}
        >
          Delete User
        </Button>
      }
    />
  );
};
