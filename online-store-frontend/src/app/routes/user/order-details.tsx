import React from 'react';
import { Link, useParams } from 'react-router';
import { QueryClient } from '@tanstack/react-query';
import { ContentLayout } from '@/shared/components/layouts';
import { OrderDetails } from '@/features/orders/components/order-details';
import { ErrorBoundary } from 'react-error-boundary';
import { useAuth } from '@/shared/lib/auth';
import { ArrowLeft } from 'lucide-react';
import { paths } from '@/config/paths.ts';
import { getGetOrdersQueryOptions } from '@/shared/api';

export const orderDetailsLoader =
  (queryClient: QueryClient) =>
  async ({ params }: { params: any }) => {
    const orderQuery = getGetOrdersQueryOptions(params.orderId);
    return (
      queryClient.getQueryData(orderQuery.queryKey) ??
      (await queryClient.fetchQuery(orderQuery))
    );
  };

export const OrderDetailsRoute = () => {
  const { orderId } = useParams<{ orderId: string }>();
  const user = useAuth().state.user;
  if (!user || !orderId) {
    return null;
  }

  return (
    <ContentLayout title="Order Details">
      <div className="mt-8">
        {/* Back to Orders */}
        <div className="mb-6">
          <Link
            to={paths.app.orders.root.getHref()}
            className="inline-flex items-center text-sm text-blue-600 hover:text-blue-800"
          >
            <ArrowLeft className="w-4 h-4 mr-2" />
            Back to Orders
          </Link>
        </div>

        <ErrorBoundary
          fallback={
            <div className="text-center py-8">
              <p className="text-gray-600">
                Failed to load order details. Try to refresh the page.
              </p>
            </div>
          }
        >
          <OrderDetails orderId={orderId} />
        </ErrorBoundary>
      </div>
    </ContentLayout>
  );
};
