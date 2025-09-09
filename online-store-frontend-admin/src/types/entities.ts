export interface Order {
  id: string | number;
  userId: string | number;
  createdAt: string;
  totalPrice: number;
  status: string;
  deliveryAddress?: DeliveryAddress;
  items: OrderItem[];
}

export interface OrderItem {
  productId: string | number;
  productName: string;
  quantity: number;
  pricePerUnit: number;
}

export interface DeliveryAddress {
  street: string;
  city: string;
  postalCode: string;
  country: string;
  phone: string;
  createdAt: string;
}
