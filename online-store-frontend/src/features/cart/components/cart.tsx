import React from 'react';
import {CartItem} from './cart-item';
import {useCart} from '../api/get-cart';
import {ClearCart} from "@/features/cart/components/clear-cart";
import {CreateOrderButton} from "@/features/orders/components/create-order-button";
import {Authorization} from "@/shared/lib/auth/authorization";

export const Cart: React.FC = () => {
  const cartQuery = useCart({});

  if (cartQuery.isLoading) {
    return <div className="flex justify-center p-8">Loading cart...</div>;
  }

  const cart = cartQuery.data;
  if (!cart || cart.items.length === 0) {
    return (
      <Authorization allowedRoles={['USER']}
                     forbiddenFallback={<div className="p-8 text-center">This page is only for customer</div>}>
        <div className="p-8 text-center">
          <h2 className="text-2xl font-bold mb-4">Your Cart</h2>
          <p className="text-gray-600">Your cart is empty</p>
        </div>
      </Authorization>
    );
  }

  const totalItems = cart.items.reduce((sum, item) => sum + item.quantity, 0);
  const totalPrice = cart.totalPrice;

  return (
    <Authorization allowedRoles={['USER']}
                   forbiddenFallback={<div className="p-8 text-center">This page is only for customer</div>}>
      <div className="p-6">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-2xl font-bold">Your Cart</h2>
          <ClearCart/>
        </div>

        <div className="space-y-4 mb-6">
          {cart.items.map((item) => (
            <CartItem
              key={item.productId}
              item={item}
            />
          ))}
        </div>

        <div className="border-t pt-4">
          <div className="flex justify-between items-center mb-4">
            <span className="text-lg font-semibold">Total Items: {totalItems}</span>
            <span className="text-xl font-bold">Total: ${totalPrice.toFixed(2)}</span>
          </div>
          <div className="flex justify-end">
            <CreateOrderButton className="bg-green-600 hover:bg-green-700 text-white"/>
          </div>
        </div>
      </div>
    </Authorization>
  );
};
