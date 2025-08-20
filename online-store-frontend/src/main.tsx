import * as React from 'react';
import {createRoot} from 'react-dom/client';

import './index.css';
import {App} from './app';
import {AuthProvider} from '@/shared/lib/auth';

const root = document.getElementById('root');
if (!root) throw new Error('No root element found');

createRoot(root).render(
  <React.StrictMode>
    <AuthProvider>
      <App/>
    </AuthProvider>
  </React.StrictMode>,
);
