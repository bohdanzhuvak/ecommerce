import {QueryClient} from '@tanstack/react-query';
import {LoaderFunctionArgs, useParams} from 'react-router';

import {ContentLayout} from '@/shared/components/layouts';
import {getCartQueryOptions} from '@/features/cart/api/get-cart';
import {Cart} from '@/features/cart/components/cart';
import {ErrorBoundary} from 'react-error-boundary';

export const cartLoader =
  (queryClient: QueryClient) =>
    async ({params}: LoaderFunctionArgs) => {
      const clientEmail = params.clientEmail as string;

      if (!clientEmail) {
        return null;
      }

      const cartQuery = getCartQueryOptions(clientEmail);

      return (
        queryClient.getQueryData(cartQuery.queryKey) ??
        (await queryClient.fetchQuery(cartQuery))
      );
    };

export const CartRoute = () => {
  const params = useParams();
  const clientEmail = params.clientEmail as string;

  if (!clientEmail) {
    return null;
  }

  return (
    <ContentLayout title="Your Cart">
      <div className="mt-8">
        <ErrorBoundary
          fallback={
            <div>Failed to load cart. Try to refresh the page.</div>
          }
        >
          <Cart clientEmail={clientEmail}/>
        </ErrorBoundary>
      </div>
    </ContentLayout>
  );
};
