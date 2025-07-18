import {QueryClient} from '@tanstack/react-query';
import {LoaderFunctionArgs} from 'react-router-dom';

import {ContentLayout} from '@/shared/components/layouts';
import {getOrdersQueryOptions} from '@/features/orders/api/get-orders';
import {useUser} from '@/shared/lib/auth/auth';
import {Orders} from '@/features/orders/components/orders';
import {ErrorBoundary} from 'react-error-boundary';
import {UnconfirmedOrders} from '@/features/orders/components';
import {Authorization, ROLES} from '@/shared/lib/auth/authorization';

export const ordersLoader =
  (queryClient: QueryClient) =>
    async ({params}: LoaderFunctionArgs) => {
      const clientEmail = params.clientEmail as string;

      if (!clientEmail) {
        return null;
      }

      const ordersQuery = getOrdersQueryOptions(clientEmail);

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
      <Authorization allowedRoles={[ROLES.EMPLOYEE]}>
        <div className="mt-12">
          <h2 className="text-xl font-semibold mb-4">Unconfirmed Orders</h2>
          <ErrorBoundary fallback={<div>Failed to load unconfirmed orders.</div>}>
            <UnconfirmedOrders/>
          </ErrorBoundary>
        </div>
      </Authorization>
    </ContentLayout>
  );
};
