import { endpoints } from '../../api/endpoints.ts';

export const update =
  (httpClient: any) => async (resource: string, params: any) => {
    const url = `${endpoints.update(resource, params.id)}`;
    const { json } = await httpClient(url, {
      method: 'PUT',
      body: JSON.stringify(params.data),
    });
    return { data: json };
  };
