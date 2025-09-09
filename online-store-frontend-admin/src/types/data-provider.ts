export interface HttpClientOptions {
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

export interface SpringPageResponse<T = any> {
  content: T[];
  totalElements: number;
  totalPages: number;
  size: number;
  number: number;
  first: boolean;
  last: boolean;
  numberOfElements: number;
}

export interface SpringQueryParams {
  page: number;
  size: number;
  sort?: string;
  [key: string]: any;
}
