import { endpoints } from '../../api/endpoints.ts';
import { HttpClient } from '../../../types/data-provider.ts';
import { CreateParams, CreateResult, Identifier, RaRecord } from 'react-admin';

export const create =
  (httpClient: HttpClient) =>
  async <
    RecordType extends Omit<RaRecord, 'id'> = any,
    ResultRecordType extends RaRecord = RecordType & { id: Identifier },
  >(
    resource: string,
    params: CreateParams<RecordType>,
  ): Promise<CreateResult<ResultRecordType>> => {
    const url = `${endpoints.create(resource)}`;
    const { json } = await httpClient<ResultRecordType>(url, {
      method: 'POST',
      body: JSON.stringify(params.data),
    });

    // Wrap the response in { data: ... } format expected by react-admin
    return { data: json };
  };
