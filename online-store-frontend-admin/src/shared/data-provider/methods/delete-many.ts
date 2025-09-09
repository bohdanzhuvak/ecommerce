import { endpoints } from '../../api/endpoints.ts';

export const deleteMany =
  (httpClient: any) => async (resource: string, params: any) => {
    const responses = await Promise.all(
      params.ids.map((id: string | number) =>
        httpClient(`${endpoints.delete(resource, id)}`, {
          method: 'DELETE',
        }),
      ),
    );

    return { data: responses.map(({ json }) => json.id) };
  };
