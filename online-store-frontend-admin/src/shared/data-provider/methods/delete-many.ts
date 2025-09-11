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
    const url = `${endpoints.delete(resource, '')}`.replace(/\/$/, ''); // Убираем последний слеш
    const { json } = await httpClient<{ data: Identifier[] }>(url, {
      method: 'DELETE',
      body: JSON.stringify(params.ids),
    });
    return json;
  };
