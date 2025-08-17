import {api} from '@/shared/lib/api-client';
import {CreateCategoryRequest} from '../api.types';

export const createCategory = (data: CreateCategoryRequest) => api.post('/admin/categories', data);


