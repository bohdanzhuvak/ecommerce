import { useQuery } from '@tanstack/react-query';
import { getBalance } from '../api/get-balance';

export const useBalance = () => {
  return useQuery({
    queryKey: ['balance'],
    queryFn: getBalance,
    staleTime: 30000, // 30 seconds
    refetchOnWindowFocus: true,
  });
};
