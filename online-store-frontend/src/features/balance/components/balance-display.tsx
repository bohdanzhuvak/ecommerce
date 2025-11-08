import React from 'react';
import { Spinner } from '@/shared/components/ui/spinner';
import { useGetBalance } from '@/shared/api';

export const BalanceDisplay: React.FC = () => {
  const balanceQuery = useGetBalance();

  if (balanceQuery.isLoading) {
    return <Spinner size="sm" />;
  }

  const balance = balanceQuery.data;

  if (balance == null || !balance.currentBalance) {
    return <span className="text-gray-500">Balance: --</span>;
  }

  return (
    <div className="flex items-center gap-2">
      <span className="text-sm font-medium">Balance:</span>
      <span className="text-sm font-bold text-green-600">
        ${balance.currentBalance.amount?.toFixed(2) ?? '0.00'}
      </span>
    </div>
  );
};
