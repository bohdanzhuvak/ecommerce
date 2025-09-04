import React from 'react';
import {useBalance} from '@/shared/hooks';
import {BalanceTransaction, TRANSACTION_TYPES} from '@/shared/types';
import {Spinner} from '@/shared/components/ui/spinner';

const getTransactionIcon = (type: BalanceTransaction['type']) => {
  switch (type) {
    case TRANSACTION_TYPES.DEPOSIT:
      return '💰';
    case TRANSACTION_TYPES.PURCHASE:
      return '🛒';
    case TRANSACTION_TYPES.REFUND:
      return '↩️';
    case TRANSACTION_TYPES.WITHDRAW:
      return '💸';
    default:
      return '⚙️';
  }
};

const getTransactionColor = (type: BalanceTransaction['type']) => {
  switch (type) {
    case TRANSACTION_TYPES.DEPOSIT:
    case TRANSACTION_TYPES.REFUND:
      return 'text-green-600';
    case TRANSACTION_TYPES.PURCHASE:
    case TRANSACTION_TYPES.WITHDRAW:
      return 'text-red-600';
    default:
      return 'text-gray-600';
  }
};

export const BalanceHistory: React.FC = () => {
  const balanceQuery = useBalance();

  if (balanceQuery.isLoading) {
    return <Spinner />;
  }
  const balance = balanceQuery.data;

  if (balance == null) {
    return <div className="text-gray-500">Failed to load balance history</div>;
  }

  return (
    <div className="space-y-4">
      <h3 className="text-lg font-semibold">Transaction History</h3>

      {balance.recentTransactions.length === 0 ? (
        <p className="text-gray-500 text-center py-4">No transactions yet</p>
      ) : (
        <div className="space-y-3">
          {balance.recentTransactions.map((transaction) => (
            <div
              key={transaction.id}
              className="flex items-center justify-between p-3 bg-gray-50 rounded-lg"
            >
              <div className="flex items-center gap-3">
                <span className="text-xl">{getTransactionIcon(transaction.type)}</span>
                <div>
                  <p className="font-medium">{transaction.description}</p>
                  <p className="text-sm text-gray-500">
                    {new Date(transaction.createdAt).toLocaleDateString()}
                  </p>
                  {transaction.orderId && (
                    <p className="text-xs text-blue-600">Order #{transaction.orderId}</p>
                  )}
                </div>
              </div>

              <div className="text-right">
                <p className={`font-semibold ${getTransactionColor(transaction.type)}`}>
                  {transaction.type === TRANSACTION_TYPES.DEPOSIT ||
                   transaction.type === TRANSACTION_TYPES.REFUND ? '+' : ''}
                  ${transaction.amount.toFixed(2)}
                </p>
                <p className="text-sm text-gray-500">
                  Balance: ${transaction.balanceAfter.toFixed(2)}
                </p>
              </div>
            </div>
          ))}
        </div>
      )}
    </div>
  );
};
