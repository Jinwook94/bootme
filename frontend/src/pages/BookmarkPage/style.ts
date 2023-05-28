import styled from 'styled-components';

export const BodyWrapper = styled.div`
  padding-top: 0.5rem;
  padding-bottom: 2rem;
  display: flex;
  flex-direction: column;
  background: #f9fafb;
  min-height: 41rem;
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
  margin-top: 0.5rem;
  margin-bottom: 0.5rem;
  padding-left: 1rem;
`;

export const CourseListWrapper = styled.section`
  flex: 1 1 auto;
  display: flex;
  flex-direction: column;
  padding: 0 0.625rem;
  min-width: 970px;

  @media (max-width: 575px) {
    grid-template-columns: repeat(1, minmax(0px, 1fr));
    min-width: 0;
  }

  @media (max-width: 991px) {
    grid-template-columns: repeat(2, minmax(0px, 1fr));
    min-width: 0;
    width: 100%;
  }
`;

export const NoResultsMessage = styled.div`
  font-weight: 700;
  margin-top: 5rem;
  width: 884px;
  text-align: center;
  font-size: 1.2rem;

  @media (max-width: 575px) {
    width: 100%;
    font-size: 0.9375rem;
  }

  @media (min-width: 575px) and (max-width: 768px) {
    width: 508px;
    font-size: 0.9375rem;
  }

  @media (min-width: 768px) and (max-width: 991px) {
    width: 464px;
    font-size: 1rem;
  }

  @media (min-width: 991px) and (max-width: 1200px) {
    width: 704px;
  }
`;

export const PaginationWrapper = styled.div`
  display: flex;
  flex-direction: row;
  flex-wrap: nowrap;
  align-items: center;
  justify-content: center;
  margin-top: 1rem;
  margin-bottom: 2rem;
`;
