import React from 'react';
import { useGetTransactions } from '@/shared/api/generated/balance-customer/balance-customer';
import { Spinner } from '@/shared/components/ui/spinner';

const getTransactionIcon = (type: string) => {
  switch (type) {
    case 'DEPOSIT':
      return '💰';
    case 'PURCHASE':
      return '🛒';
    case 'REFUND':
      return '↩️';
    case 'WITHDRAWAL':
      return '💸';
    default:
      return '⚙️';
  }
};

const getTransactionColor = (type: string) => {
  switch (type) {
    case 'DEPOSIT':
    case 'REFUND':
      return 'text-green-600';
    case 'PURCHASE':
    case 'WITHDRAWAL':
      return 'text-red-600';
    default:
      return 'text-gray-600';
  }
};

export const BalanceHistory: React.FC = () => {
  const { data: transactions, isLoading } = useGetTransactions({
    offset: 0,
    limit: 20,
  });

  if (isLoading) {
    return <Spinner />;
  }

  if (!transactions) {
    return (
      <div className="text-gray-500">Failed to load transaction history</div>
    );
  }

  return (
    <div className="space-y-4">
      <h3 className="text-lg font-semibold">Transaction History</h3>

      {transactions.length === 0 ? (
        <p className="text-gray-500 text-center py-4">No transactions yet</p>
      ) : (
        <div className="space-y-3">
          {transactions.map((transaction) => (
            <div
              key={transaction.id}
              className="flex items-center justify-between p-3 bg-gray-50 rounded-lg"
            >
              <div className="flex items-center gap-3">
                <span className="text-xl">
                  {getTransactionIcon(transaction.type ?? '')}
                </span>
                <div>
                  <p className="font-medium">{transaction.description}</p>
                  <p className="text-sm text-gray-500">
                    {transaction.createdAt
                      ? new Date(transaction.createdAt).toLocaleDateString()
                      : 'N/A'}
                  </p>
                </div>
              </div>

              <div className="text-right">
                <p
                  className={`font-semibold ${getTransactionColor(transaction.type ?? '')}`}
                >
                  {transaction.type === 'DEPOSIT' ||
                  transaction.type === 'REFUND'
                    ? '+'
                    : ''}
                  ${transaction.amount?.amount?.toFixed(2) ?? '0.00'}
                </p>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};
