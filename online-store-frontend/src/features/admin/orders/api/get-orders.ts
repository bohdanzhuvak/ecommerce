import {api} from '@/shared/lib/api-client';
import {queryOptions} from '@tanstack/react-query';

export type AdminOrderItemDTO = { productId: number; productName: string; quantity: number; pricePerUnit: number };
export type AdminOrderDTO = { id: number; userId: number; createdAt: string; totalPrice: number; status: string; items: AdminOrderItemDTO[] };
export type Page<T> = { content: T[]; totalElements: number; totalPages: number; size: number; number: number };

export const getAdminOrders = (page = 0, size = 20): Promise<Page<AdminOrderDTO>> => api.get('/admin/orders', {params: {page, size}});
export const getAdminOrdersQueryOptions = (page = 0, size = 20) => queryOptions({ queryKey: ['admin','orders',page,size], queryFn: ()=> getAdminOrders(page,size) });


