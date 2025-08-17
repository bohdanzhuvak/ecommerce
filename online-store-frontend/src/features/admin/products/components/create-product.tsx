import React from 'react';
import {useForm} from 'react-hook-form';
import {Button} from '@/shared/components/ui/button';
import {createProduct} from '@/features/admin/products/api/products';
import {ProductForm} from '../api.types';

export const AdminProductCreate: React.FC = () => {
  const {register, handleSubmit, reset} = useForm<ProductForm>();
  const [isLoading, setLoading] = React.useState(false);
  const onSubmit = async (values: ProductForm) => {
    setLoading(true);
    try {
      const payload = {
        ...values,
        imageUrls: values.imageUrls ? values.imageUrls.split(',').map(s => s.trim()) : [],
      };
      await createProduct(payload);
      reset();
    } finally {
      setLoading(false);
    }
  };
  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-3">
      <input className="border p-2 w-full" placeholder="Name" {...register('name', {required: true})}/>
      <textarea className="border p-2 w-full" placeholder="Description" {...register('description')}/>
      <input className="border p-2 w-full" type="number" step="0.01" placeholder="Price" {...register('price', {valueAsNumber: true})}/>
      <input className="border p-2 w-full" type="number" placeholder="Stock" {...register('stock', {valueAsNumber: true})}/>
      <input className="border p-2 w-full" type="number" placeholder="Category ID" {...register('categoryId', {valueAsNumber: true})}/>
      <input className="border p-2 w-full" placeholder="Image URLs (comma separated)" {...register('imageUrls')}/>
      <Button type="submit" isLoading={isLoading}>Create Product</Button>
    </form>
  );
};


