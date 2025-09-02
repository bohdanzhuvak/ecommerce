import React, { useState } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { depositToBalance } from '../api/deposit';
import { Button } from '@/shared/components/ui/button';
import { useDisclosure } from '@/shared/hooks/use-disclosure';
import { Drawer, DrawerContent, DrawerHeader, DrawerTitle, DrawerTrigger } from '@/shared/components/ui/drawer';
import { DepositRequest } from '@/shared/types';

interface DepositFormProps {
  defaultAmount?: string;
}

export const DepositForm: React.FC<DepositFormProps> = ({defaultAmount}) => {
  const { isOpen, open, close } = useDisclosure();
  const [amount, setAmount] = useState(defaultAmount || '');
  const [description, setDescription] = useState('');
  const queryClient = useQueryClient();

  const depositMutation = useMutation({
    mutationFn: depositToBalance,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['balance'] });
      close();
      setAmount('');
      setDescription('');
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    const numAmount = parseFloat(amount);
    if (isNaN(numAmount) || numAmount <= 0) return;

    depositMutation.mutate({
      amount: numAmount,
      description: description || undefined,
    });
  };

  return (
    <>
      <Button onClick={open} variant="outline" size="sm">
        Deposit
      </Button>

      <Drawer open={isOpen} onOpenChange={(isOpen) => isOpen ? open() : close()}>
        <DrawerContent>
          <DrawerHeader>
            <DrawerTitle>Deposit to Balance</DrawerTitle>
          </DrawerHeader>

          <form onSubmit={handleSubmit} className="p-6 space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Amount ($)
              </label>
              <input
                type="number"
                step="0.01"
                min="0.01"
                value={amount}
                onChange={(e) => setAmount(e.target.value)}
                placeholder="0.00"
                required
                className="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Description (optional)
              </label>
              <input
                type="text"
                value={description}
                onChange={(e) => setDescription(e.target.value)}
                placeholder="e.g., Bank transfer"
                className="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring"
              />
            </div>

            <Button
              type="submit"
              disabled={depositMutation.isPending || !amount || parseFloat(amount) <= 0}
              className="w-full"
            >
              {depositMutation.isPending ? 'Processing...' : 'Deposit'}
            </Button>
          </form>
        </DrawerContent>
      </Drawer>
    </>
  );
};
