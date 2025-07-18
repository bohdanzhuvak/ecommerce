import React from 'react';
import {OrderDTO} from '../api/get-orders';
import {useConfirmOrder} from '../api/confirm-order';
import {Button} from '@/shared/components/ui/button';
import {useNotifications} from '@/shared/components/ui/notifications';
import {Authorization, ROLES} from '@/shared/lib/auth/authorization';

interface OrderItemProps {
  order: OrderDTO;
}

export const OrderItem: React.FC<OrderItemProps> = ({order}) => {
  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const getStatusColor = (status: string) => {
    switch (status.toLowerCase()) {
      case 'pending':
        return 'bg-yellow-100 text-yellow-800';
      case 'confirmed':
        return 'bg-blue-100 text-blue-800';
      case 'processing':
        return 'bg-purple-100 text-purple-800';
      case 'shipped':
        return 'bg-orange-100 text-orange-800';
      case 'delivered':
        return 'bg-green-100 text-green-800';
      case 'cancelled':
        return 'bg-red-100 text-red-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  const {addNotification} = useNotifications();
  const confirmOrderMutation = useConfirmOrder({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Order confirmed',
          message: 'Order has been successfully confirmed.'
        });
      },
      onError: (error) => {
        addNotification({
          type: 'error',
          title: 'Failed to confirm order',
          message: error.message,
        });
      }
    }
  });

  return (
    <div className="border rounded-lg p-6 bg-white shadow-sm">
      <div className="flex justify-between items-start mb-4">
        <div>
          <h3 className="text-lg font-semibold">Order #{order.id}</h3>
          <span className="font-medium"> by:</span> {order.clientEmail}
          <p className="text-sm text-gray-600">{formatDate(order.orderDate)}</p>
        </div>
        <div className="text-right">
          <span
            className={`px-3 py-1 rounded-full text-xs font-medium ${getStatusColor(order.employeeEmail ? 'confirmed' : 'pending')}`}>
            {order.employeeEmail ? 'confirmed' : 'pending'}
          </span>
          <p className="text-lg font-bold text-blue-600 mt-1">${order.price.toFixed(2)}</p>
        </div>
      </div>

      <div className="space-y-3">
        {order.bookItems.map((item) => (
          <div key={item.bookName} className="flex justify-between items-center p-3 bg-gray-50 rounded">
            <div className="flex-1">
              <h4 className="font-medium">{item.bookName}</h4>
            </div>
            <div className="text-right">
              <p className="font-medium">Quantity: {item.quantity}</p>
            </div>
          </div>
        ))}
      </div>

      {order.employeeEmail && (
        <div className="mt-4 pt-4 border-t">
          <p className="text-sm text-gray-600">
            <span className="font-medium">Confirmed by:</span> {order.employeeEmail}
          </p>
        </div>
      )}
      {!order.employeeEmail && (
        <Authorization allowedRoles={[ROLES.EMPLOYEE]}>
          <div className="mt-4 pt-4 border-t flex justify-end">
            <Button
              isLoading={confirmOrderMutation.isPending}
              onClick={() => confirmOrderMutation.mutate(order.id)}
              variant="default"
            >
              {confirmOrderMutation.isPending ? 'Confirming...' : 'Confirm Order'}
            </Button>
          </div>
        </Authorization>
      )}
    </div>
  );
};
