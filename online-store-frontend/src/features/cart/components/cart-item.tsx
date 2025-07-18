import React from 'react';
import {CartItemDTO} from '../api/get-cart';
import {RemoveFromCart} from "@/features/cart/components/remove-from-cart";

interface CartItemProps {
  item: CartItemDTO;
  clientEmail: string;
}

export const CartItem: React.FC<CartItemProps> = ({item, clientEmail}) => {
  return (
    <div className="flex items-center justify-between p-4 border rounded-lg">
      <div className="flex-1">
        <h3 className="font-semibold text-lg">{item.book.name}</h3>
        <p className="text-gray-600">by {item.book.author}</p>
        <p className="text-sm text-gray-500">
          Age Group: {item.book.ageGroup} | Language: {item.book.language}
        </p>
      </div>

      <div className="flex items-center space-x-4">
        <div className="text-center">
          <p className="font-semibold">Quantity: {item.quantity}</p>
          <p className="text-lg font-bold text-blue-600">
            ${(item.book.price * item.quantity).toFixed(2)}
          </p>
        </div>

        <RemoveFromCart
          clientEmail={clientEmail}
          bookName={item.book.name}/>

      </div>
    </div>
  );
}; 