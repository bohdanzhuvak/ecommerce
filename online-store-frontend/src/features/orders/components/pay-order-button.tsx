import React from 'react';
import {Button} from '@/shared/components/ui/button';
import {usePayOrder} from '../api/pay-order';
import {useNotifications} from '@/shared/components/ui/notifications';
import {ConfirmationDialog} from '@/shared/components/ui/dialog';

interface PayOrderButtonProps {
  orderId: number;
  orderTotal: number;
  userBalance: number;
  className?: string;
}

export const PayOrderButton: React.FC<PayOrderButtonProps> = ({
  orderId,
  orderTotal,
  userBalance,
  className
}) => {
  const { addNotification } = useNotifications();
  const hasSufficientFunds = userBalance >= orderTotal;

  const payOrderMutation = usePayOrder({
    orderId,
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Order paid successfully!',
          message: `Order #${orderId} has been paid with your balance.`,
        });
      },
      onError: (error) => {
        addNotification({
          type: 'error',
          title: 'Payment failed',
          message: error.message,
        });
      }
    },
  });

  if (!hasSufficientFunds) {
    return (
      <Button
        variant="outline"
        size="sm"
        disabled
        className="text-gray-500 cursor-not-allowed"
        title={`Insufficient funds. You need $${(orderTotal - userBalance).toFixed(2)} more.`}
      >
        Insufficient Funds
      </Button>
    );
  }

  return (
    <ConfirmationDialog
      isDone={payOrderMutation.isSuccess}
      icon="info"
      title="Confirm Payment"
      body={`Are you sure you want to pay $${orderTotal.toFixed(2)} for order #${orderId}? This will be deducted from your balance.`}
      triggerButton={
        <Button
          variant="outline"
          size="sm"
          className={className}
        >
          Pay with Balance
        </Button>
      }
      confirmButton={
        <Button
          isLoading={payOrderMutation.isPending}
          type="button"
          variant="default"
          onClick={() => payOrderMutation.mutate(orderId)}
        >
          {payOrderMutation.isPending ? 'Processing...' : 'Confirm Payment'}
        </Button>
      }
    />
  );
};
