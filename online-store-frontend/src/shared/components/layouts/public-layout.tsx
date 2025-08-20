import {Package} from 'lucide-react';
import {ComponentType, SVGProps, useEffect, useState} from 'react';
import {NavLink, useNavigate} from 'react-router';

import logo from '@/assets/logo.svg';
import {paths} from '@/config/paths';
import {Button} from '@/shared/components/ui/button';
import {cn} from '@/shared/utils/cn';
import {Link} from '../ui/link';

type SideNavigationItem = {
  name: string;
  to: string;
  icon: ComponentType<SVGProps<SVGSVGElement>>;
};

const Logo = () => {
  return (
    <Link className="flex items-center text-white" to={paths.products.getHref()}>
      <img className="h-8 w-auto" src={logo} alt="Workflow"/>
      <span className="text-sm font-semibold text-white">Nico AI</span>
    </Link>
  );
};

const Progress = () => {
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    const timer = setInterval(() => {
      setProgress((oldProgress) => {
        if (oldProgress === 100) {
          clearInterval(timer);
          return 100;
        }
        const newProgress = oldProgress + 10;
        return newProgress > 100 ? 100 : newProgress;
      });
    }, 300);

    return () => {
      clearInterval(timer);
    };
  }, []);

  return (
    <div
      className="fixed left-0 top-0 h-1 bg-blue-500 transition-all duration-200 ease-out"
      style={{width: `${progress}%`}}
    ></div>
  );
};

export function PublicLayout({children}: { children: React.ReactNode }) {
  const navigate = useNavigate();

  const navigation = [
    {name: 'Catalog', to: paths.products.getHref(), icon: Package},
  ];

  return (
    <div className="flex min-h-screen w-full flex-col bg-muted/40">
      <div className="flex flex-col sm:gap-4 sm:py-4">
        <header
          className="sticky top-0 z-30 flex h-14 items-center justify-between gap-4 border-b bg-background px-4 sm:static sm:h-auto sm:justify-between sm:border-0 sm:bg-transparent sm:px-6">
          <Progress/>

          <div className="flex items-center">
            <Logo/>
          </div>

          <nav className="hidden sm:flex items-center gap-4">
            {navigation.map((item) => (
              <NavLink
                key={item.name}
                to={item.to}
                end
                className={({isActive}) =>
                  cn(
                    isActive
                      ? 'text-white bg-gray-800'
                      : 'text-gray-300 hover:text-white hover:bg-gray-700',
                    'px-3 py-2 rounded-md text-sm font-medium transition-colors'
                  )
                }
              >
                {item.name}
              </NavLink>
            ))}
          </nav>

          <Button variant={"outline"} onClick={() => navigate(paths.auth.login.getHref())}>
            Login
          </Button>
        </header>

        <main className="grid flex-1 items-start gap-4 p-4 sm:px-6 sm:py-0 md:gap-8">
          {children}
        </main>
      </div>
    </div>
  );
}
