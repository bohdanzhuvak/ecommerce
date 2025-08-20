import {QueryClient} from '@tanstack/react-query';
import {ContentLayout} from '@/shared/components/layouts';
import {CreateProduct} from '@/features/admin/products/components/create-product';
import {ProductsList} from "@/features/admin/products/components/products-list.tsx";

export const adminProductsLoader = (_queryClient: QueryClient) => async () => null;

export const AdminProductsRoute = () => {
  return (
    <ContentLayout title="Admin Products">
      <CreateProduct/>
      <ProductsList/>
    </ContentLayout>
  );
};
