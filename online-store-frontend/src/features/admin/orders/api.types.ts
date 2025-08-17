export interface AdminOrderItem {
  productId: number;
  productName: string;
  quantity: number;
  pricePerUnit: number;
}

export interface AdminOrder {
  id: number;
  userId: number;
  createdAt: string;
  totalPrice: number;
  status: string;
  items: AdminOrderItem[];
}
