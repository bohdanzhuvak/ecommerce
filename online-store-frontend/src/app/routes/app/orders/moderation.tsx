import {ContentLayout} from '@/shared/components/layouts';
import {UnconfirmedOrders} from '@/features/orders/components';
import {Authorization, ROLES} from '@/shared/lib/auth/authorization';
import {ErrorBoundary} from 'react-error-boundary';
import {useOrdersByEmployee} from '@/features/orders/api/orders-by-employee';
import {OrderItem} from '@/features/orders/components/order-item';
import {useUser} from '@/shared/lib/auth/auth';
import React from 'react';
import type {OrderDTO} from '@/features/orders/api/get-orders';

export const ModerationOrdersRoute = () => {
  const user = useUser().data;
  // Получаем подтвержденные заказы, назначенные этому сотруднику
  const ordersQuery = useOrdersByEmployee({employeeEmail: user?.email || ''});
  const confirmedOrders = ordersQuery.data || [];

  return (
    <Authorization allowedRoles={[ROLES.EMPLOYEE]}>
      <ContentLayout title="Order Moderation">
        <div className="mt-8">
          <h2 className="text-xl font-semibold mb-4">Unconfirmed Orders</h2>
          <ErrorBoundary fallback={<div>Failed to load unconfirmed orders.</div>}>
            <UnconfirmedOrders/>
          </ErrorBoundary>
        </div>
        <div className="mt-12">
          <h2 className="text-xl font-semibold mb-4">Confirmed Orders</h2>
          <ErrorBoundary fallback={<div>Failed to load confirmed orders.</div>}>
            {ordersQuery.isLoading ? (
              <div className="flex h-48 w-full items-center justify-center">Loading...</div>
            ) : confirmedOrders.length === 0 ? (
              <div className="p-8 text-center text-gray-600">No confirmed orders found</div>
            ) : (
              <div className="p-6 space-y-6">
                {confirmedOrders.map((order: OrderDTO) => (
                  <OrderItem key={order.orderDate} order={order}/>
                ))}
              </div>
            )}
          </ErrorBoundary>
        </div>
      </ContentLayout>
    </Authorization>
  );
};
