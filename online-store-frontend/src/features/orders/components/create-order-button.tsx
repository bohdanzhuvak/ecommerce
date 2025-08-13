import React from 'react';
import {Button} from '@/shared/components/ui/button';
import {useCreateOrderFromCart} from '../api/create-order-from-cart';
import {useNotifications} from '@/shared/components/ui/notifications';

interface CreateOrderButtonProps {
  className?: string;
  disabled?: boolean;
}

export const CreateOrderButton: React.FC<CreateOrderButtonProps> = ({
  className,
  disabled = false
}) => {
  const {addNotification} = useNotifications();
  const createOrderMutation = useCreateOrderFromCart({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Order created successfully!',
          message: 'Your order has been placed and your cart has been cleared.',
        });
      }
    },
  });

  return (
    <Button
      onClick={() => createOrderMutation.mutate(undefined)}
      isLoading={createOrderMutation.isPending}
      disabled={disabled || createOrderMutation.isPending}
      className={className}
    >
      {createOrderMutation.isPending ? 'Creating Order...' : 'Create Order'}
    </Button>
  );
};
