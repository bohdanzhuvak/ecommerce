import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider.ts';
import {
  DeleteManyParams,
  DeleteManyResult,
  Identifier,
  RaRecord,
} from 'react-admin';

export const deleteMany =
  (httpClient: HttpClient) =>
  async <RecordType extends RaRecord = any>(
    resource: string,
    params: DeleteManyParams<RecordType>,
  ): Promise<DeleteManyResult<RecordType>> => {
    // Backend doesn't support batch delete, so delete one by one
    await Promise.all(
      params.ids.map((id: Identifier) =>
        httpClient<void>(`${endpoints.delete(resource, id)}`, {
          method: 'DELETE',
        }),
      ),
    );

    // React-admin expects { data: Identifier[] } with deleted IDs
    return { data: params.ids };
  };
