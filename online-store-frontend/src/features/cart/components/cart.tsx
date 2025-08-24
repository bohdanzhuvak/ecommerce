import React from 'react';
import {CartItemComponent} from './cart-item-component.tsx';
import {useCart} from '../api/get-cart';
import {ClearCart} from "@/features/cart/components/clear-cart";
import {CreateOrderForm} from "@/features/orders/components/create-order-form";
import {BalanceDisplay} from "@/features/balance";
import {useBalance} from "@/features/balance/hooks/use-balance";

export const Cart: React.FC = () => {
  const cartQuery = useCart({});
  const { data: balanceData } = useBalance();

  if (cartQuery.isLoading) {
    return <div className="flex justify-center p-8">Loading cart...</div>;
  }

  const cart = cartQuery.data;
  if (!cart || cart.items.length === 0) {
    return (
      <div className="p-8 text-center">
        <h2 className="text-2xl font-bold mb-4">Your Cart</h2>
        <p className="text-gray-600">Your cart is empty</p>
      </div>
    );
  }

  const totalItems = cart.items.reduce((sum, item) => sum + item.quantity, 0);
  const totalPrice = cart.totalPrice;
  const currentBalance = balanceData?.currentBalance || 0;
  const hasSufficientFunds = currentBalance >= totalPrice;

  return (
    <div className="p-6">
      <div className="flex justify-between items-center mb-6">
        <h2 className="text-2xl font-bold">Your Cart</h2>
        <ClearCart/>
      </div>

      <div className="space-y-4 mb-6">
        {cart.items.map((item) => (
          <CartItemComponent
            key={item.productId}
            item={item}
          />
        ))}
      </div>

      <div className="border-t pt-4">
        {/* Balance Information */}
        <div className="bg-gray-50 p-4 rounded-lg mb-4">
          <div className="flex justify-between items-center mb-2">
            <span className="text-sm font-medium text-gray-700">Your Balance:</span>
            <BalanceDisplay />
          </div>
          
          {!hasSufficientFunds && (
            <div className="bg-yellow-50 border border-yellow-200 rounded-md p-3 mt-2">
              <div className="flex">
                <div className="flex-shrink-0">
                  <svg className="h-5 w-5 text-yellow-400" viewBox="0 0 20 20" fill="currentColor">
                    <path fillRule="evenodd" d="M8.257 3.099c.765-1.36 2.722-1.36 3.486 0l5.58 9.92c.75 1.334-.213 2.98-1.742 2.98H4.42c-1.53 0-2.493-1.646-1.743-2.98l5.58-9.92zM11 13a1 1 0 11-2 0 1 1 0 012 0zm-1-8a1 1 0 00-1 1v3a1 1 0 002 0V6a1 1 0 00-1-1z" clipRule="evenodd" />
                  </svg>
                </div>
                <div className="ml-3">
                  <h3 className="text-sm font-medium text-yellow-800">
                    Insufficient funds
                  </h3>
                  <div className="mt-2 text-sm text-yellow-700">
                    <p>
                      You need ${(totalPrice - currentBalance).toFixed(2)} more to complete this order.
                      You can still create the order and pay later.
                    </p>
                  </div>
                </div>
              </div>
            </div>
          )}
        </div>

        <div className="flex justify-between items-center mb-4">
          <span className="text-lg font-semibold">Total Items: {totalItems}</span>
          <span className="text-xl font-bold">Total: ${totalPrice.toFixed(2)}</span>
        </div>
        
        <div className="flex justify-end">
          <CreateOrderForm 
            className={`${
              hasSufficientFunds 
                ? 'bg-green-600 hover:bg-green-700 text-white' 
                : 'bg-blue-600 hover:bg-blue-700 text-white'
            }`}
          />
        </div>
      </div>
    </div>
  );
};
