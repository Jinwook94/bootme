import {
    MainWrapper,
    Wrapper,
    CourseListHeader,
    HeaderLeft,
    CourseCount,
    HeaderRight,
    FilterButton,
    FilterSelect,
    PaginationWrapper,
    FooterWrapper,
    Footer
} from "./style"

import SlideBanner from "../../components/SlideBanner";
import PaginationBar from "../../components/PaginationBar"
import React, {useEffect, useState} from "react";
import CourseCardList from "../../components/CourseCardList";
import usePaging from "../../hooks/usePaging";

const Home = () => {

    const [cards, setCards] = useState([]);

    useEffect(() => {
        fetch("/api/cards")
            .then((response) => response.json())
            .then(({cards}) => setCards(cards));
    }, []);

    const [cardsPerPage] = useState(20);
    const maxPage = Math.floor(cards.length / cardsPerPage) + 1
    const { currentPage, handleNumberClick, handleNextClick, handlePrevClick } = usePaging(maxPage)

    // Get current cards
    const indexOfLastCard = currentPage * cardsPerPage;
    const indexOfFirstCard = indexOfLastCard - cardsPerPage;
    const currentCards = cards.slice(indexOfFirstCard, indexOfLastCard);

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
                <CourseCardList cards={currentCards}/>
            </Wrapper>
            <PaginationWrapper>
                <PaginationBar
                    itemsPerPage={cardsPerPage}
                    totalItems={cards.length}
                    handleNumberClick={handleNumberClick}
                    currentPage={currentPage}
                    handlePrevClick={handlePrevClick}
                    handleNextClick={handleNextClick}
                />
            </PaginationWrapper>
            <FooterWrapper>
                <Footer style={{textAlign: "center"}}> Footer 작성 필요</Footer>
            </FooterWrapper>
        </MainWrapper>
    );
};

export default Home;