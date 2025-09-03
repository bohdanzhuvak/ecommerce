import {api} from '@/shared/lib/api-client';
import {DeliveryAddress} from "../api.types.ts";
import {QueryConfig} from "@/shared/lib/react-query.ts";
import {queryOptions, useQuery} from "@tanstack/react-query";

export const getDeliveryAddresses = (): Promise<DeliveryAddress[]> => {
  return api.get('/users/delivery-addresses');
};

export const getDeliveryAddressesQueryOptions = () => {
  return queryOptions({
    queryKey: ['delivery-addresses'],
    queryFn: () => getDeliveryAddresses(),
  });
}

type UseDeliveryAddressesOptions = {
  queryConfig?: QueryConfig<typeof getDeliveryAddresses>;
};

export const useDeliveryAddresses = ({queryConfig}: UseDeliveryAddressesOptions) => {
  return useQuery({
    ...getDeliveryAddressesQueryOptions(),
    ...queryConfig,
  })
}
