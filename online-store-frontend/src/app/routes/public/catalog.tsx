import { QueryClient } from '@tanstack/react-query';
import { ContentLayout } from '@/shared/components/layouts';
import { Catalog } from '@/features/products';
import { getGetProductsForCustomerQueryOptions } from '@/shared/api';

export const catalogLoader = (queryClient: QueryClient) => async () => {
  const q = getGetProductsForCustomerQueryOptions({
    offset: 0,
    limit: 12,
  });
  return (
    queryClient.getQueryData(q.queryKey) ?? (await queryClient.fetchQuery(q))
  );
};

export const CatalogRoute = () => {
  return (
    <ContentLayout title="Catalog">
      <div className="mt-8">
        <Catalog />
      </div>
    </ContentLayout>
  );
};
