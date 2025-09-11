import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider.ts';
import { DeleteParams, DeleteResult, RaRecord } from 'react-admin';

export const deleteOne =
  (httpClient: HttpClient) =>
  async <RecordType extends RaRecord = any>(
    resource: string,
    params: DeleteParams<RecordType>,
  ): Promise<DeleteResult<RecordType>> => {
    const url = `${endpoints.delete(resource, params.id)}`;
    const { json } = await httpClient<{ data: RecordType }>(url, {
      method: 'DELETE',
    });
    return json;
  };
