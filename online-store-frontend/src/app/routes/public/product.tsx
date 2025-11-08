import { QueryClient } from '@tanstack/react-query';
import { ContentLayout } from '@/shared/components/layouts';
import { ProductDetails } from '@/features/products';
import { LoaderFunctionArgs } from 'react-router';
import { getGetProductForCustomerQueryOptions } from '@/shared/api';

export const productLoader =
  (queryClient: QueryClient) =>
  async ({ params }: LoaderFunctionArgs) => {
    const id = params.id as string;
    const q = getGetProductForCustomerQueryOptions(id);
    return (
      queryClient.getQueryData(q.queryKey) ?? (await queryClient.fetchQuery(q))
    );
  };

export const ProductDetailsRoute = () => {
  return (
    <ContentLayout title="Product">
      <div className="mt-8">
        <ProductDetails />
      </div>
    </ContentLayout>
  );
};
