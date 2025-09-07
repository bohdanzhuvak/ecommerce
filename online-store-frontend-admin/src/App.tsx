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
      edit={EditGuesser}
      show={ShowGuesser}
    />
    <Resource
      name="users"
      list={ListGuesser}
      edit={EditGuesser}
      show={ShowGuesser}
    />
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
