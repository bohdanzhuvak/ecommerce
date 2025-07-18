import {queryOptions, useQuery} from '@tanstack/react-query';
import {api} from '@/shared/lib/api-client';
import {QueryConfig} from '@/shared/lib/react-query';
import {OrderDTO} from './get-orders';

export const getOrdersByEmployee = ({employeeEmail}: { employeeEmail: string }): Promise<OrderDTO[]> => {
  return api.get(`/orders/employee/${employeeEmail}`);
};

export const getOrdersByEmployeeQueryOptions = (employeeEmail: string) => {
  return queryOptions({
    queryKey: ['orders', 'employee', employeeEmail],
    queryFn: () => getOrdersByEmployee({employeeEmail}),
    enabled: !!employeeEmail,
  });
};

type UseOrdersByEmployeeOptions = {
  employeeEmail: string;
  queryConfig?: QueryConfig<typeof getOrdersByEmployeeQueryOptions>;
};

export const useOrdersByEmployee = ({employeeEmail, queryConfig}: UseOrdersByEmployeeOptions) => {
  return useQuery({
    ...getOrdersByEmployeeQueryOptions(employeeEmail),
    ...queryConfig,
  });
};
