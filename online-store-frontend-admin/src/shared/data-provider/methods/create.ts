import { endpoints } from '../../api/endpoints.ts';

export const create =
  (httpClient: any) => async (resource: string, params: any) => {
    const url = `${endpoints.create(resource)}`;
    const { json } = await httpClient(url, {
      method: 'POST',
      body: JSON.stringify(params.data),
    });
    return { data: json };
  };
