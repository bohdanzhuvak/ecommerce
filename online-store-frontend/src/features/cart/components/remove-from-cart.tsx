import {Button} from '@/shared/components/ui/button';
import {useNotifications} from '@/shared/components/ui/notifications';
import {useRemoveFromCart} from "@/features/cart/api/remove-from-cart";

type RemoveFromCartProps = {
  clientEmail: string;
  bookName: string;
};

export const RemoveFromCart = ({clientEmail, bookName}: RemoveFromCartProps) => {
  const {addNotification} = useNotifications();
  const removeFromCartMutation = useRemoveFromCart({
    clientEmail: clientEmail,
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Book removed from cart successfully!',
        });
      }
    }
  });

  return (
    <Button
      isLoading={removeFromCartMutation.isPending}
      disabled={removeFromCartMutation.isPending}
      variant="outline"
      className="text-red-600 hover:text-red-700"
      size="sm"
      onClick={() => removeFromCartMutation.mutate({clientEmail: clientEmail, bookName: bookName})}
    >
      {removeFromCartMutation.isPending ? 'Removing...' : 'Remove'}
    </Button>
  );
};
