import {QueryClient} from '@tanstack/react-query';
import {LoaderFunctionArgs} from 'react-router';

import {ContentLayout} from '@/shared/components/layouts';
import {getCartQueryOptions} from '@/features/cart/api/get-cart';
import {Cart} from '@/features/cart/components/cart';
import {ErrorBoundary} from 'react-error-boundary';

export const cartLoader =
  (queryClient: QueryClient) =>
    async (_args: LoaderFunctionArgs) => {
      const cartQuery = getCartQueryOptions();
      return (
        queryClient.getQueryData(cartQuery.queryKey) ??
        (await queryClient.fetchQuery(cartQuery))
      );
    };

export const CartRoute = () => {
  return (
    <ContentLayout title="Your Cart">
      <div className="mt-8">
        <ErrorBoundary
          fallback={
            <div>Failed to load cart. Try to refresh the page.</div>
          }
        >
          <Cart/>
        </ErrorBoundary>
      </div>
    </ContentLayout>
  );
};
