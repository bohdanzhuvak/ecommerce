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
  quantity: number;
  pricePerUnit: number;
}

export interface DeliveryAddress {
  street: string;
  city: string;
  postalCode: string;
  country: string;
  phone: string;
  isDefault: boolean;
  createdAt: string;
}

export interface User {
  id: string | number;
  username: string;
  email: string;
  firstName?: string;
  lastName?: string;
  createdAt: string;
  updatedAt: string;
}

export interface Product {
  id: string | number;
  name: string;
  description?: string;
  price: number;
  categoryId: string | number;
  stock: number;
  createdAt: string;
  updatedAt: string;
}

export interface Category {
  id: string | number;
  name: string;
  description?: string;
  createdAt: string;
  updatedAt: string;
}
