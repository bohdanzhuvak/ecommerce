import React from 'react';
import {useProduct} from '@/features/products/api/get-product';
import {useParams} from 'react-router';
import {Spinner} from '@/shared/components/ui/spinner';
import {AddToCartButton} from '@/features/cart/components/add-to-cart-button';

export const ProductDetails: React.FC = () => {
  const params = useParams();
  const id = params.id as string;
  const {data, isLoading} = useProduct(id);

  if (isLoading) {
    return (
      <div className="flex h-48 w-full items-center justify-center">
        <Spinner size="lg"/>
      </div>
    );
  }

  if (!data) return null;

  return (
    <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
      <div className="rounded-lg bg-gray-800 p-4">
        <div className="aspect-video w-full overflow-hidden rounded-md bg-gray-700">
          {data.imageUrls?.[0] ? (
            <img src={data.imageUrls[0]} alt={data.name} className="h-full w-full object-cover"/>
          ) : (
            <div className="flex h-full w-full items-center justify-center text-gray-400">No image</div>
          )}
        </div>
        {data.imageUrls?.length > 1 && (
          <div className="mt-2 grid grid-cols-5 gap-2">
            {data.imageUrls.slice(1, 6).map((url, idx) => (
              <img key={idx} src={url} className="h-16 w-full object-cover rounded"/>
            ))}
          </div>
        )}
      </div>
      <div>
        <h1 className="text-3xl font-bold mb-2">{data.name}</h1>
        <div className="text-gray-400 mb-4">{data.categoryName}</div>
        <div className="text-2xl font-semibold text-blue-500 mb-4">${data.price.toFixed(2)}</div>
        <p className="text-gray-200 whitespace-pre-wrap mb-6">{data.description}</p>
        <div className="flex items-center gap-4">
          <AddToCartButton productId={data.id} />
          <span className="text-sm text-gray-400">In stock: {data.stock}</span>
        </div>
      </div>
    </div>
  );
};


