import { stringify } from 'query-string';
import { endpoints } from '../../api/endpoints.ts';

export const getMany =
  (httpClient: any) => async (resource: string, params: any) => {
    const query = { filter: JSON.stringify({ id: params.ids }) };
    const url = `${endpoints.list(resource, stringify(query))}`;
    const { json } = await httpClient(url, { signal: params?.signal });

    return { data: json.content ?? json };
  };
