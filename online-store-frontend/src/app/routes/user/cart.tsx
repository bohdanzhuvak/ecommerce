import { QueryClient } from '@tanstack/react-query';
import { LoaderFunctionArgs } from 'react-router';

import { ContentLayout } from '@/shared/components/layouts';
import { Cart } from '@/features/cart/components/cart';
import { ErrorBoundary } from 'react-error-boundary';
import { getGetCartQueryOptions } from '@/shared/api';

export const cartLoader =
  (queryClient: QueryClient) => async (_args: LoaderFunctionArgs) => {
    const cartQuery = getGetCartQueryOptions();
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
          fallback={<div>Failed to load cart. Try to refresh the page.</div>}
        >
          <Cart />
        </ErrorBoundary>
      </div>
    </ContentLayout>
  );
};
