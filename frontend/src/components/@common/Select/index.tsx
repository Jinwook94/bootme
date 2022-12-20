import { SelectStyle } from './style';

const Select = ({ children }: SelectProps) => {
  return <SelectStyle>{children}</SelectStyle>;
};

interface SelectProps {
  children: JSX.Element | JSX.Element[];
}

export default Select;
