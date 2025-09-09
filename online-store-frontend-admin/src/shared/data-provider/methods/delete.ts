import { endpoints } from '../../api/endpoints.ts';

export const deleteOne =
  (httpClient: any) => async (resource: string, params: any) => {
    const url = `${endpoints.delete(resource, params.id)}`;
    const { json } = await httpClient(url, { method: 'DELETE' });
    return { data: json };
  };
