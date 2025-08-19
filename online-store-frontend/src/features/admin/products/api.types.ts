import {z} from 'zod';

export const createProductFormSchema = z.object({
  name: z.string().min(1, 'Name is required'),
  description: z.string().optional(),
  price: z.number().min(0.01, 'Price must be greater than 0'),
  stock: z.number().int().min(0, 'Stock must be a non-negative integer'),
  categoryId: z.number().int().min(1, 'Category ID is required'),
  imageUrls: z.string().optional(),
});

export const createProductInputSchema = z.object({
  name: z.string().min(1, 'Name is required'),
  description: z.string().optional(),
  price: z.number().min(0.01, 'Price must be greater than 0'),
  stock: z.number().int().min(0, 'Stock must be a non-negative integer'),
  categoryId: z.number().int().min(1, 'Category ID is required'),
  imageUrls: z.array(z.string()).optional(),
});

export type ProductForm = z.infer<typeof createProductFormSchema>;
export type CreateProductRequest = z.infer<typeof createProductInputSchema>;
