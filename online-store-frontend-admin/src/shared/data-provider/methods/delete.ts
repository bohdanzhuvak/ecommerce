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

    // Backend returns 204 No Content, so we ignore the response
    await httpClient<void>(url, {
      method: 'DELETE',
    });

    // React-admin expects { data: ... } with the deleted record
    // Since backend returns no content, we return the previousData from params
    return { data: params.previousData as RecordType };
  };
