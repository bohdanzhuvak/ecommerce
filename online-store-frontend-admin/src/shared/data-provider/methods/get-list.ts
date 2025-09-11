import { fetchUtils } from 'ra-core';
import { endpoints } from '../../api/endpoints.ts';
import { HttpClient, ListQuery } from '../../../types/data-provider.ts';
import { GetListParams, GetListResult, RaRecord } from 'react-admin';

export const getList =
  (httpClient: HttpClient) =>
  async <RecordType extends RaRecord = any>(
    resource: string,
    params: GetListParams,
  ): Promise<GetListResult<RecordType>> => {
    const { page, perPage } = params.pagination || { page: 1, perPage: 10 };
    const { field, order } = params.sort || { field: 'id', order: 'ASC' };

    const query: ListQuery = {
      sort: field,
      order,
      page,
      perPage,
    };

    if (params.filter && Object.keys(params.filter).length > 0) {
      query.filter = JSON.stringify(params.filter);
    }

    const url = `${endpoints.list(resource, fetchUtils.queryParameters(query))}`;
    const { json } = await httpClient<{ data: RecordType[]; total: number }>(
      url,
      {
        signal: params?.signal,
      },
    );

    return json;
  };
