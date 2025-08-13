import {QueryClient} from '@tanstack/react-query';
import {ContentLayout} from '@/shared/components/layouts';
import {AdminProductCreate} from '@/features/admin/products/components/create-product';

export const adminProductsLoader = (_queryClient: QueryClient) => async () => null;

export const AdminProductsRoute = () => {
  return (
    <ContentLayout title="Admin Products">
      <AdminProductCreate/>
    </ContentLayout>
  );
};


