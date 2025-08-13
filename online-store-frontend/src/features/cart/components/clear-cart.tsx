import {Trash} from 'lucide-react';

import {Button} from '@/shared/components/ui/button';
import {ConfirmationDialog} from '@/shared/components/ui/dialog';
import {useNotifications} from '@/shared/components/ui/notifications';
import {useClearCart} from "@/features/cart/api/clear-cart";


export const ClearCart = () => {
  const {addNotification} = useNotifications();
  const clearCartMutation = useClearCart({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Cart cleared successfully!',
        });
      },
    },
  });

  return (
    <ConfirmationDialog
      isDone={clearCartMutation.isSuccess}
      icon="danger"
      title="Delete Comment"
      body="Are you sure you want to clear your cart?"
      triggerButton={
        <Button
          variant="destructive"
          size="sm"
          icon={<Trash className="size-4"/>}
        >
          Clear cart
        </Button>
      }
      confirmButton={
        <Button
          isLoading={clearCartMutation.isPending}
          type="button"
          variant="destructive"
          onClick={() => clearCartMutation.mutate(undefined)}
        >
          Clear cart
        </Button>
      }
    />
  );
};
