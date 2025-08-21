export const TRANSACTION_TYPES = {
  DEPOSIT: 'DEPOSIT',
  WITHDRAW: 'WITHDRAW',
  PURCHASE: 'PURCHASE',
  REFUND: 'REFUND',
  ADMIN_ADJUSTMENT: 'ADMIN_ADJUSTMENT',
} as const;

export type TransactionType = typeof TRANSACTION_TYPES[keyof typeof TRANSACTION_TYPES];

export interface BalanceTransaction {
  id: number;
  type: TransactionType;
  amount: number;
  balanceAfter: number;
  description: string;
  orderId?: number;
  createdAt: string;
}

export interface BalanceResponse {
  currentBalance: number;
  recentTransactions: BalanceTransaction[];
}
