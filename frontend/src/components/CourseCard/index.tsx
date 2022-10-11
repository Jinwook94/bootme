import {
    Wrapper,
    ItemHeader,
    ItemBody,
    CourseTitleWrapper,
    CourseTitle,
    CompanyLogo,
    CompanyName,
    CompanyLink,
    CourseInfo,
    Experience,
    Location,
    CourseTags,
    TagItem,
    Bookmark
} from "./style";

import ExperienceIcon from "../../assets/experience.svg"
import LocationIcon from "../../assets/location.svg"
import BookmarkIcon from "../../assets/bookmark.svg"


const CourseCard = () => {
    return (
        <Wrapper>
            <ItemHeader>
                <a>
                    <CompanyLogo
                        src="https://grepp-programmers.s3.amazonaws.com/production/company/logo/8132/%E1%84%87%E1%85%B5%E1%86%A8%E1%84%91%E1%85%B5%E1%86%A8%E1%84%8E%E1%85%A5%E1%84%90%E1%85%B5%E1%86%B7_%E1%84%85%E1%85%A9%E1%84%80%E1%85%A9_160x160-01.png"
                        alt="빅픽처팀" className="company-logo"/>
                </a>
            </ItemHeader>
            <ItemBody>
                <CourseTitleWrapper>
                    <CourseTitle>
                        <a href={"https://google.com"}> 웹/앱 프론트엔드 개발자</a>
                    </CourseTitle>
                </CourseTitleWrapper>
                <CompanyName>
                    <CompanyLink>빅픽쳐</CompanyLink>
                </CompanyName>
                <CourseInfo>
                    <Experience>
                        <ExperienceIcon />
                        경력무관
                    </Experience>
                    <Location>
                        <LocationIcon />
                        서울 강남구
                    </Location>
                </CourseInfo>
                <CourseTags>
                    <TagItem>프론트엔트</TagItem>
                    <TagItem>TypeScript</TagItem>
                    <TagItem>JavaScript</TagItem>
                    <TagItem>외 1개</TagItem>
                </CourseTags>
            </ItemBody>

            <div className={"Bookmark"}>
                <Bookmark>
                    <BookmarkIcon />
                </Bookmark>
            </div>
        </Wrapper>
)
}

interface CourseCardProps
    {

    }

export default CourseCard;

