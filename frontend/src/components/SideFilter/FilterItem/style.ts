import styled from 'styled-components';

export const Wrapper = styled.div`
  border: 0.0625rem solid rgb(215, 226, 235);
  border-radius: 0.9375rem;
  margin-bottom: 1rem;
  overflow: hidden;
  padding-bottom: 0.5rem;
`;

export const FilterTitle = styled.div`
  font-size: 1rem;
  line-height: 1.4375rem;
  padding: 0.9375rem 1.625rem 0.9375rem 1.5rem;
  display: flex;
  -webkit-box-pack: justify;
  justify-content: space-between;
  cursor: pointer;
  font-weight: 700;
  background-color: rgb(252, 252, 252);

  @media (max-width: 1200px) {
    font-size: 14px;
  }
`;

export const CaretWrapper = styled.div``;

export const FilterBodyWrapper = styled.div`
  padding: 1.375rem 1.5rem 1rem;
  display: block;
  border-top: 0.0625rem solid rgb(215, 226, 235);
`;

export const FilterBody = styled.div`
  position: relative;
  padding: 0 0.5rem;
  transition: all 0.3s linear 0s;
`;

export const FilterOptionList = styled.ul`
  overflow: hidden;
  transition: height 0.2s ease-out;
  padding-left: 0;
`;

export const CostFilterOptionList = styled(FilterOptionList)`
  display: flex;
  justify-content: space-between;
  padding-right: 1.5rem;

  @media (max-width: 1200px) {
    flex-direction: column;
  }
`;

export const TestOptionList = styled(FilterOptionList)`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  padding-right: 4rem;

  @media (max-width: 1200px) {
    padding-right: 2rem;
  }
`;

type ButtonProps = {
  position: 'absolute' | 'relative' | 'fixed' | 'unset';
  paddingTop: string;
  paddingBottom: string;
};

export const MoreButton = styled.button<ButtonProps>`
  border: 0;
  cursor: pointer;
  display: flex;
  -webkit-box-align: center;
  align-items: center;
  -webkit-box-pack: center;
  justify-content: center;
  width: 100%;
  font-size: 0.875rem;
  line-height: 1.0625rem;
  background: linear-gradient(rgba(255, 255, 255, 0) 0%, rgb(255, 255, 255) 100%);
  color: rgb(0, 120, 255);
  position: ${props => props.position};
  padding-top: ${props => props.paddingTop};
  top: 4.75rem;
  padding-bottom: ${props => props.paddingBottom};
  padding-right: 1rem;
  bottom: -1rem;
`;

export const CaretUp = styled.svg`
  margin-left: 0.25rem;
  overflow: hidden;
  vertical-align: middle;
  transform: rotate(180deg);
`;

export const CaretDown = styled(CaretUp)`
  transform: rotate(0deg);
`;
