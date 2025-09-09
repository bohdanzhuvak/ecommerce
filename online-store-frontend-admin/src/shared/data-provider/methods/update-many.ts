import { endpoints } from '../../api/endpoints.ts';

export const updateMany =
  (httpClient: any) => async (resource: string, params: any) => {
    const responses = await Promise.all(
      params.ids.map((id: string | number) =>
        httpClient(`${endpoints.update(resource, id)}`, {
          method: 'PUT',
          body: JSON.stringify(params.data),
        }),
      ),
    );

    return { data: responses.map(({ json }) => json.id) };
  };
