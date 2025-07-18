import React from 'react';
import {Button} from '@/shared/components/ui/button';
import {useAddToCart} from '../api/add-to-cart';
import {useNotifications} from "@/shared/components/ui/notifications";

interface AddToCartButtonProps {
  bookName: string;
  clientEmail: string;
  className?: string;
}

export const AddToCartButton: React.FC<AddToCartButtonProps> = ({
                                                                  bookName,
                                                                  clientEmail,
                                                                  className
                                                                }) => {
  const {addNotification} = useNotifications();
  const addToCartMutation = useAddToCart({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Book added to cart successfully!',
        });
      },
      onError: (error) => {
        addNotification({
          type: 'error',
          title: 'Failed to add book to cart',
          message: error.message,
        });
      }
    },
    clientEmail: clientEmail,
  });

  return (
    <Button
      size="sm"
      onClick={() => addToCartMutation.mutate({clientEmail, data: {bookName, quantity: 1}})}
      isLoading={addToCartMutation.isPending}
      className={className}
    >
      Add to Cart
    </Button>
  );
}; 