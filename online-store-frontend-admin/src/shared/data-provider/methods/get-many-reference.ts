import { fetchUtils } from 'ra-core';
import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider';
import {
  GetManyReferenceParams,
  GetManyReferenceResult,
  RaRecord,
} from 'react-admin';

export const getManyReference =
  (httpClient: HttpClient) =>
  async <RecordType extends RaRecord = any>(
    resource: string,
    params: GetManyReferenceParams,
  ): Promise<GetManyReferenceResult<RecordType>> => {
    const { page, perPage } = params.pagination;
    const { field, order } = params.sort;

    const query = {
      sort: field,
      order: order,
      page: page,
      perPage: perPage,
      [params.target]: params.id,
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
