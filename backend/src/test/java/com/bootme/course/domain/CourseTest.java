package com.bootme.course.domain;

import com.bootme.course.dto.CourseRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;


import java.time.LocalDate;

import static com.bootme.util.fixture.CourseFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Course 엔티티 클래스의")
class CourseTest {

    private Course course;


    @BeforeEach
    void setUp() {
        course = Course.builder()
                .title(VALID_TITLE_1)
                .url(VALID_URL_1)
                .company(VALID_COMPANY_1)
                .location(VALID_LOCATION_1)
                .cost(VALID_COST_1)
                .costType(VALID_CostType_1)
                .dates(Dates.builder()
                        .registrationStartDate(LocalDate.of(2022, 12, 5))
                        .registrationEndDate(LocalDate.of(2023, 1, 10))
                        .courseStartDate(LocalDate.of(2023, 1, 20))
                        .courseEndDate(LocalDate.of(2023, 7, 31))
                        .build())
                .onoffline(VALID_ONOFFLINE_1)
                .tags(VALID_TAGS_1)
                .prerequisites(VALID_PREREQUISITES_1)
                .isRecommended(VALID_ISRECOMMENDED_1)
                .isTested(VALID_ISTESTED_1)
                .build();
    }

    @Test
    @DisplayName("modifyCourse()는 코스 정보를 변경한다.")
    void updateUrl() {
        CourseRequest courseRequest = CourseRequest.builder()
                                                .title(VALID_TITLE_2)
                                                .url(VALID_URL_2)
                                                .location(VALID_LOCATION_2)
                                                .cost(VALID_COST_2)
                                                .costType(VALID_CostType_2.name())
                                                .dates(Dates.builder()
                                                        .registrationStartDate(LocalDate.of(2022, 1, 1))
                                                        .registrationEndDate(LocalDate.of(2022, 2, 1))
                                                        .courseStartDate(LocalDate.of(2022, 2, 10))
                                                        .courseEndDate(LocalDate.of(2022, 8, 31))
                                                        .build())
                                                .onOffline(VALID_ONOFFLINE_2.name())
                                                .tags(VALID_TAGS_2)
                                                .prerequisites(VALID_PREREQUISITES_2.name())
                                                .recommended(VALID_ISRECOMMENDED_2)
                                                .tested(VALID_ISTESTED_2)
                                                .build();
        course.modifyCourse(courseRequest);

        assertAll(
                () -> assertThat(course.getTitle()).isEqualTo(VALID_TITLE_2),
                () -> assertThat(course.getUrl()).isEqualTo(VALID_URL_2),
                () -> assertThat(course.getLocation()).isEqualTo(VALID_LOCATION_2),
                () -> assertThat(course.getCost()).isEqualTo(VALID_COST_2),
                () -> assertThat(course.getCostType()).isEqualTo(VALID_CostType_2),
                () -> assertThat(course.getDates().getRegistrationStartDate()).isEqualTo(LocalDate.of(2022, 1, 1)),
                () -> assertThat(course.getOnoffline()).isEqualTo(VALID_ONOFFLINE_2),
                () -> assertThat(course.getTags()).isEqualTo(VALID_TAGS_2),
                () -> assertThat(course.getPrerequisites()).isEqualTo(VALID_PREREQUISITES_2),
                () -> assertThat(course.isRecommended()).isEqualTo(VALID_ISRECOMMENDED_2),
                () -> assertThat(course.isTested()).isEqualTo(VALID_ISTESTED_2)
        );
    }

}