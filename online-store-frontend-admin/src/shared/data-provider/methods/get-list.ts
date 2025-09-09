import { buildSpringPaginationQuery } from '../../api/utils.ts';
import { endpoints } from '../../api/endpoints.ts';
import { HttpClient, SpringPageResponse } from '../../../types/data-provider';
import { GetListParams } from 'ra-core';

export const getList =
  (httpClient: HttpClient) =>
  async (resource: string, params: GetListParams) => {
    const query = buildSpringPaginationQuery(params);
    const url = `${endpoints.list(resource, query)}`;
    const { json } = await httpClient<SpringPageResponse>(url, {
      signal: params?.signal,
    });

    return {
      data: json.content,
      total: json.totalElements,
    };
  };
