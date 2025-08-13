import {QueryClient} from '@tanstack/react-query';
import {ContentLayout} from '@/shared/components/layouts';
import {AdminCategoryCreate} from '@/features/admin/categories/components/create-category';

export const adminCategoriesLoader = (_queryClient: QueryClient) => async () => null;

export const AdminCategoriesRoute = () => {
  return (
    <ContentLayout title="Admin Categories">
      <AdminCategoryCreate/>
    </ContentLayout>
  );
};


