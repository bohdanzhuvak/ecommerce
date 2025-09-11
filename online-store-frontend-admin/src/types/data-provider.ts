import { Options } from 'react-admin';

export interface HttpClientOptions extends Options {
  method?: string;
  body?: string;
  headers?: HeadersInit;
  signal?: AbortSignal;
}

export interface HttpClientResponse<T = any> {
  json: T;
  status: number;
  headers: Headers;
}

export type HttpClient = <T = any>(
  url: string,
  options?: HttpClientOptions,
) => Promise<HttpClientResponse<T>>;

export interface ListQuery {
  sort: string;
  order: 'ASC' | 'DESC';
  page: number;
  perPage: number;
  filter?: string;
}
