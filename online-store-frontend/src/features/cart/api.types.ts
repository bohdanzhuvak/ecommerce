export interface CartItem {
  productId: number;
  productName: string;
  quantity: number;
  price: number;
}

export interface Cart {
  items: CartItem[];
  totalPrice: number;
}

export interface AddToCartRequest {
  productId: number;
  quantity: number;
}
