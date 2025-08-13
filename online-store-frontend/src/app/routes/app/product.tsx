import {QueryClient} from '@tanstack/react-query';
import {ContentLayout} from '@/shared/components/layouts';
import {ProductDetails} from '@/features/products/components';
import {getProductQueryOptions} from '@/features/products/api/get-product';
import {LoaderFunctionArgs} from 'react-router';

export const productLoader = (queryClient: QueryClient) => async ({params}: LoaderFunctionArgs) => {
  const id = params.id as string;
  const q = getProductQueryOptions(id);
  return queryClient.getQueryData(q.queryKey) ?? (await queryClient.fetchQuery(q));
};

export const ProductDetailsRoute = () => {
  return (
    <ContentLayout title="Product">
      <div className="mt-8">
        <ProductDetails/>
      </div>
    </ContentLayout>
  );
};


