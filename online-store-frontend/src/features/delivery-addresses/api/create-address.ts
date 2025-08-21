import { api } from '@/shared/lib/api-client';
import { CreateDeliveryAddressRequest, DeliveryAddress } from '@/shared/types';

export const createDeliveryAddress = (request: CreateDeliveryAddressRequest): Promise<DeliveryAddress> => {
  return api.post('/users/delivery-addresses', request);
};
