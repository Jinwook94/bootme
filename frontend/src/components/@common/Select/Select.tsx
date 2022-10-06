import { SelectStyle} from "./Select.styles";


const Select = ({children}: SelectProps) => {
    return(
        <SelectStyle>
            {children}
        </SelectStyle>
    )
};

interface SelectProps {
    children: JSX.Element|JSX.Element[];
}

export default Select;