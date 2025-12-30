import { QueryClient } from '@tanstack/react-query';

import { ContentLayout } from '@/shared/components/layouts';
import { Orders } from '@/features/orders/components/orders';
import { ErrorBoundary } from 'react-error-boundary';
import { useAuth } from '@/shared/lib/auth';
import { getGetOrdersQueryOptions } from '@/shared/api';

export const ordersLoader = (queryClient: QueryClient) => async () => {
  const ordersQuery = getGetOrdersQueryOptions();

  return (
    queryClient.getQueryData(ordersQuery.queryKey) ??
    (await queryClient.fetchQuery(ordersQuery))
  );
};

export const OrdersRoute = () => {
  const user = useAuth().state.user;

  if (!user) {
    return null;
  }

  return (
    <ContentLayout title="Your Orders">
      <div className="mt-8">
        <ErrorBoundary
          fallback={<div>Failed to load orders. Try to refresh the page.</div>}
        >
          <Orders clientEmail={user.email} />
        </ErrorBoundary>
      </div>
    </ContentLayout>
  );
};
