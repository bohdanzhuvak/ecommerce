import {Plus} from 'lucide-react';

import {Button} from '@/shared/components/ui/button';
import {Controller, Form, FormDrawer, Input, Switch} from '@/shared/components/ui/form';
import {useNotifications} from '@/shared/components/ui/notifications';

import {useCreateDeliveryAddress} from '../api/create-address';
import {type AddressFormData, createAddressFormSchema} from '../api.types';

export const AddressForm = () => {
  const {addNotification} = useNotifications();
  const createDeliveryAddressMutation = useCreateDeliveryAddress({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Address added successfully!',
        });
      },
    },
  });

  return (
    <FormDrawer
      isDone={createDeliveryAddressMutation.isSuccess}
      triggerButton={
        <Button size="sm" icon={<Plus className="size-4"/>}>
          Add Address
        </Button>
      }
      title="Add Address"
      submitButton={
        <Button
          form="address-form"
          type="submit"
          size="sm"
          isLoading={createDeliveryAddressMutation.isPending}
        >
          Submit
        </Button>
      }
    >
      <Form
        id="address-form"
        onSubmit={(values: AddressFormData) => {
          console.log('Form submitted with values:', values);
          createDeliveryAddressMutation.mutate(values);
        }}
        schema={createAddressFormSchema}
        options={{
          defaultValues: {
            street: '',
            city: '',
            postalCode: '',
            country: '',
            phone: '',
            isDefault: false,
          },
        }}
      >
        {({register, formState, control}) => (
          <>
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

            <Input
              label="Phone"
              type="tel"
              placeholder="+1234567890"
              error={formState.errors['phone']}
              registration={register('phone')}
            />

            <div className="flex items-center space-x-2">
              <Controller
                name="isDefault"
                control={control}
                render={({field}) => (
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
