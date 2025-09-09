import { endpoints } from '../../api/endpoints.ts';

export const getOne =
  (httpClient: any) => async (resource: string, params: any) => {
    const url = `${endpoints.one(resource, params.id)}`;
    const { json } = await httpClient(url, { signal: params?.signal });
    return { data: json };
  };
