import {Button} from '@/shared/components/ui/button';
import {useNotifications} from '@/shared/components/ui/notifications';
import {useRemoveFromCart} from "@/features/cart/api/remove-from-cart";

type RemoveFromCartProps = {
  productId: number;
};

export const RemoveFromCart = ({productId}: RemoveFromCartProps) => {
  const {addNotification} = useNotifications();
  const removeFromCartMutation = useRemoveFromCart({
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
      onClick={() => removeFromCartMutation.mutate({productId: productId})}
    >
      {removeFromCartMutation.isPending ? 'Removing...' : 'Remove'}
    </Button>
  );
};
