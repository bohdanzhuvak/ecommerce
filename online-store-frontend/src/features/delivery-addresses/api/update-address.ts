import {api} from '@/shared/lib/api-client';
import {UpdateDeliveryAddressRequest, DeliveryAddress} from "../api.types.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {MutationConfig} from "@/shared/lib/react-query.ts";

export const updateDeliveryAddress = (request: UpdateDeliveryAddressRequest): Promise<DeliveryAddress> => {
  return api.put(`/users/delivery-addresses/${request.id}`, request);
};

type UseUpdateDeliveryAddressOptions = {
  mutationConfig?: MutationConfig<typeof updateDeliveryAddress>;
};

export const useUpdateDeliveryAddress = ({mutationConfig}: UseUpdateDeliveryAddressOptions = {}) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {}

  return useMutation({
    mutationFn: updateDeliveryAddress,
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['delivery-addresses']});
      onSuccess?.(...args);
    },
    ...restConfig,
  })
}
