import React from 'react';
import {useBalance} from '@/shared/hooks';
import {Spinner} from '@/shared/components/ui/spinner';

export const BalanceDisplay: React.FC = () => {
  const balanceQuery = useBalance();

  if (balanceQuery.isLoading) {
    return <Spinner size="sm" />;
  }

  const balance = balanceQuery.data;

  if (balance == null) {
    return <span className="text-gray-500">Balance: --</span>;
  }

  return (
    <div className="flex items-center gap-2">
      <span className="text-sm font-medium">Balance:</span>
      <span className="text-sm font-bold text-green-600">
        ${balance.currentBalance.toFixed(2)}
      </span>
    </div>
  );
};
