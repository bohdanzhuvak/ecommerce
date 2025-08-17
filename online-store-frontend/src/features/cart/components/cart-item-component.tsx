import React from 'react';
import {CartItem} from '../api.types';
import {RemoveFromCart} from "@/features/cart/components/remove-from-cart";

interface CartItemProps {
  item: CartItem;
}

export const CartItemComponent: React.FC<CartItemProps> = ({item}) => {
  return (
    <div className="flex items-center justify-between p-4 border rounded-lg">
      <div className="flex-1">
        <h3 className="font-semibold text-lg">{item.productName}</h3>
      </div>

      <div className="flex items-center space-x-4">
        <div className="text-center">
          <p className="font-semibold">Quantity: {item.quantity}</p>
          <p className="text-lg font-bold text-blue-600">
            ${(item.price * item.quantity).toFixed(2)}
          </p>
        </div>

        <RemoveFromCart productId={item.productId}/>
      </div>
    </div>
  );
};
