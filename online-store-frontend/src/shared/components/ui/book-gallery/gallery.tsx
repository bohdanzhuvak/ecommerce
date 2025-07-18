import * as React from 'react';
import {cn} from '@/shared/utils/cn';

export const BooksContainer = React.forwardRef<
  HTMLDivElement,
  React.HTMLAttributes<HTMLDivElement>
>(({className, ...props}, ref) => (
  <div
    ref={ref}
    className={cn(
      'grid grid-cols-1 gap-6 sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 xl:grid-cols-5',
      className
    )}
    {...props}
  />
));
BooksContainer.displayName = 'BooksContainer';

export const BookCard = React.forwardRef<
  HTMLDivElement,
  React.HTMLAttributes<HTMLDivElement> & {
  actionSlot?: React.ReactNode;
}>(({className, children, actionSlot, ...props}, ref) => (
  <div
    ref={ref}
    className={cn(
      'overflow-hidden rounded-lg border bg-white shadow-sm transition-all hover:shadow-md',
      className
    )}
    {...props}
  >
    {children}
    {actionSlot && (
      <div className="border-t p-3">
        {actionSlot}
      </div>
    )}
  </div>
));
BookCard.displayName = 'BookCard';

export const BookCover = React.forwardRef<
  HTMLDivElement,
  React.HTMLAttributes<HTMLDivElement>
>(({className, ...props}, ref) => (
  <div
    ref={ref}
    className={cn(
      'aspect-[2/3] w-full overflow-hidden bg-gray-200 flex items-center justify-center',
      className
    )}
    {...props}
  />
));
BookCover.displayName = 'BookCover';

export const BookInfo = React.forwardRef<
  HTMLDivElement,
  React.HTMLAttributes<HTMLDivElement>
>(({className, ...props}, ref) => (
  <div
    ref={ref}
    className={cn('p-4 space-y-2', className)}
    {...props}
  />
));
BookInfo.displayName = 'BookInfo';
