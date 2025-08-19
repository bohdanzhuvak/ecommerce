import React from 'react';
import {Button} from '@/shared/components/ui/button';
import {useCreateProduct} from '@/features/admin/products/api/create-product.ts';
import {CreateProductRequest, ProductForm, createProductFormSchema} from '../api.types';
import {useNotifications} from "@/shared/components/ui/notifications";
import {Authorization} from "@/shared/lib/auth/authorization.tsx";
import {ROLES} from "@/shared/types/api.ts";
import {Form, FormDrawer, Input, Textarea} from "@/shared/components/ui/form";
import {Plus} from "lucide-react";

export const CreateProduct: React.FC = () => {
  const {addNotification} = useNotifications();
  const createProductMutation = useCreateProduct({
    mutationConfig: {
      onSuccess: () => {
        addNotification({
          type: 'success',
          title: 'Product created successfully',
        });
      }
    }
  });
  return (
    <Authorization allowedRoles={[ROLES.ADMIN]}>
      <FormDrawer isDone={createProductMutation.isSuccess}
                  triggerButton={
                    <Button size="sm" icon={<Plus className="size-4"/>}>
                      Create Product
                    </Button>
                  }
                  submitButton={
                    <Button
                      form="create-product"
                      type="submit"
                      size="sm"
                      isLoading={createProductMutation.isPending}
                    >
                      Submit
                    </Button>
                  }
                  title="CreateProduct">
        <Form
          id="create-product"
          onSubmit={(values: ProductForm) => {
            const payload: CreateProductRequest = {
              ...values,
              imageUrls: values.imageUrls ? values.imageUrls.split(',').map(s => s.trim()) : [],
            };
            createProductMutation.mutate(payload);
          }}
          schema={createProductFormSchema}
        >
          {({ register, formState }) => (
            <>
              <Input
                label="Name"
                error={formState.errors['name']}
                registration={register('name')}
              />

              <Textarea
                label="Description"
                error={formState.errors['description']}
                registration={register('description')}
              />

              <Input
                label="Price"
                type="number"
                step="0.01"
                error={formState.errors['price']}
                registration={register('price', { valueAsNumber: true })}
              />

              <Input
                label="Stock"
                type="number"
                error={formState.errors['stock']}
                registration={register('stock', { valueAsNumber: true })}
              />

              <Input
                label="Category ID"
                type="number"
                error={formState.errors['categoryId']}
                registration={register('categoryId', { valueAsNumber: true })}
              />

              <Input
                label="Image URLs (comma separated)"
                error={formState.errors['imageUrls']}
                registration={register('imageUrls')}
              />
            </>
          )}
        </Form>

      </FormDrawer>
    </Authorization>
  );
};


