import React from 'react';
import { useDepositToBalance } from '../api/deposit';
import { Button } from '@/shared/components/ui/button';
import { useNotifications } from '@/shared/components/ui/notifications';
import { Form, FormDrawer, Input, Textarea } from '@/shared/components/ui/form';
import { Plus } from 'lucide-react';
import { depositInputSchema } from '../api.types.ts';

interface DepositFormProps {
  defaultAmount?: number;
}

export const DepositForm: React.FC<DepositFormProps> = ({ defaultAmount }) => {
  const { addNotification } = useNotifications();

  const depositMutation = useDepositToBalance({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Deposit Successful',
        });
      },
      onError: () => {
        addNotification({
          type: 'error',
          title: 'Deposit Failed',
        });
      },
    },
  });

  return (
    <FormDrawer
      isDone={depositMutation.isSuccess}
      triggerButton={
        <Button icon={<Plus className="size-4" />} size="sm" variant="outline">
          Deposit
        </Button>
      }
      title="Deposit to Balance"
      submitButton={
        <Button
          form="deposit-form"
          type="submit"
          size="sm"
          isLoading={depositMutation.isPending}
          className="w-full"
        >
          Submit
        </Button>
      }
    >
      <Form
        id="deposit-form"
        onSubmit={(values) => {
          depositMutation.mutate({
            data: {
              amount: parseFloat(values.amount),
              description: values.description || undefined,
              currency: 'USD',
            },
          });
        }}
        options={{
          defaultValues: {
            amount: defaultAmount ?? '',
            description: '',
          },
        }}
        schema={depositInputSchema}
      >
        {({ register, formState }) => (
          <>
            <Input
              type="number"
              step="0.01"
              min="0.01"
              label="Amount ($)"
              error={formState.errors['amount']}
              registration={register('amount')}
              placeholder="0.00"
            />
            <Textarea
              label="Description (optional)"
              error={formState.errors['description']}
              registration={register('description')}
              placeholder="e.g., Bank transfer"
            />
          </>
        )}
      </Form>
    </FormDrawer>
  );
};
