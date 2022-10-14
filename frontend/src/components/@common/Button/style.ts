import styled from "styled-components";
import {ButtonProps} from "./index"


export const ButtonStyle = styled.button<ButtonProps>`
    display: inline-block;
    padding: 0.4375rem 0.8125rem;
    font-size: 1rem;
    line-height: 1.5rem;
    border: 1px solid transparent;
    background-color: transparent;
    color: #0078FF;
    cursor: pointer;
    font-weight: 500;
    text-align: center;
    vertical-align: middle;
    user-select: none;
    border-radius: 0.25rem;
    transition: color .08s ease-in-out, background-color .08s ease-in-out, border-color .08s ease-in-out, box-shadow .08s ease-in-out;
    -webkit-appearance: button;
    background: none;
    overflow: visible;
    
    ${(p) => p.primary && `
        border-color: #0078FF;
        background-color: #0078FF;
        color: white;
    `}
    
    ${(p) => p.outlineLight && `
        color: #f8f9fa;
        border-color: #f8f9fa;
    `}
    
    @media (max-width: 767px) {
        padding: 0.3125rem 0.8125rem;
        font-size: 0.875rem;
        line-height: 1.25rem;
    }
`;