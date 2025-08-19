import {QueryClient} from '@tanstack/react-query';
import {ContentLayout} from '@/shared/components/layouts';
import {Catalog} from '@/features/products/components';
import {getProductsQueryOptions} from '@/features/products/api/get-products.ts';

export const catalogLoader = (queryClient: QueryClient) => async () => {
  const q = getProductsQueryOptions(0, 12);
  return queryClient.getQueryData(q.queryKey) ?? (await queryClient.fetchQuery(q));
};

export const CatalogRoute = () => {
  return (
    <ContentLayout title="Catalog">
      <div className="mt-8">
        <Catalog/>
      </div>
    </ContentLayout>
  );
};


