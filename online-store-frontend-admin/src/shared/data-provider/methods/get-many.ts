import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider.ts';
import { GetManyParams, GetManyResult, RaRecord } from 'react-admin';

export const getMany =
  (httpClient: HttpClient) =>
  async <RecordType extends RaRecord = any>(
    resource: string,
    params: GetManyParams<RecordType>,
  ): Promise<GetManyResult<RecordType>> => {
    // Backend doesn't support filtering by IDs in list endpoint
    // So we fetch each resource individually using getOne
    const responses = await Promise.all(
      params.ids.map((id) =>
        httpClient<RecordType>(`${endpoints.one(resource, id)}`, {
          signal: params?.signal,
        }),
      ),
    );

    // Extract the data from responses
    return { data: responses.map(({ json }) => json) };
  };
