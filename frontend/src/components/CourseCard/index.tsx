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
                        courseName,
                        companyName,
                        locationName,
                        courseTag
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
                        <a href={courseTitleUrl}> {courseName}</a>
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
                        {locationName}
                    </Location>
                </CourseInfo>
                <CourseTags>
                    {courseTag.map((tag: string) => (
                        <TagItem>{tag}</TagItem>
                    ))}
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

export interface CourseCardProps {
    "companyLogoUrl": string;
    "companyLogoAlt": string;
    "courseTitleUrl": string;
    "courseName": string;
    "companyName": string;
    "locationName": string;
    "courseTag": [];
}

export default CourseCard;

