import {QueryClient} from '@tanstack/react-query';
import {ContentLayout} from '@/shared/components/layouts';
import {AdminOrdersList} from '@/features/admin/orders/components';
import {getAdminOrdersQueryOptions} from '@/features/admin/orders/api/get-admin-orders.ts';

export const adminOrdersLoader = (queryClient: QueryClient) => async () => {
  const q = getAdminOrdersQueryOptions();
  return queryClient.getQueryData(q.queryKey) ?? (await queryClient.fetchQuery(q));
};

export const AdminOrdersRoute = () => {
  return (
    <ContentLayout title="Admin Orders">
      <div className="mt-8">
        <AdminOrdersList/>
      </div>
    </ContentLayout>
  );
};


