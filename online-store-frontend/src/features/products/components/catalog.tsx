import React from 'react';
import {useProducts} from '@/features/products/api/get-products.ts';
import {ImageCard, ImageGalleryContainer} from '@/shared/components/ui/image-gallery/gallery';
import {Button} from '@/shared/components/ui/button/button';
import {Link} from '@/shared/components/ui/link';
import {paths} from '@/config/paths';
import {Spinner} from '@/shared/components/ui/spinner';
import {AddToCartButton} from '@/features/cart/components/add-to-cart-button';

export const Catalog: React.FC = () => {
  const {data, isLoading} = useProducts({page: 0, size: 12, sort: "asc"});

  if (isLoading) {
    return (
      <div className="flex h-48 w-full items-center justify-center">
        <Spinner size="lg"/>
      </div>
    );
  }

  const products = data?.content ?? [];

  return (
    <div>
      <div className="mb-4 flex items-center justify-between">
        <h2 className="text-2xl font-bold">Products</h2>
      </div>
      <ImageGalleryContainer>
        {products.map((p) => (
          <ImageCard key={p.id}>
            <div className="aspect-video w-full overflow-hidden rounded-md bg-gray-700">
              {p.imageUrls?.[0] ? (
                <img src={p.imageUrls[0]} alt={p.name} className="h-full w-full object-cover"/>
              ) : (
                <div className="flex h-full w-full items-center justify-center text-gray-400">No image</div>
              )}
            </div>
            <div className="mt-3 flex items-start justify-between gap-3">
              <div>
                <Link to={paths.app.product.getHref(p.id)} className="text-white font-semibold">
                  {p.name}
                </Link>
                <div className="text-sm text-gray-400">{p.categoryName}</div>
              </div>
              <div className="text-right">
                <div className="text-blue-400 font-bold">${p.price.toFixed(2)}</div>
                <div className="text-xs text-gray-400">In stock: {p.stock}</div>
              </div>
            </div>
            <div className="mt-4 flex justify-between">
              <Link to={paths.app.product.getHref(p.id)}>
                <Button size="sm" variant="outline">Details</Button>
              </Link>
              <AddToCartButton productId={p.id} />
            </div>
          </ImageCard>
        ))}
      </ImageGalleryContainer>
    </div>
  );
};


