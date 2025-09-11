import { fetchUtils } from 'ra-core';
import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider.ts';
import { GetManyParams, GetManyResult, RaRecord } from 'react-admin';

export const getMany =
  (httpClient: HttpClient) =>
  async <RecordType extends RaRecord = any>(
    resource: string,
    params: GetManyParams<RecordType>,
  ): Promise<GetManyResult<RecordType>> => {
    const query = { id: params.ids };
    const url = `${endpoints.list(resource, fetchUtils.queryParameters(query))}`;
    const { json } = await httpClient<
      { data: RecordType[]; total: number } | RecordType[]
    >(url, {
      signal: params?.signal,
    });

    if (json && typeof json === 'object' && 'data' in json) {
      return { data: json.data };
    }

    return { data: Array.isArray(json) ? json : [json] };
  };
