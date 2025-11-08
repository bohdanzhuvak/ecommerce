import React from 'react';
import {
  ImageCard,
  ImageGalleryContainer,
} from '@/shared/components/ui/image-gallery/gallery';
import { Button } from '@/shared/components/ui/button/button';
import { Link } from '@/shared/components/ui/link';
import { paths } from '@/config/paths';
import { Spinner } from '@/shared/components/ui/spinner';
import { AddToCartButton } from '@/features/cart/components/add-to-cart-button';
import { useGetProductsForCustomer } from '@/shared/api';

export const Catalog: React.FC = () => {
  const { data, isLoading } = useGetProductsForCustomer({
    offset: 0,
    limit: 12,
  });

  if (isLoading) {
    return (
      <div className="flex h-48 w-full items-center justify-center">
        <Spinner size="lg" />
      </div>
    );
  }

  const products = data ?? [];

  return (
    <div>
      <div className="mb-4 flex items-center justify-between">
        <h2 className="text-2xl font-bold">Products</h2>
      </div>
      <ImageGalleryContainer>
        {products.map((p) => (
          <ImageCard key={p.id}>
            <div className="aspect-video w-full overflow-hidden rounded-md bg-gray-700">
              {p.images?.[0] ? (
                <img
                  src={p.images[0]}
                  alt={p.name}
                  className="h-full w-full object-cover"
                />
              ) : (
                <div className="flex h-full w-full items-center justify-center text-gray-400">
                  No image
                </div>
              )}
            </div>
            <div className="mt-3 flex items-start justify-between gap-3">
              <div>
                <Link
                  to={paths.product.getHref(p.id!)}
                  className="text-white font-semibold"
                >
                  {p.name}
                </Link>
                {p.categoryId && (
                  <div className="text-sm text-gray-400">
                    Category: {p.categoryId}
                  </div>
                )}
              </div>
              <div className="text-right">
                <div className="text-blue-400 font-bold">
                  {p.price?.amount?.toFixed(2)} {p.price?.currency}
                </div>
                <div className="text-xs text-gray-400">In stock: {p.stock}</div>
              </div>
            </div>
            <div className="mt-4 flex justify-between">
              <Link to={paths.product.getHref(p.id!)}>
                <Button size="sm" variant="outline">
                  Details
                </Button>
              </Link>
              <AddToCartButton productId={p.id!} />
            </div>
          </ImageCard>
        ))}
      </ImageGalleryContainer>
    </div>
  );
};
