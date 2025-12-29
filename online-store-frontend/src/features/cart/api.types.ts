import type { MoneyResponse } from '@/shared/api';

export interface CartItem {
  productId: string;
  productName: string;
  quantity: number;
  productPrice: MoneyResponse;
  totalPrice: MoneyResponse;
}

export interface Cart {
  items: CartItem[];
  totalPrice: number;
}

export interface AddToCartRequest {
  productId: number;
  quantity: number;
}
