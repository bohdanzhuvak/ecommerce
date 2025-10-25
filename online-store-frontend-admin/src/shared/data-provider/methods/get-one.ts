import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider';
import { GetOneParams, GetOneResult, RaRecord } from 'react-admin';

export const getOne =
  (httpClient: HttpClient) =>
  async <RecordType extends RaRecord = any>(
    resource: string,
    params: GetOneParams<RecordType>,
  ): Promise<GetOneResult<RecordType>> => {
    const url = `${endpoints.one(resource, params.id)}`;
    const { json } = await httpClient<RecordType>(url, {
      signal: params?.signal,
    });

    // Wrap the response in { data: ... } format expected by react-admin
    return { data: json };
  };
