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
}
