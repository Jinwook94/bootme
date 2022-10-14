import {
    MainWrapper,
    Wrapper,
    CourseListHeader,
    HeaderLeft,
    CourseCount,
    HeaderRight,
    FilterButton,
    FilterSelect,
    CourseList,
    PaginationWrapper,
    Pagination,
    PageItem,
    FooterWrapper,
    Footer
} from "./style"

import SlideBanner from "../../components/SlideBanner";
import CourseCard from "../../components/CourseCard";

const Home = () => {
    return (
        <MainWrapper>
            <Wrapper style={{marginTop: "32px"}}>
                <SlideBanner/>
            </Wrapper>
            <Wrapper>
                <CourseListHeader>
                    <HeaderLeft>
                        <CourseCount>
                            <h6> 100개의 커리큘럼 </h6>
                        </CourseCount>
                    </HeaderLeft>
                    <HeaderRight>
                        <FilterButton primary>
                            <span> 검색 필터 </span>
                        </FilterButton>
                        <FilterSelect>
                            <option value={"recent"}> 최신순 </option>
                            <option value={"popular"}> 인기순 </option>
                            <option value={"popular"}> 응답률순 </option>
                        </FilterSelect>
                    </HeaderRight>
                </CourseListHeader>
                <CourseList>
                    Mockup data 필요
                </CourseList>
            </Wrapper>
            <PaginationWrapper>
                <Pagination> Pagination 컴포넌트 필요
                </Pagination>
            </PaginationWrapper>
            <FooterWrapper>
                <Footer> Footer 작성 필요</Footer>
            </FooterWrapper>
        </MainWrapper>
    );
};

export default Home;