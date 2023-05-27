import styled from 'styled-components';

export const BodyWrapper = styled.div`
  padding-top: 0.5rem;
  padding-bottom: 5rem;
  display: flex;
  flex-direction: column;
  background: #f9fafb;
  min-height: 41rem;

  @media (max-width: 991px) {
    padding-bottom: 10.6875rem;
  }
`;

export const BodyWrapper2 = styled.div`
  display: flex;
  flex-direction: column;
  -webkit-box-pack: justify;
  justify-content: space-between;
  align-items: flex-start;
  max-width: 72rem;
  margin: 0 auto;
`;

export const BodyTitle = styled.p`
  font-size: 1.5rem;
  line-height: 1.5;
  font-weight: 700;
  margin-top: 1.5rem;
  margin-bottom: 0.5rem;
`;

export const CourseListWrapper = styled.section`
  flex: 1 1 auto;
  display: flex;
  flex-direction: column;

  @media (max-width: 575px) {
    grid-template-columns: repeat(1, minmax(0px, 1fr));
  }

  @media (max-width: 991px) {
    grid-template-columns: repeat(2, minmax(0px, 1fr));
    width: 100%;
  }
`;
