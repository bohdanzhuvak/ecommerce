import React from 'react';
import { Button } from '@/shared/components/ui/button';
import { useNotifications } from '@/shared/components/ui/notifications';
import { useAuth } from '@/shared/lib/auth';
import { useQueryClient } from '@tanstack/react-query';
import { getGetCartQueryKey, useAddItemToCart } from '@/shared/api';

interface AddToCartButtonProps {
  productId: string;
  className?: string;
}

export const AddToCartButton: React.FC<AddToCartButtonProps> = ({
  productId,
  className,
}) => {
  const { addNotification } = useNotifications();
  const { state } = useAuth();
  const queryClient = useQueryClient();

  const addToCartMutation = useAddItemToCart({
    mutation: {
      onSuccess: () => {
        // Invalidate cart query to refetch
        queryClient.invalidateQueries({
          queryKey: getGetCartQueryKey(),
        });

        addNotification({
          type: 'success',
          title: 'Product added to cart successfully!',
        });
      },
      onError: (error) => {
        addNotification({
          type: 'error',
          title: 'Failed to add product to cart',
          message: error instanceof Error ? error.message : 'Unknown error',
        });
      },
    },
  });

  const handleAddToCart = () => {
    if (!state.user) {
      addNotification({
        type: 'error',
        title: 'Please login to add items to cart',
      });
      return;
    }

    addToCartMutation.mutate({
      data: {
        productId,
        quantity: 1,
      },
    });
  };

  return (
    <Button
      size="sm"
      onClick={handleAddToCart}
      isLoading={addToCartMutation.isPending}
      className={className}
      disabled={!state.isAuthenticated}
    >
      Add to Cart
    </Button>
  );
};
