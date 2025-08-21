import { api } from '@/shared/lib/api-client';
import { DepositRequest } from '@/shared/types';

export const depositToBalance = (request: DepositRequest): Promise<void> => {
  return api.post('/users/balance/deposit', request);
};
