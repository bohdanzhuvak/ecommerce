import React from 'react';
import {Order} from '../api.types';
import {Link} from "@/shared/components/ui/link";
import {paths} from "@/config/paths.ts";

interface OrderItemProps {
  order: Order;
}

export const OrderItem: React.FC<OrderItemProps> = ({order}) => {
  const formatDate = (dateString: string) => {
    return new Date(dateString).toLocaleDateString('en-US', {
      year: 'numeric',
      month: 'long',
      day: 'numeric',
      hour: '2-digit',
      minute: '2-digit'
    });
  };

  const getStatusColor = (status: string) => {
    switch (status?.toLowerCase()) {
      case 'created':
      case 'pending':
        return 'bg-yellow-100 text-yellow-800';
      case 'confirmed':
        return 'bg-blue-100 text-blue-800';
      case 'processing':
        return 'bg-purple-100 text-purple-800';
      case 'shipped':
        return 'bg-orange-100 text-orange-800';
      case 'delivered':
        return 'bg-green-100 text-green-800';
      case 'cancelled':
        return 'bg-red-100 text-red-800';
      default:
        return 'bg-gray-100 text-gray-800';
    }
  };

  return (
    <div className="border rounded-lg p-6 bg-white shadow-sm">
      <div className="flex justify-between items-start mb-4">
        <div>
          <Link
            to={paths.app.orders.order.getHref(order.id.toString())}
            className="text-lg font-semibold text-blue-600 hover:text-blue-800 hover:underline"
          >
            Order #{order.id}
          </Link>
          <p className="text-sm text-gray-600">{formatDate(order.createdAt)}</p>
        </div>
        <div className="text-right">
          <span className={`px-3 py-1 rounded-full text-xs font-medium ${getStatusColor(order.status)}`}>
            {order.status}
          </span>
          <p className="text-lg font-bold text-blue-600 mt-1">${order.totalPrice.toFixed(2)}</p>
        </div>
      </div>

      <div className="space-y-3">
        {order.items.map((item) => (
          <div key={`${item.productId}-${item.productName}`} className="flex justify-between items-center p-3 bg-gray-50 rounded">
            <div className="flex-1">
              <h4 className="font-medium">{item.productName}</h4>
            </div>
            <div className="text-right">
              <p className="font-medium">Quantity: {item.quantity}</p>
            </div>
          </div>
        ))}
      </div>
      <div className="mt-5">
        <Link
          to={paths.app.orders.order.getHref(order.id.toString())}
          className="text-lg text-right font-semibold text-blue-600 hover:text-blue-800 hover:underline"
        >
          Go to order details &rarr;
        </Link>
      </div>

    </div>
  );
};
