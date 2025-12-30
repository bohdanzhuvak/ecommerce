import React from 'react';
import { OrderItem } from './order-item';
import { Spinner } from '@/shared/components/ui/spinner';
import { useGetOrders } from '@/shared/api';

interface OrdersProps {
  clientEmail: string;
}

export const Orders: React.FC<OrdersProps> = () => {
  const ordersQuery = useGetOrders();

  if (ordersQuery.isLoading) {
    return (
      <div className="flex h-48 w-full items-center justify-center">
        <Spinner size="lg" />
      </div>
    );
  }

  const orders = ordersQuery.data;

  if (!orders || orders.length === 0) {
    return (
      <div className="p-8 text-center">
        <p className="text-gray-600">You don't have any orders yet</p>
      </div>
    );
  }

  return (
    <div className="p-6">
      <div className="space-y-6">
        {orders.map((order) => (
          <OrderItem key={order.id} order={order} />
        ))}
      </div>
    </div>
  );
};
