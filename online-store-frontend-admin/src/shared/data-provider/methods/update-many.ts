import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider';
import {
  Identifier,
  RaRecord,
  UpdateManyParams,
  UpdateManyResult,
} from 'react-admin';

export const updateMany =
  (httpClient: HttpClient) =>
  async <RecordType extends RaRecord = any>(
    resource: string,
    params: UpdateManyParams<RecordType>,
  ): Promise<UpdateManyResult<RecordType>> => {
    const responses = await Promise.all(
      params.ids.map((id: Identifier) =>
        httpClient<RecordType>(`${endpoints.update(resource, id)}`, {
          method: 'PUT',
          body: JSON.stringify(params.data),
        }),
      ),
    );

    // Extract IDs from the updated records
    return {
      data: responses.map(({ json }) => json.id),
    };
  };
