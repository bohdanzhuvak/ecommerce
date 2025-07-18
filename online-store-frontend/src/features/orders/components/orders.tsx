import React from 'react';
import {useOrders} from '../api/get-orders';
import {useUnconfirmedOrders} from '../api/unconfirmed-orders';
import {OrderItem} from './order-item';
import {Spinner} from "@/shared/components/ui/spinner";

interface OrdersProps {
  clientEmail: string;
}

interface UnconfirmedOrdersProps {
}

export const Orders: React.FC<OrdersProps> = ({clientEmail}) => {
  const ordersQuery = useOrders({clientEmail});

  if (ordersQuery.isLoading) {
    return (
      <div className="flex h-48 w-full items-center justify-center">
        <Spinner size="lg"/>
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
          <OrderItem key={order.id} order={order}/>
        ))}
      </div>
    </div>
  );
};

export const UnconfirmedOrders: React.FC<UnconfirmedOrdersProps> = () => {
  const ordersQuery = useUnconfirmedOrders();

  if (ordersQuery.isLoading) {
    return (
      <div className="flex h-48 w-full items-center justify-center">
        <Spinner size="lg"/>
      </div>
    );
  }

  const orders = ordersQuery.data;

  if (!orders || orders.length === 0) {
    return (
      <div className="p-8 text-center">
        <p className="text-gray-600">No unconfirmed orders found</p>
      </div>
    );
  }

  return (
    <div className="p-6">
      <div className="space-y-6">
        {orders.map((order) => (
          <OrderItem key={order.id} order={order}/>
        ))}
      </div>
    </div>
  );
};
