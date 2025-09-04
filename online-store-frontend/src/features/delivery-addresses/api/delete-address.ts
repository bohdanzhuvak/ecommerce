import {api} from '@/shared/lib/api-client';
import {DeleteDeliveryAddressRequest} from "../api.types.ts";
import {useMutation, useQueryClient} from "@tanstack/react-query";
import {MutationConfig} from "@/shared/lib/react-query.ts";

export const deleteDeliveryAddress = (request: DeleteDeliveryAddressRequest): Promise<void> => {
  return api.delete(`/users/delivery-addresses/${request.id}`);
};

type UseDeleteDeliveryAddressOptions = {
  mutationConfig?: MutationConfig<typeof deleteDeliveryAddress>;
};

export const useDeleteDeliveryAddress = ({mutationConfig}: UseDeleteDeliveryAddressOptions = {}) => {
  const queryClient = useQueryClient();
  const {onSuccess, ...restConfig} = mutationConfig || {}

  return useMutation({
    mutationFn: deleteDeliveryAddress,
    onSuccess: (...args) => {
      queryClient.invalidateQueries({queryKey: ['delivery-addresses']});
      onSuccess?.(...args);
    },
    ...restConfig,
  })
}
