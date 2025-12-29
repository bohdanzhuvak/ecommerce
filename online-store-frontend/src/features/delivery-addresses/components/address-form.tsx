import { Edit, Plus } from 'lucide-react';

import { Button } from '@/shared/components/ui/button';
import {
  Controller,
  Form,
  FormDrawer,
  Input,
  Switch,
} from '@/shared/components/ui/form';
import { useNotifications } from '@/shared/components/ui/notifications';

import { useUpdateDeliveryAddress } from '../api/update-address';
import {
  type AddressFormData,
  createAddressFormSchema,
  type DeliveryAddress,
  type UpdateAddressFormData,
  updateAddressFormSchema,
} from '../api.types';
import { useAddDeliveryAddress } from '@/shared/api';

interface AddressFormProps {
  address?: DeliveryAddress;
  mode?: 'create' | 'edit';
}

export const AddressForm = ({ address, mode = 'create' }: AddressFormProps) => {
  const { addNotification } = useNotifications();
  const isEditMode = mode === 'edit' && address;

  const createDeliveryAddressMutation = useAddDeliveryAddress({
    mutation: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Address added successfully!',
        });
      },
    },
  });

  const updateDeliveryAddressMutation = useUpdateDeliveryAddress({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Address updated successfully!',
        });
      },
    },
  });

  const handleSubmit = (values: AddressFormData | UpdateAddressFormData) => {
    if (isEditMode && address) {
      updateDeliveryAddressMutation.mutate(values as UpdateAddressFormData);
    } else {
      createDeliveryAddressMutation.mutate({ data: values as AddressFormData });
    }
  };

  const isSuccess = isEditMode
    ? updateDeliveryAddressMutation.isSuccess
    : createDeliveryAddressMutation.isSuccess;
  const isLoading = isEditMode
    ? updateDeliveryAddressMutation.isPending
    : createDeliveryAddressMutation.isPending;

  return (
    <FormDrawer
      isDone={isSuccess}
      triggerButton={
        isEditMode ? (
          <Button size="sm" icon={<Edit className="size-4" />}>
            Edit
          </Button>
        ) : (
          <Button size="sm" icon={<Plus className="size-4" />}>
            Add Address
          </Button>
        )
      }
      title={isEditMode ? 'Edit Address' : 'Add Address'}
      submitButton={
        <Button
          form="address-form"
          type="submit"
          size="sm"
          isLoading={isLoading}
        >
          {isEditMode ? 'Update' : 'Submit'}
        </Button>
      }
    >
      <Form
        id="address-form"
        onSubmit={handleSubmit}
        schema={isEditMode ? updateAddressFormSchema : createAddressFormSchema}
        options={{
          defaultValues:
            isEditMode && address
              ? {
                  id: address.id,
                  street: address.street,
                  city: address.city,
                  postalCode: address.postalCode,
                  country: address.country,
                  isDefault: address.isDefault,
                }
              : {
                  street: '',
                  city: '',
                  state: '',
                  postalCode: '',
                  country: '',
                  recipientName: '',
                  isDefault: false,
                },
        }}
      >
        {({ register, formState, control }) => (
          <>
            {isEditMode && <input type="hidden" {...register('id')} />}
            <Input
              label="Street Address"
              placeholder="123 Main St"
              error={formState.errors['street']}
              registration={register('street')}
            />

            <Input
              label="City"
              placeholder="New York"
              error={formState.errors['city']}
              registration={register('city')}
            />

            {!isEditMode && (
              <Input
                label="State"
                placeholder="California"
                error={formState.errors['state']}
                registration={register('state')}
              />
            )}

            <Input
              label="Postal Code"
              placeholder="10001"
              error={formState.errors['postalCode']}
              registration={register('postalCode')}
            />

            <Input
              label="Country"
              placeholder="United States"
              error={formState.errors['country']}
              registration={register('country')}
            />

            {!isEditMode && (
              <Input
                label="Recipient name"
                placeholder="Thomas Shelby"
                error={formState.errors['recipientName']}
                registration={register('recipientName')}
              />
            )}

            <div className="flex items-center space-x-2">
              <Controller
                name="isDefault"
                control={control}
                render={({ field }) => (
                  <Switch
                    id="isDefault"
                    checked={field.value || false}
                    onCheckedChange={field.onChange}
                  />
                )}
              />
              <label htmlFor="isDefault" className="text-sm font-medium">
                Set as default address
              </label>
            </div>
          </>
        )}
      </Form>
    </FormDrawer>
  );
};
