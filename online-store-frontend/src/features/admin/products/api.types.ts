export interface CreateProductRequest {
  name: string;
  description?: string;
  price: number;
  stock: number;
  categoryId: number;
  imageUrls?: string[];
}

export interface ProductForm {
  name: string;
  description?: string;
  price: number;
  stock: number;
  categoryId: number;
  imageUrls?: string;
}
