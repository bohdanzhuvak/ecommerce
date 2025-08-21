import {DeliveryAddress} from "@/features/delivery-addresses/api.types.ts";

export interface OrderItem {
  productId: number;
  productName: string;
  quantity: number;
  pricePerUnit: number;
}

export interface Order {
  id: number;
  createdAt: string;
  totalPrice: number;
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

export type OrderStatus = typeof ORDER_STATUSES[keyof typeof ORDER_STATUSES];

