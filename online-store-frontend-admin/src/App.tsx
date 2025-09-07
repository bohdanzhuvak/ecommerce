import {
  Admin,
  EditGuesser,
  ListGuesser,
  Resource,
  ShowGuesser,
} from 'react-admin';
import { Layout } from './Layout';
import dataProvider from './providers/data-provider.ts';
import { authProvider } from './providers/auth-provider.ts';
import { OrderList } from './components/orders/OrderList';
import { OrderShow } from './components/orders/OrderShow';
import { ProductCreate } from './components/products/ProductCreate';
import { CategoryCreate } from './components/categories/CategoryCreate';
import { Route } from 'react-router';
import { UserList } from './components/users/UserList.tsx';
import { ProductEdit } from './components/products/ProductEdit.tsx';

export const App = () => (
  <Admin
    layout={Layout}
    dataProvider={dataProvider('http://localhost:8080/api/v1')}
    authProvider={authProvider}
    requireAuth
  >
    <Resource
      name="products"
      list={ListGuesser}
      create={ProductCreate}
      edit={ProductEdit}
      show={ShowGuesser}
    />
    <Resource
      name="users"
      list={UserList}
      edit={EditGuesser}
      show={ShowGuesser}
    >
      <Route path=":id/orders" element={<OrderList />} />
    </Resource>
    <Resource
      name="categories"
      list={ListGuesser}
      create={CategoryCreate}
      edit={EditGuesser}
      show={ShowGuesser}
    />
    <Resource name="orders" list={OrderList} show={OrderShow} />
  </Admin>
);
