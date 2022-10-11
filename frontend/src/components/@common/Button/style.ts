import styled from "styled-components";

export const ButtonStyle = styled.button`
    -webkit-text-size-adjust: 100%;
    -webkit-tap-highlight-color: transparent;
    --breakpoint-xs: 0;
    --breakpoint-sm: 576px;
    --breakpoint-md: 768px;
    --breakpoint-lg: 992px;
    --breakpoint-xl: 1200px;
    --swiper-theme-color: #007aff;
    --swiper-navigation-size: 44px;
    word-break: keep-all;
    word-wrap: break-word;
    -webkit-font-smoothing: antialiased;
    box-sizing: border-box;
    font: inherit;
    margin: 0;
    font-family: inherit;
    overflow: visible;
    text-transform: none;
    background: none;
    -webkit-appearance: button;
    display: inline-block;
    text-align: center;
    vertical-align: middle;
    user-select: none;
    border-radius: 0.25rem;
    border: 1px solid transparent;
    font-weight: 500;
    transition: color .08s ease-in-out, background-color .08s ease-in-out, border-color .08s ease-in-out, box-shadow .08s ease-in-out;
    padding: 0.3125rem 0.8125rem;
    font-size: 0.875rem;
    line-height: 1.25rem;
    border-color: #0078FF;
    background-color: #0078FF;
    color: white;
    max-height: 1.9375rem;
    cursor: pointer;
    
    @media (max-width: 767px) {
    padding: 0.3125rem 0.8125rem;
    font-size: 0.875rem;
    line-height: 1.25rem;
}
`;