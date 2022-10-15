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
import React, {useEffect, useState} from "react";

const Home = () => {
    const [cards, setCards] = useState([]);

    useEffect(() => {
        fetch("/api/cards")
            .then((response) => response.json())
            .then(({cards}) => setCards(cards));
    }, []);

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
                            <option value={"recent"}> 최신순</option>
                            <option value={"popular"}> 인기순</option>
                            <option value={"popular"}> 응답률순</option>
                        </FilterSelect>
                    </HeaderRight>
                </CourseListHeader>
                <CourseList>
                    {cards.map(({
                                    companyLogoUrl,
                                    companyLogoAlt,
                                    courseTitleUrl,
                                    courseTitleDesc,
                                    companyName,
                                    locationName,
                                    courseTag
                                }) => (
                        <CourseCard companyLogoUrl={companyLogoUrl}
                                    companyLogoAlt={companyLogoAlt}
                                    courseTitleUrl={courseTitleUrl}
                                    courseTitleDesc={courseTitleDesc}
                                    companyName={companyName}
                                    locationName={locationName}
                                    courseTag={courseTag}/>
                    ))}
                </CourseList>
            </Wrapper>
            <PaginationWrapper>
                <Pagination> Pagination 컴포넌트 필요
                </Pagination>
            </PaginationWrapper>
            <FooterWrapper>
                <Footer style={{textAlign: "center"}}> Footer 작성 필요</Footer>
            </FooterWrapper>
        </MainWrapper>
    );
};

export default Home;