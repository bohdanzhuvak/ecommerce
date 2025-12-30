import React from 'react';
import { PayOrderButton } from './pay-order-button';
import { Spinner } from '@/shared/components/ui/spinner';
import { OrderItem } from '../api.types';
import { DepositForm } from '@/features/balance';
import { useGetBalance, useGetOrder } from '@/shared/api';

interface OrderDetailsProps {
  orderId: string;
}

export const OrderDetails: React.FC<OrderDetailsProps> = ({ orderId }) => {
  const { data: balanceData } = useGetBalance();

  const orderQuery = useGetOrder(orderId);

  if (orderQuery.isLoading) {
    return (
      <div className="flex h-48 w-full items-center justify-center">
        <Spinner size="lg" />
      </div>
    );
  }

  const order = orderQuery.data;

  if (order == null) return null;

  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit',
    });
  };

  const getStatusColor = (status: string) => {
    switch (status?.toLowerCase()) {
      case 'pending':
        return 'bg-yellow-100 text-yellow-800';
      case 'paid':
        return 'bg-green-100 text-green-800';
      case 'shipped':
        return 'bg-blue-100 text-blue-800';
      case 'delivered':
        return 'bg-green-100 text-green-800';
      case 'cancelled':
        return 'bg-red-100 text-red-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  const currentBalance = balanceData?.currentBalance?.amount || 0;
  const canPay =
    order.status === 'PENDING' && currentBalance >= order.totalPrice.amount;

  return (
    <div className="max-w-4xl mx-auto">
      {/* Order Header */}
      <div className="bg-white rounded-lg shadow-sm border p-6 mb-6">
        <div className="flex justify-between items-start mb-4">
          <div>
            <h1 className="text-2xl font-bold text-gray-900">
              Order #{order.id}
            </h1>
            <p className="text-gray-600 mt-1">
              Placed on {formatDate(order.createdAt)}
            </p>
          </div>
          <div className="text-right">
            <span
              className={`px-4 py-2 rounded-full text-sm font-medium ${getStatusColor(order.status)}`}
            >
              {order.status}
            </span>
            <p className="text-2xl font-bold text-blue-600 mt-2">
              ${order.totalPrice.amount.toFixed(2)}
            </p>
          </div>
        </div>

        {/* Payment Actions */}
        {order.status === 'PENDING' && (
          <div className="border-t pt-4">
            <div className="flex items-center justify-between">
              <div>
                <p className="text-sm text-gray-600">
                  Your balance:{' '}
                  <span className="font-medium">
                    ${currentBalance.toFixed(2)}
                  </span>
                </p>
                {!canPay ? (
                  <>
                    <p className="text-sm text-red-600 mt-1">
                      Insufficient funds. You need $
                      {(order.totalPrice.amount - currentBalance).toFixed(2)}{' '}
                      more.
                    </p>
                    <DepositForm
                      defaultAmount={order.totalPrice.amount - currentBalance}
                    />
                  </>
                ) : (
                  <PayOrderButton
                    orderId={order.id}
                    orderTotal={order.totalPrice.amount}
                    userBalance={currentBalance}
                  />
                )}
              </div>
            </div>
          </div>
        )}
      </div>

      {/* Order Items */}
      <div className="bg-white rounded-lg shadow-sm border p-6 mb-6">
        <h2 className="text-lg font-semibold text-gray-900 mb-4">
          Order Items
        </h2>
        <div className="space-y-4">
          {order.items.map((item: OrderItem, index: number) => (
            <div
              key={index}
              className="flex justify-between items-center p-4 bg-gray-50 rounded-lg"
            >
              <div className="flex-1">
                <h3 className="font-medium text-gray-900">
                  {item.productName}
                </h3>
                <p className="text-sm text-gray-600">SKU: {item.productId}</p>
              </div>
              <div className="text-right">
                <p className="font-medium text-gray-900">
                  Quantity: {item.quantity}
                </p>
                <p className="text-sm text-gray-600">
                  ${item.productPrice.amount.toFixed(2)} each
                </p>
                <p className="font-semibold text-blue-600">
                  ${item.totalPrice.amount.toFixed(2)}
                </p>
              </div>
            </div>
          ))}
        </div>
      </div>

      {/* Delivery Address */}
      {order.deliveryAddress && (
        <div className="bg-white rounded-lg shadow-sm border p-6 mb-6">
          <h2 className="text-lg font-semibold text-gray-900 mb-4">
            Delivery Address
          </h2>
          <div className="bg-gray-50 p-4 rounded-lg">
            <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
              <div>
                <p className="font-medium text-gray-900">
                  {order.deliveryAddress.street}
                </p>
                <p className="text-gray-600">
                  {order.deliveryAddress.city},{' '}
                  {order.deliveryAddress.postalCode}
                </p>
                <p className="text-gray-600">{order.deliveryAddress.country}</p>
              </div>
            </div>
          </div>
        </div>
      )}

      {/* Order Summary */}
      <div className="bg-white rounded-lg shadow-sm border p-6">
        <h2 className="text-lg font-semibold text-gray-900 mb-4">
          Order Summary
        </h2>
        <div className="space-y-3">
          <div className="flex justify-between text-sm">
            <span className="text-gray-600">Subtotal:</span>
            <span className="font-medium">
              ${order.totalPrice.amount.toFixed(2)}
            </span>
          </div>
          <div className="flex justify-between text-sm">
            <span className="text-gray-600">Shipping:</span>
            <span className="font-medium">Free</span>
          </div>
          <div className="border-t pt-3">
            <div className="flex justify-between text-lg font-bold">
              <span>Total:</span>
              <span className="text-blue-600">
                ${order.totalPrice.amount.toFixed(2)}
              </span>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
};
