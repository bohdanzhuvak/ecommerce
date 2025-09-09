import { DataProvider } from 'ra-core';

import { getList } from './methods/get-list.ts';
import { getOne } from './methods/get-one.ts';
import { getMany } from './methods/get-many.ts';
import { getManyReference } from './methods/get-many-reference.ts';
import { update } from './methods/update.ts';
import { updateMany } from './methods/update-many.ts';
import { create } from './methods/create.ts';
import { deleteOne } from './methods/delete.ts';
import { deleteMany } from './methods/delete-many.ts';
import { httpClientWithAuth } from '../api/http-client.ts';

const httpClient = httpClientWithAuth;

export const dataProvider: DataProvider = {
  getList: getList(httpClient),
  getOne: getOne(httpClient),
  getMany: getMany(httpClient),
  getManyReference: getManyReference(httpClient),
  update: update(httpClient),
  updateMany: updateMany(httpClient),
  create: create(httpClient),
  delete: deleteOne(httpClient),
  deleteMany: deleteMany(httpClient),
};
