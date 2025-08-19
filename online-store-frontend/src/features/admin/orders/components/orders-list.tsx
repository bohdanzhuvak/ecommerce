import React from 'react';
import {useAdminOrders} from '@/features/admin/orders/api/get-admin-orders.ts';
import {Spinner} from '@/shared/components/ui/spinner';

export const AdminOrdersList: React.FC = () => {
  const ordersQuery = useAdminOrders({})
  if (ordersQuery.isLoading) return <div className="flex h-48 w-full items-center justify-center"><Spinner size="lg"/>
  </div>;
  const orders = ordersQuery.data?.content ?? [];
  return (
    <div className="space-y-4">
      {orders.map(o => (
        <div key={o.id} className="rounded border bg-white p-4">
          <div className="flex items-center justify-between">
            <div>
              <div className="font-semibold">Order #{o.id}</div>
              <div className="text-sm text-gray-500">User ID: {o.userId}</div>
            </div>
            <div className="text-right">
              <div className="text-xs uppercase">{o.status}</div>
              <div className="text-blue-600 font-bold">${o.totalPrice.toFixed(2)}</div>
            </div>
          </div>
          <div className="mt-3 grid grid-cols-1 gap-2">
            {o.items.map(it => (
              <div key={`${it.productId}-${it.productName}`} className="flex items-center justify-between bg-gray-50 rounded p-2">
                <div>{it.productName}</div>
                <div className="text-sm">x{it.quantity}</div>
              </div>
            ))}
          </div>
        </div>
      ))}
    </div>
  );
};


