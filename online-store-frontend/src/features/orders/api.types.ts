import { MoneyResponse } from '@/shared/api';

export interface OrderItem {
  productId: string;
  productName: string;
  quantity: number;
  productPrice: MoneyResponse;
  totalPrice: MoneyResponse;
}

export interface DeliveryAddress {
  id: string;
  street: string;
  city: string;
  postalCode: string;
  country: string;
}

export interface Order {
  id: string;
  createdAt: string;
  totalPrice: MoneyResponse;
  status: string;
  items: OrderItem[];
  deliveryAddress: DeliveryAddress;
}

export const ORDER_STATUSES = {
  PENDING: 'PENDING',
  PAID: 'PAID',
  SHIPPED: 'SHIPPED',
  DELIVERED: 'DELIVERED',
  CANCELLED: 'CANCELLED',
} as const;

export type OrderStatus = (typeof ORDER_STATUSES)[keyof typeof ORDER_STATUSES];
