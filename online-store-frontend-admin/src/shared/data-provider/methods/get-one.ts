import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider';
import { GetOneParams } from 'ra-core';

export const getOne =
  (httpClient: HttpClient) =>
  async (resource: string, params: GetOneParams) => {
    const url = `${endpoints.one(resource, params.id)}`;
    const { json } = await httpClient(url, { signal: params?.signal });
    return { data: json };
  };
