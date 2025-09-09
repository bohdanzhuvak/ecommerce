import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider';
import { UpdateManyParams } from 'ra-core';

export const updateMany =
  (httpClient: HttpClient) =>
  async (resource: string, params: UpdateManyParams) => {
    const responses = await Promise.all(
      params.ids.map((id: string | number) =>
        httpClient(`${endpoints.update(resource, id)}`, {
          method: 'PUT',
          body: JSON.stringify(params.data),
        }),
      ),
    );

    return {
      data: responses.map(({ json }) => json.id || json),
    };
  };
