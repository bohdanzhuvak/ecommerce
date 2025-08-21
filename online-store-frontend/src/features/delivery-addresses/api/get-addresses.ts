import { api } from '@/shared/lib/api-client';
import { DeliveryAddress } from '@/shared/types';

export const getDeliveryAddresses = (): Promise<DeliveryAddress[]> => {
  return api.get('/users/delivery-addresses');
};
