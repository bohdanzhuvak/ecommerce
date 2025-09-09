import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider';
import { DeleteManyParams } from 'ra-core';

export const deleteMany =
  (httpClient: HttpClient) =>
  async (resource: string, params: DeleteManyParams) => {
    const responses = await Promise.all(
      params.ids.map((id: string | number) =>
        httpClient(`${endpoints.delete(resource, id)}`, {
          method: 'DELETE',
        }),
      ),
    );

    return {
      data: responses.map(({ json }) => json.id || json),
    };
  };
