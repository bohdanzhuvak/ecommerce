import React from 'react';
import { useQuery } from '@tanstack/react-query';
import { getBalance } from '../api/get-balance';
import { Spinner } from '@/shared/components/ui/spinner';

export const BalanceDisplay: React.FC = () => {
  const { data: balanceData, isLoading, error } = useQuery({
    queryKey: ['balance'],
    queryFn: getBalance,
  });

  if (isLoading) {
    return <Spinner size="sm" />;
  }

  if (error || !balanceData) {
    return <span className="text-gray-500">Balance: --</span>;
  }

  return (
    <div className="flex items-center gap-2">
      <span className="text-sm font-medium">Balance:</span>
      <span className="text-sm font-bold text-green-600">
        ${balanceData.currentBalance.toFixed(2)}
      </span>
    </div>
  );
};
