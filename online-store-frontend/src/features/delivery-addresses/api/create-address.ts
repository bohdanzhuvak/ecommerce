import {api} from '@/shared/lib/api-client';
import {CreateDeliveryAddressRequest, DeliveryAddress} from "../api.types.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {MutationConfig} from "@/shared/lib/react-query.ts";

export const createDeliveryAddress = (request: CreateDeliveryAddressRequest): Promise<DeliveryAddress> => {
  return api.post('/users/delivery-addresses', request);
};

type UseCreateDeliveryAddressOptions = {
  mutationConfig?: MutationConfig<typeof createDeliveryAddress>;
};

export const useCreateDeliveryAddress = ({mutationConfig}: UseCreateDeliveryAddressOptions = {}) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {}

  return useMutation({
    mutationFn: createDeliveryAddress,
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['delivery-addresses']});
      onSuccess?.(...args);
    },
    ...restConfig,
  })
}
