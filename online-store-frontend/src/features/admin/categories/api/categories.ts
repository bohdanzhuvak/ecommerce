import {api} from '@/shared/lib/api-client';

export type CreateCategoryRequest = { name: string };
export const createCategory = (data: CreateCategoryRequest) => api.post('/admin/categories', data);


