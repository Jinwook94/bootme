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


const CourseCard = ({
                        companyLogoUrl,
                        companyLogoAlt,
                        courseTitleUrl,
                        courseTitleDesc,
                        companyName,
                        LocationName,
                        CourseTag
                    }: CourseCardProps) => {
    return (
        <Wrapper>
            <ItemHeader>
                <a>
                    <CompanyLogo
                        src={companyLogoUrl}
                        alt={companyLogoAlt} className="company-logo"/>
                </a>
            </ItemHeader>
            <ItemBody>
                <CourseTitleWrapper>
                    <CourseTitle>
                        <a href={courseTitleUrl}> {courseTitleDesc}</a>
                    </CourseTitle>
                </CourseTitleWrapper>
                <CompanyName>
                    <CompanyLink>{companyName}</CompanyLink>
                </CompanyName>
                <CourseInfo>
                    <Experience>
                        <ExperienceIcon/>
                        경력무관
                    </Experience>
                    <Location>
                        <LocationIcon/>
                        {LocationName}
                    </Location>
                </CourseInfo>
                <CourseTags>
                    <TagItem>{CourseTag}</TagItem>
                </CourseTags>
            </ItemBody>

            <div className={"Bookmark"}>
                <Bookmark>
                    <BookmarkIcon/>
                </Bookmark>
            </div>
        </Wrapper>
    )
}

interface CourseCardProps {
    "companyLogoUrl": string;
    "companyLogoAlt": string;
    "courseTitleUrl": string;
    "courseTitleDesc": string;
    "companyName": string;
    "LocationName": string;
    "CourseTag": [];
}

export default CourseCard;

