import {api} from '@/shared/lib/api-client';
import {CreateProductRequest} from '../api.types';

export const createProduct = (data: CreateProductRequest) => api.post('/admin/products', data);


