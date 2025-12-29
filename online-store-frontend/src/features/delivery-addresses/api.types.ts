import { z } from 'zod';

export interface DeliveryAddress {
  id: string;
  street: string;
  city: string;
  postalCode: string;
  country: string;
  isDefault: boolean;
}

export interface CreateDeliveryAddressRequest {
  street: string;
  city: string;
  state: string;
  postalCode: string;
  country: string;
  recipientName: string;
  isDefault: boolean;
}

export interface UpdateDeliveryAddressRequest {
  id: number;
  street: string;
  city: string;
  postalCode: string;
  country: string;
  isDefault?: boolean;
}

export interface DeleteDeliveryAddressRequest {
  id: string;
}

export const createAddressFormSchema = z.object({
  street: z.string().min(1, 'Street address is required'),
  city: z.string().min(1, 'City is required'),
  state: z.string().min(1, 'State is required'),
  postalCode: z.string().min(1, 'Postal code is required'),
  country: z.string().min(1, 'Country is required'),
  recipientName: z.string().min(1, 'Recipient name is required'),
  isDefault: z.boolean().default(false),
});

export const updateAddressFormSchema = z.object({
  id: z.number(),
  street: z.string().min(1, 'Street address is required'),
  city: z.string().min(1, 'City is required'),
  postalCode: z.string().min(1, 'Postal code is required'),
  country: z.string().min(1, 'Country is required'),
  isDefault: z.boolean().default(false),
});

export type AddressFormData = z.infer<typeof createAddressFormSchema>;
export type UpdateAddressFormData = z.infer<typeof updateAddressFormSchema>;
