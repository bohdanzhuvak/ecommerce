import React, { useState } from 'react';
import { useQueryClient } from '@tanstack/react-query';
import { useDisclosure } from '@/shared/hooks/use-disclosure';
import {
  Drawer,
  DrawerContent,
  DrawerHeader,
  DrawerTitle,
} from '@/shared/components/ui/drawer';
import { Button } from '@/shared/components/ui/button';
import { useCreateOrderFromCart } from '../api/create-order-from-cart';
import { useNotifications } from '@/shared/components/ui/notifications';
import { Spinner } from '@/shared/components/ui/spinner';
import { DeliveryAddress } from '@/features/delivery-addresses/api.types.ts';
import {
  useGetBalance,
  useGetCart,
  useGetDeliveryAddresses,
} from '@/shared/api';

interface CreateOrderFormProps {
  className?: string;
  disabled?: boolean;
}

export const CreateOrderForm: React.FC<CreateOrderFormProps> = ({
  className,
  disabled = false,
}) => {
  const { isOpen, open, close } = useDisclosure();
  const [selectedAddressId, setSelectedAddressId] = useState<string | null>(
    null,
  );
  const { addNotification } = useNotifications();
  const queryClient = useQueryClient();

  const { data: balanceData } = useGetBalance();

  const cartQuery = useGetCart({});

  const deliveryAddressesQuery = useGetDeliveryAddresses();

  const createOrderMutation = useCreateOrderFromCart({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Order created successfully!',
          message: 'Your order has been placed and your cart has been cleared.',
        });
        queryClient.invalidateQueries({ queryKey: ['cart'] });
        queryClient.invalidateQueries({ queryKey: ['orders'] });
        close();
      },
      onError: (error) => {
        addNotification({
          type: 'error',
          title: 'Failed to create order',
          message: error.message,
        });
      },
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    if (!selectedAddressId) {
      addNotification({
        type: 'error',
        title: 'Address required',
        message: 'Please select a delivery address',
      });
      return;
    }

    createOrderMutation.mutate(Number(selectedAddressId));
  };

  const cart = cartQuery.data;
  const deliveryAddresses = deliveryAddressesQuery.data;

  if (!cart || !deliveryAddresses) {
    return null;
  }
  const totalPrice = cart.totalAmount || 0;
  const currentBalance = balanceData?.currentBalance?.amount || 0;
  const hasSufficientFunds = currentBalance >= totalPrice.amount;

  return (
    <>
      <Button onClick={open} disabled={disabled} className={className}>
        Create Order
      </Button>

      <Drawer
        open={isOpen}
        onOpenChange={(isOpen) => (isOpen ? open() : close())}
      >
        <DrawerContent>
          <DrawerHeader>
            <DrawerTitle>Create Order</DrawerTitle>
          </DrawerHeader>

          <form onSubmit={handleSubmit} className="p-6 space-y-6">
            {/* Order Summary */}
            <div className="bg-gray-50 p-4 rounded-lg">
              <h3 className="font-medium text-gray-900 mb-3">Order Summary</h3>
              <div className="space-y-2">
                <div className="flex justify-between">
                  <span className="text-gray-600">Total Items:</span>
                  <span className="font-medium">
                    {cart?.items.reduce(
                      (sum, item) => sum + item.quantity,
                      0,
                    ) || 0}
                  </span>
                </div>
                <div className="flex justify-between">
                  <span className="text-gray-600">Total Price:</span>
                  <span className="font-bold text-lg">
                    ${totalPrice.amount + totalPrice.currency}
                  </span>
                </div>
                <div className="flex justify-between">
                  <span className="text-gray-600">Your Balance:</span>
                  <span
                    className={`font-medium ${hasSufficientFunds ? 'text-green-600' : 'text-red-600'}`}
                  >
                    ${currentBalance.toFixed(2)}
                  </span>
                </div>
              </div>

              {!hasSufficientFunds && (
                <div className="mt-3 p-3 bg-yellow-50 border border-yellow-200 rounded-md">
                  <p className="text-sm text-yellow-800">
                    <strong>Note:</strong> You don't have sufficient funds to
                    pay for this order immediately. The order will be created
                    with status "PENDING" and you can pay later.
                  </p>
                </div>
              )}
            </div>

            {/* Address Selection */}
            <div>
              <h3 className="font-medium text-gray-900 mb-3">
                Delivery Address
              </h3>

              {deliveryAddressesQuery.isLoading ? (
                <Spinner />
              ) : !deliveryAddresses || deliveryAddresses.length === 0 ? (
                <div className="text-center py-4">
                  <p className="text-gray-500 mb-2">
                    No delivery addresses found
                  </p>
                  <p className="text-sm text-gray-400">
                    Please add a delivery address in your profile first
                  </p>
                </div>
              ) : (
                <div className="space-y-3">
                  {deliveryAddresses.map((address: DeliveryAddress) => (
                    <label
                      key={address.id}
                      className="flex items-center space-x-3 cursor-pointer"
                    >
                      <input
                        type="radio"
                        name="address"
                        value={address.id}
                        checked={selectedAddressId === address.id}
                        onChange={(e) => setSelectedAddressId(e.target.value)}
                        className="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300"
                      />
                      <div
                        className={`flex-1 p-3 border rounded-lg ${
                          selectedAddressId === address.id
                            ? 'border-blue-500 bg-blue-50'
                            : 'border-gray-200'
                        }`}
                      >
                        {address.isDefault && (
                          <span className="inline-flex items-center px-2 py-1 rounded-full text-xs font-medium bg-blue-100 text-blue-800 mb-2">
                            Default
                          </span>
                        )}
                        <p className="font-medium">{address.street}</p>
                        <p className="text-sm text-gray-600">
                          {address.city}, {address.postalCode}
                        </p>
                        <p className="text-sm text-gray-600">
                          {address.country}
                        </p>
                      </div>
                    </label>
                  ))}
                </div>
              )}
            </div>

            {/* Action Buttons */}
            <div className="flex space-x-3 pt-4">
              <Button
                type="button"
                variant="outline"
                onClick={close}
                className="flex-1"
              >
                Cancel
              </Button>
              <Button
                type="submit"
                disabled={
                  createOrderMutation.isPending ||
                  !selectedAddressId ||
                  !deliveryAddresses?.length
                }
                className="flex-1"
              >
                {createOrderMutation.isPending
                  ? 'Creating Order...'
                  : 'Create Order'}
              </Button>
            </div>
          </form>
        </DrawerContent>
      </Drawer>
    </>
  );
};
