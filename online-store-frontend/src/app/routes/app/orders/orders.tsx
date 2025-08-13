import {QueryClient} from '@tanstack/react-query';

import {ContentLayout} from '@/shared/components/layouts';
import {getOrdersQueryOptions} from '@/features/orders/api/get-orders';
import {useUser} from '@/shared/lib/auth/auth';
import {Orders} from '@/features/orders/components/orders';
import {ErrorBoundary} from 'react-error-boundary';
//

export const ordersLoader =
  (queryClient: QueryClient) =>
    async () => {
      const ordersQuery = getOrdersQueryOptions();

      return (
        queryClient.getQueryData(ordersQuery.queryKey) ??
        (await queryClient.fetchQuery(ordersQuery))
      );
    };

export const OrdersRoute = () => {
  const user = useUser().data;

  if (!user) {
    return null;
  }

  return (
    <ContentLayout title="Your Orders">
      <div className="mt-8">
        <ErrorBoundary
          fallback={
            <div>Failed to load orders. Try to refresh the page.</div>
          }
        >
          <Orders clientEmail={user.email}/>
        </ErrorBoundary>
      </div>
    </ContentLayout>
  );
};
