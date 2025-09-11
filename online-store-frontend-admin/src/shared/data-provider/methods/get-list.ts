import { fetchUtils } from 'ra-core';
import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider.ts';
import { GetListParams, GetListResult, RaRecord } from 'react-admin';

export const getList =
  (httpClient: HttpClient) =>
  async <RecordType extends RaRecord = any>(
    resource: string,
    params: GetListParams,
  ): Promise<GetListResult<RecordType>> => {
    const { page, perPage } = params.pagination || { page: 1, perPage: 10 };
    const { field, order } = params.sort || { field: 'id', order: 'ASC' };

    const query = {
      sort: field,
      order: order,
      page: page,
      perPage: perPage,
      ...params.filter,
    };

    const url = `${endpoints.list(resource, fetchUtils.queryParameters(query))}`;
    const { json } = await httpClient<{ data: RecordType[]; total: number }>(
      url,
      {
        signal: params?.signal,
      },
    );

    return json;
  };
