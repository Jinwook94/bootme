import CourseCard, {CourseCardProps} from "../CourseCard";
import { CourseCardListStyle } from "./style"
import React from "react";

const CourseCardList = ({cards}: CourseCardListProps) => {
    return (
        <CourseCardListStyle>
            {cards.map(({
                            companyLogoUrl,
                            companyLogoAlt,
                            courseTitleUrl,
                            courseTitleDesc,
                            companyName,
                            locationName,
                            courseTag
                        }: CourseCardProps) => (
                <CourseCard companyLogoUrl={companyLogoUrl}
                            companyLogoAlt={companyLogoAlt}
                            courseTitleUrl={courseTitleUrl}
                            courseTitleDesc={courseTitleDesc}
                            companyName={companyName}
                            locationName={locationName}
                            courseTag={courseTag}/>
            ))}
        </CourseCardListStyle>
    )
}

interface CourseCardListProps {
    cards: any;
}

export default CourseCardList;
