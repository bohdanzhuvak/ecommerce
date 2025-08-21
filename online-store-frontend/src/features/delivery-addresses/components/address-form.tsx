import React, { useState, useEffect } from 'react';
import { useMutation, useQueryClient } from '@tanstack/react-query';
import { createDeliveryAddress } from '../api/create-address';
import { Button } from '@/shared/components/ui/button';
import { useDisclosure } from '@/shared/hooks/use-disclosure';
import { Drawer, DrawerContent, DrawerHeader, DrawerTitle } from '@/shared/components/ui/drawer';
import { CreateDeliveryAddressRequest, DeliveryAddress } from '@/shared/types';

interface AddressFormProps {
  address?: DeliveryAddress;
  onSuccess?: () => void;
}

export const AddressForm: React.FC<AddressFormProps> = ({ address, onSuccess }) => {
  const { isOpen, open, close } = useDisclosure();
  const [formData, setFormData] = useState<CreateDeliveryAddressRequest>({
    street: '',
    city: '',
    postalCode: '',
    country: '',
    phone: '',
    isDefault: false,
  });
  
  const queryClient = useQueryClient();

  useEffect(() => {
    if (address) {
      setFormData({
        street: address.street,
        city: address.city,
        postalCode: address.postalCode,
        country: address.country,
        phone: address.phone,
        isDefault: address.isDefault,
      });
    }
  }, [address]);

  const createMutation = useMutation({
    mutationFn: createDeliveryAddress,
    onSuccess: () => {
      queryClient.invalidateQueries({ queryKey: ['delivery-addresses'] });
      close();
      setFormData({
        street: '',
        city: '',
        postalCode: '',
        country: '',
        phone: '',
        isDefault: false,
      });
      onSuccess?.();
    },
  });

  const handleSubmit = (e: React.FormEvent) => {
    e.preventDefault();
    createMutation.mutate(formData);
  };

  const handleInputChange = (field: keyof CreateDeliveryAddressRequest, value: string | boolean) => {
    setFormData(prev => ({ ...prev, [field]: value }));
  };

  return (
    <>
      <Button onClick={open} variant="outline" size="sm">
        {address ? 'Edit Address' : 'Add Address'}
      </Button>

      <Drawer open={isOpen} onOpenChange={(isOpen) => isOpen ? open() : close()}>
        <DrawerContent>
          <DrawerHeader>
            <DrawerTitle>{address ? 'Edit Address' : 'Add New Address'}</DrawerTitle>
          </DrawerHeader>
          
          <form onSubmit={handleSubmit} className="p-6 space-y-4">
            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Street Address *
              </label>
              <input
                type="text"
                value={formData.street}
                onChange={(e) => handleInputChange('street', e.target.value)}
                placeholder="123 Main St"
                required
                className="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                City *
              </label>
              <input
                type="text"
                value={formData.city}
                onChange={(e) => handleInputChange('city', e.target.value)}
                placeholder="New York"
                required
                className="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Postal Code *
              </label>
              <input
                type="text"
                value={formData.postalCode}
                onChange={(e) => handleInputChange('postalCode', e.target.value)}
                placeholder="10001"
                required
                className="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Country *
              </label>
              <input
                type="text"
                value={formData.country}
                onChange={(e) => handleInputChange('country', e.target.value)}
                placeholder="United States"
                required
                className="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring"
              />
            </div>

            <div>
              <label className="block text-sm font-medium text-gray-700 mb-1">
                Phone *
              </label>
              <input
                type="tel"
                value={formData.phone}
                onChange={(e) => handleInputChange('phone', e.target.value)}
                placeholder="+1234567890"
                required
                className="flex h-9 w-full rounded-md border border-input bg-transparent px-3 py-1 text-sm shadow-sm transition-colors focus-visible:outline-none focus-visible:ring-1 focus-visible:ring-ring"
              />
            </div>

            <div className="flex items-center">
              <input
                type="checkbox"
                id="isDefault"
                checked={formData.isDefault}
                onChange={(e) => handleInputChange('isDefault', e.target.checked)}
                className="h-4 w-4 text-blue-600 focus:ring-blue-500 border-gray-300 rounded"
              />
              <label htmlFor="isDefault" className="ml-2 block text-sm text-gray-900">
                Set as default address
              </label>
            </div>

            <Button
              type="submit"
              disabled={createMutation.isPending || !formData.street || !formData.city || 
                       !formData.postalCode || !formData.country || !formData.phone}
              className="w-full"
            >
              {createMutation.isPending ? 'Saving...' : (address ? 'Update Address' : 'Add Address')}
            </Button>
          </form>
        </DrawerContent>
      </Drawer>
    </>
  );
};
