import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider';
import { RaRecord, UpdateParams, UpdateResult } from 'react-admin';

export const update =
  (httpClient: HttpClient) =>
  async <RecordType extends RaRecord = any>(
    resource: string,
    params: UpdateParams<RecordType>,
  ): Promise<UpdateResult<RecordType>> => {
    const url = `${endpoints.update(resource, params.id)}`;
    const { json } = await httpClient<{ data: RecordType }>(url, {
      method: 'PUT',
      body: JSON.stringify(params.data),
    });
    return json;
  };
