import * as React from 'react';

import logo from '@/assets/logo.svg';
import {paths} from '@/config/paths';
import {Head} from '@/shared/components/seo';
import {Link} from '@/shared/components/ui/link';


type LayoutProps = {
  children: React.ReactNode;
};

export const AuthLayout = ({children}: LayoutProps) => {
  return (
    <>
      <Head/>
      <div className="flex min-h-screen flex-col justify-center bg-gray-50 py-12 sm:px-6 lg:px-8">
        <div className="sm:mx-auto sm:w-full sm:max-w-md">
          <div className="flex justify-center">
            <Link
              className="flex items-center text-white"
              to={paths.home.getHref()}
            >
              <img className="h-24 w-auto" src={logo} alt="Workflow"/>
            </Link>
          </div>
        </div>

        <div className="mt-8 sm:mx-auto sm:w-full sm:max-w-md">
          <div className="bg-white px-4 py-8 shadow sm:rounded-lg sm:px-10">
            {children}
          </div>
        </div>
      </div>
    </>
  );
};
