import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider';
import { CreateParams } from 'ra-core';

export const create =
  (httpClient: HttpClient) =>
  async (resource: string, params: CreateParams) => {
    const url = `${endpoints.create(resource)}`;
    const { json } = await httpClient(url, {
      method: 'POST',
      body: JSON.stringify(params.data),
    });
    return { data: json };
  };
