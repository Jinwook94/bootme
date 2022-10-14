import styled from "styled-components";
import Button from "../../components/@common/Button";

export const MainWrapper = styled.div`
    min-height: calc(100vh - 3.125rem - 24.625rem - 5rem);
    box-sizing: border-box;
    font-size: 100%;
    vertical-align: baseline;
`

export const Wrapper = styled.div`
    max-width: 100%;
    padding-right: 16px;
    padding-left: 16px;
    margin-right: auto;
    margin-left: auto;
    width: 100%;
    
    @media (min-width: 576px){
    max-width: 540px;
    }
    
    @media (min-width: 768px){
    max-width: 720px;
    }

    @media (min-width: 992px){
    max-width: 960px;
    }
    
    @media (min-width: 1200px){
    max-width: 1200px !important;
    }
`

export const CourseListHeader = styled.div`
    display: flex;
    align-items: center;
    justify-content: space-between;
    padding: 1rem 0;
    vertical-align: middle;
    
    @media (max-width: 575px){
        align-items: center;
        padding: 1.5rem 0;
    }
`

export const HeaderLeft = styled.div``

export const CourseCount = styled.h6`
    display: block;
    margin-bottom: 0.25rem;
    font-weight: 700;
    vertical-align: top;
    line-height: 1.5;
    margin-top: 0;
    
    @media (max-width: 767px){
    font-size: 13px;
    }
`

export const HeaderRight = styled.div``

export const FilterButton = styled(Button)`
    display: inline-flex;
    align-items:center;
    max-height: 1.9375rem;
    font-size: 15px;
    
    span{
        display: inline-block;
        vertical-align: middle;
    }
    
    @media (max-width: 767px){
        font-size: 14px;
    }
`

export const FilterSelect = styled.select`
    -webkit-appearance:none;
    -moz-appearance: none;
    background-image: url(https://career.programmers.co.kr/assets/toggle-black-3ebb19a240c1ef57dac0b24e19fd00eff32a7e32ff8f2b87cfa2eb399c193c3a.png);
    background-position: calc(100% - 0.5rem) 49%;
    background-repeat: no-repeat;
    background-size: 0.625rem 0.3125rem;
    border: 1px solid transparent;
    border-color: #D7E2EB;
    border-radius: 0.25rem;
    color: #263747;
    padding: 0.3125rem 0.8125rem;
    padding-right: 1.5rem;
    font-size: 0.875rem;
    font-weight: 500;
    line-height: 1.25rem;
    background-color: transparent;
    display: inline-block;
    text-align: center;
    vertical-align: middle;
    user-select: none;
    word-wrap: normal;
    text-transform: none;
    margin: 0;
    margin-left: 4.5px;
    
    @media (max-width: 767px){
    font-size: 0.8125rem;
    line-height: 1.125rem;
    }
`

export const CourseList = styled.ul`
    display: flex;
    flex-wrap: wrap;
    padding: 0;
    margin: 0;
    font-size: 0;
    font-weight: 500;
    list-style: none;
`

export const PaginationWrapper = styled.div`
    display: flex;
    flex-direction: row;
    flex-wrap: nowrap;
    align-items: center;
    justify-content: center;
`

export const Pagination = styled.ul`
    display: flex;
    justify-content: center;
    list-style: none;
    padding-left: 0;
    margin: 0;
    border-radius: 0.25rem;
    
    @media (max-width: 575px){
        margin-top: calc(2.5rem - ((2.5rem / 8) * 4)) !important;
    }
`
export const PageItem = styled.li`
    margin-top: 0;
    letter-spacing: -0.009em;
    line-height: 1.6;
    list-style: none;
    
    span{
        z-index: 3;
        background-color: #0078FF;
        color: white;
        cursor: default;
    } 
    
    @media (max-width: 767px){
    font-size: 15px;
    }
`

export const FooterWrapper = styled.div``

export const Footer = styled.div``