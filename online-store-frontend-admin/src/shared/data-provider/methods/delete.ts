import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider';
import { DeleteParams } from 'ra-core';

export const deleteOne =
  (httpClient: HttpClient) =>
  async (resource: string, params: DeleteParams) => {
    const url = `${endpoints.delete(resource, params.id)}`;
    const { json } = await httpClient(url, { method: 'DELETE' });
    return { data: json };
  };
