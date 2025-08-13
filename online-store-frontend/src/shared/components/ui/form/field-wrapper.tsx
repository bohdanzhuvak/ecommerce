import * as React from 'react';
import {type FieldError, type FieldErrorsImpl, type Merge} from 'react-hook-form';

import {Error} from './error';
import {Label} from './label';

type FieldWrapperProps = {
  label?: string;
  className?: string;
  children: React.ReactNode;
  error?: FieldError | Merge<FieldError, FieldErrorsImpl<any>> | undefined;
};

export type FieldWrapperPassThroughProps = Omit<
  FieldWrapperProps,
  'className' | 'children'
>;

export const FieldWrapper = (props: FieldWrapperProps) => {
  const {label, error, children} = props;
  return (
    <div>
      <Label>
        {label}
        <div className="mt-1">{children}</div>
      </Label>
      <Error errorMessage={(typeof error === 'string' ? error : error?.message) as string | null | undefined}/>
    </div>
  );
};
