import { Button } from '@/shared/components/ui/button';
import { useNotifications } from '@/shared/components/ui/notifications';
import { useRemoveItemFromCart } from '@/shared/api';

type RemoveFromCartProps = {
  productId: string;
};

export const RemoveFromCart = ({ productId }: RemoveFromCartProps) => {
  const { addNotification } = useNotifications();
  const removeFromCartMutation = useRemoveItemFromCart({
    mutation: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Item removed from cart successfully!',
        });
      },
    },
  });

  return (
    <Button
      isLoading={removeFromCartMutation.isPending}
      disabled={removeFromCartMutation.isPending}
      variant="outline"
      className="text-red-600 hover:text-red-700"
      size="sm"
      onClick={() => removeFromCartMutation.mutate({ productId: productId })}
    >
      {removeFromCartMutation.isPending ? 'Removing...' : 'Remove'}
    </Button>
  );
};
