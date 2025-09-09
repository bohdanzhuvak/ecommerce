import {
  buildSpringPaginationQuery,
  validateSpringResponse,
} from '../../api/utils.ts';
import { endpoints } from '../../api/endpoints.ts';

export const getManyReference =
  (httpClient: any) => async (resource: string, params: any) => {
    const query = buildSpringPaginationQuery(params);
    const url = `${endpoints.list(resource, query)}`;
    const { json } = await httpClient(url, { signal: params?.signal });

    validateSpringResponse(json);

    return {
      data: json.content,
      total: json.totalElements,
    };
  };
