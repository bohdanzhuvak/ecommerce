import {api} from '@/shared/lib/api-client';
import {queryOptions} from '@tanstack/react-query';
import {AdminOrder} from '../api.types';
import {Page} from '@/shared/types/api';

export const getAdminOrders = (page = 0, size = 20): Promise<Page<AdminOrder>> => api.get('/admin/orders', {params: {page, size}});
export const getAdminOrdersQueryOptions = (page = 0, size = 20) => queryOptions({ queryKey: ['admin','orders',page,size], queryFn: ()=> getAdminOrders(page,size) });


