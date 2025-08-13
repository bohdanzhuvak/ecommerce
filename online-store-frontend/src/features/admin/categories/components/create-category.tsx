import React from 'react';
import {useForm} from 'react-hook-form';
import {Button} from '@/shared/components/ui/button';
import {createCategory} from '@/features/admin/categories/api/categories';

type CategoryForm = { name: string };

export const AdminCategoryCreate: React.FC = () => {
  const {register, handleSubmit, reset} = useForm<CategoryForm>();
  const [isLoading, setLoading] = React.useState(false);
  const onSubmit = async (values: CategoryForm) => {
    setLoading(true);
    try {
      await createCategory(values);
      reset();
    } finally {
      setLoading(false);
    }
  };
  return (
    <form onSubmit={handleSubmit(onSubmit)} className="space-y-3">
      <input className="border p-2 w-full" placeholder="Name" {...register('name', {required: true})}/>
      <Button type="submit" isLoading={isLoading}>Create Category</Button>
    </form>
  );
};


