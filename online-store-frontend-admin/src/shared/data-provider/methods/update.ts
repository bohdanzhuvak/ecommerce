import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider';
import { UpdateParams } from 'ra-core';

export const update =
  (httpClient: HttpClient) =>
  async (resource: string, params: UpdateParams) => {
    const url = `${endpoints.update(resource, params.id)}`;
    const { json } = await httpClient(url, {
      method: 'PUT',
      body: JSON.stringify(params.data),
    });
    return { data: json };
  };
