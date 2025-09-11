import {
  Admin,
  EditGuesser,
  ListGuesser,
  Resource,
  ShowGuesser,
} from 'react-admin';
import {
  Category as CategoriesIcon,
  People as UsersIcon,
  Receipt as OrdersIcon,
  ShoppingCart as ProductsIcon,
} from '@mui/icons-material';
import { Layout } from './Layout';
import { OrderList } from './components/orders/OrderList';
import { OrderShow } from './components/orders/OrderShow';
import { ProductCreate } from './components/products/ProductCreate';
import { CategoryCreate } from './components/categories/CategoryCreate';
import { Route } from 'react-router';
import { UserList } from './components/users/UserList.tsx';
import { ProductEdit } from './components/products/ProductEdit.tsx';
import { authProvider } from './shared/auth';
import { dataProvider } from './shared/data-provider';
import { ProductList } from './components/products/ProductList.tsx';

export const App = () => (
  <Admin
    layout={Layout}
    dataProvider={dataProvider}
    authProvider={authProvider}
    requireAuth
  >
    <Resource
      name="products"
      list={ProductList}
      create={ProductCreate}
      edit={ProductEdit}
      show={ShowGuesser}
      icon={ProductsIcon}
    />
    <Resource
      name="users"
      list={UserList}
      edit={EditGuesser}
      show={ShowGuesser}
      icon={UsersIcon}
    >
      <Route path=":id/orders" element={<OrderList />} />
    </Resource>
    <Resource
      name="categories"
      list={ListGuesser}
      create={CategoryCreate}
      edit={EditGuesser}
      show={ShowGuesser}
      icon={CategoriesIcon}
    />
    <Resource
      name="orders"
      list={OrderList}
      show={OrderShow}
      icon={OrdersIcon}
    />
  </Admin>
);
