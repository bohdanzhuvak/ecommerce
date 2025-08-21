import {Package, ShoppingCart, User2} from 'lucide-react';
import {ComponentType, SVGProps, useEffect, useState} from 'react';
import {NavLink, useNavigate, useNavigation} from 'react-router';

import logo from '@/assets/logo.svg';
import {paths} from '@/config/paths';
import {Button} from '@/shared/components/ui/button';
import {cn} from '@/shared/utils/cn';

import {
  DropdownMenu,
  DropdownMenuContent,
  DropdownMenuItem,
  DropdownMenuSeparator,
  DropdownMenuTrigger,
} from '../ui/dropdown';
import {Link} from '../ui/link';
import {useAuth} from "@/shared/lib/auth";

type NavigationItem = {
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
  const {state, location} = useNavigation();
  const [progress, setProgress] = useState(0);

  useEffect(() => {
    setProgress(0);
  }, [location?.pathname]);

  useEffect(() => {
    if (state === 'loading') {
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
    }
  }, [state]);

  if (state !== 'loading') {
    return null;
  }

  return (
    <div
      className="fixed left-0 top-0 h-1 bg-blue-500 transition-all duration-200 ease-out"
      style={{width: `${progress}%`}}
    ></div>
  );
};

export function UserLayout({children}: { children: React.ReactNode }) {
  const navigate = useNavigate();

  const navigation = [
    {name: 'Catalog', to: paths.products.getHref(), icon: Package},
    {name: 'Cart', to: paths.app.cart.getHref(), icon: ShoppingCart},
    {name: 'Orders', to: paths.app.orders.getHref(), icon: Package},
  ];

  return (
    <div className="flex min-h-screen w-full flex-col bg-muted/40">
      <div className="flex flex-col sm:gap-4 sm:py-4">
        <header
          className="sticky top-0 z-30 flex h-14 items-center justify-between gap-4 border-b bg-background px-4 sm:static sm:h-auto sm:justify-between sm:border-0 sm:bg-transparent sm:px-6">
          <Progress/>

          {/* Logo */}
          <div className="flex items-center">
            <Logo/>
          </div>

          {/* Desktop Navigation */}
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
                <item.icon className="size-4 mr-2 inline"/>
                {item.name}
              </NavLink>
            ))}
          </nav>

          {/* User menu */}
          <DropdownMenu>
            <DropdownMenuTrigger asChild>
              <Button
                variant="outline"
                size="icon"
                className="overflow-hidden rounded-full"
              >
                <span className="sr-only">Open user menu</span>
                <User2 className="size-6 rounded-full"/>
              </Button>
            </DropdownMenuTrigger>
            <DropdownMenuContent align="end">
              <DropdownMenuItem
                onClick={() => navigate(paths.app.profile.getHref())}
                className={cn('block px-4 py-2 text-sm text-gray-700')}
              >
                Your Profile
              </DropdownMenuItem>
             <DropdownMenuSeparator/>
              <DropdownMenuItem
                className={cn('block px-4 py-2 text-sm text-gray-700 w-full')}
                onClick={async () => await useAuth().logout()}
              >
                Sign Out
              </DropdownMenuItem>
            </DropdownMenuContent>
          </DropdownMenu>
        </header>

        <main className="grid flex-1 items-start gap-4 p-4 sm:px-6 sm:py-0 md:gap-8">
          {children}
        </main>
      </div>
    </div>
  );
}
