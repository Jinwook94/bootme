import { ButtonStyle } from "./Button.styles";
import React from "react";

const Button = ({
  children,
  type = 'button',
  disabled = false,
  color,
  onClick,
  ...props
}: ButtonProps) => {
  return (
    <ButtonStyle disabled={disabled} onClick={onClick} color={color} {...props}>
      {children}
    </ButtonStyle>
  );
};

interface ButtonProps {
  children: string;
  type?: string;
  disabled?: boolean;
  onClick?: React.MouseEventHandler<HTMLButtonElement>;
  color?: string;
  [props: string]: any;
}

export default Button;
