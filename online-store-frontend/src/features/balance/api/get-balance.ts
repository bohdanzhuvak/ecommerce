import { api } from '@/shared/lib/api-client';
import { BalanceResponse } from '@/shared/types';

export const getBalance = (): Promise<BalanceResponse> => {
  return api.get('/users/balance');
};
