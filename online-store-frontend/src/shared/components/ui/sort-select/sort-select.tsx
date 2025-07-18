import {useSearchParams} from 'react-router';
import {Select, SelectContent, SelectItem, SelectTrigger, SelectValue} from './select';
import {cn} from "@/shared/utils/cn";

interface SortSelectProps {
  options: { value: string; label: string }[];
  defaultValue?: string;
  paramName?: string;
  className?: string;
}

export const SortSelect = ({
                             options,
                             defaultValue = '',
                             paramName = 'sort',
                             className,
                           }: SortSelectProps) => {
  const [searchParams, setSearchParams] = useSearchParams();

  const handleValueChange = (value: string) => {
    const newSearchParams = new URLSearchParams(searchParams);
    if (value) {
      newSearchParams.set(paramName, value);
    } else {
      newSearchParams.delete(paramName);
    }
    setSearchParams(newSearchParams);
  };

  return (
    <Select
      value={searchParams.get(paramName) || defaultValue}
      onValueChange={handleValueChange}
    >
      <SelectTrigger className={cn('w-full min-w-[200px]', className)}>
        <SelectValue placeholder="Sort by..."/>
      </SelectTrigger>
      <SelectContent>
        {options.map((option) => (
          <SelectItem key={option.value} value={option.value}>
            {option.label}
          </SelectItem>
        ))}
      </SelectContent>
    </Select>
  );
};
