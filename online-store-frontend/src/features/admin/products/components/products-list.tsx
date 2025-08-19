import {useSearchParams} from "react-router";
import {useProducts} from "@/features/products/api/get-products.ts";
import {Spinner} from "@/shared/components/ui/spinner";
import {Table} from "@/shared/components/ui/table";

export const ProductsList = () => {
  const [searchParams] = useSearchParams();

  const productsQuery = useProducts({
    page: +(searchParams.get('page') || 1) - 1,
    size: 12
  });
  if (productsQuery.isLoading) {
    return (
      <div className="flex h-48 w-full items-center justify-center">
        <Spinner size="lg"/>
      </div>
    );
  }

  const products = productsQuery.data?.content ?? [];

  if (!products) return null;
  const meta = {
    totalPages: productsQuery.data?.totalPages,
    totalElements: productsQuery.data?.totalElements,
    size: productsQuery.data?.size,
    number: productsQuery.data?.number,
  };

  return (
    <Table
      data={products}
      columns={[
        {
          title: 'id',
          field: 'id',
        },
        {
          title: 'Name',
          field: 'name',
        },
        {
          title: 'Description',
          field: 'description',
        },
        {
          title: 'Price',
          field: 'price',
        },
        {
          title: 'Stock',
          field: 'stock',
        },
        {
          title: 'Category',
          field: 'categoryName',
        },

      ]}
      pagination={
        meta && meta.totalPages && meta.totalPages > 1 ? {
          totalPages: meta.totalPages,
          currentPage: (meta.number || 0) + 1,
          rootUrl: '',
        } : undefined
      }
    />
  );
}
