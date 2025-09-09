import { stringify } from 'query-string';
import { endpoints } from '../../api/endpoints.ts';
import { HttpClient, SpringPageResponse } from '../../../types/data-provider';
import { GetManyParams } from 'ra-core';

export const getMany =
  (httpClient: HttpClient) =>
  async (resource: string, params: GetManyParams) => {
    const query = { filter: JSON.stringify({ id: params.ids }) };
    const url = `${endpoints.list(resource, stringify(query))}`;
    const { json } = await httpClient<SpringPageResponse | any[]>(url, {
      signal: params?.signal,
    });

    if (json && typeof json === 'object' && 'content' in json) {
      return { data: (json as SpringPageResponse).content };
    }

    return { data: Array.isArray(json) ? json : [json] };
  };
