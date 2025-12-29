import React from 'react';
import { useDeleteDeliveryAddress } from '../api/delete-address';
import { Spinner } from '@/shared/components/ui/spinner';
import { Button } from '@/shared/components/ui/button';
import { ConfirmationDialog } from '@/shared/components/ui/dialog';
import { useNotifications } from '@/shared/components/ui/notifications';
import { DeliveryAddress } from '../api.types.ts';
import { AddressForm } from './address-form';
import { useGetDeliveryAddresses } from '@/shared/api';

interface AddressCardProps {
  address: DeliveryAddress;
}

const AddressCard: React.FC<AddressCardProps> = ({ address }) => {
  const { addNotification } = useNotifications();

  const deleteDeliveryAddressMutation = useDeleteDeliveryAddress({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Address deleted successfully!',
        });
      },
    },
  });

  return (
    <div
      className={`p-4 border rounded-lg ${address.isDefault ? 'border-blue-500 bg-blue-50' : 'border-gray-200'}`}
    >
      {address.isDefault && (
        <span className="inline-flex items-center px-2.5 py-0.5 rounded-full text-xs font-medium bg-blue-100 text-blue-800 mb-2">
          Default
        </span>
      )}

      <div className="space-y-1">
        <p className="font-medium text-gray-900">{address.street}</p>
        <p className="text-sm text-gray-600">
          {address.city}, {address.postalCode}
        </p>
        <p className="text-sm text-gray-600">{address.country}</p>
      </div>

      <div className="mt-3 flex space-x-2">
        <AddressForm address={address} mode="edit" />
        <ConfirmationDialog
          icon="danger"
          title="Delete Address"
          body="Are you sure you want to delete this address? This action cannot be undone."
          triggerButton={
            <Button
              variant="outline"
              size="sm"
              className="text-red-600 border-red-300 hover:bg-red-50"
            >
              Delete
            </Button>
          }
          confirmButton={
            <Button
              isLoading={deleteDeliveryAddressMutation.isPending}
              type="button"
              variant="destructive"
              onClick={() =>
                deleteDeliveryAddressMutation.mutate({ id: address.id })
              }
            >
              Delete Address
            </Button>
          }
        />
      </div>
    </div>
  );
};

export const AddressList: React.FC = () => {
  const addressesQuery = useGetDeliveryAddresses();

  if (addressesQuery.isLoading) {
    return (
      <div className="flex h-48 w-full items-center justify-center">
        <Spinner size="lg" />
      </div>
    );
  }

  const addresses = addressesQuery.data;

  if (!addresses || addresses.length === 0) {
    return (
      <div className="text-center py-8">
        <p className="text-gray-500">No delivery addresses found</p>
        <p className="text-sm text-gray-400 mt-1">
          Add your first delivery address to get started
        </p>
      </div>
    );
  }

  return (
    <div className="space-y-4">
      <div className="grid grid-cols-1 md:grid-cols-2 gap-4">
        {addresses.map((address) => (
          <AddressCard key={address.id} address={address} />
        ))}
      </div>
    </div>
  );
};
