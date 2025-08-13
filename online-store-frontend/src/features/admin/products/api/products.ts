import {api} from '@/shared/lib/api-client';

export type CreateProductRequest = {
  name: string;
  description?: string;
  price: number;
  stock: number;
  categoryId: number;
  imageUrls?: string[];
};

export const createProduct = (data: CreateProductRequest) => api.post('/admin/products', data);


