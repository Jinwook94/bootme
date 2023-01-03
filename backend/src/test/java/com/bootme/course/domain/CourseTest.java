package com.bootme.course.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.bootme.util.fixture.CourseFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Course 엔티티 클래스의")
class CourseTest {

    private Course course;


    @BeforeEach
    void setUp() {
        course = VALID_COURSE_1;
    }

    @Test
    @DisplayName("modifyCourse()는 코스 정보를 변경한다.")
    void updateUrl() {
        course.modifyCourse(VALID_COURSE_REQUEST_2);

        assertAll(
                () -> assertThat(course.getTitle()).isEqualTo(VALID_TITLE_2),
                () -> assertThat(course.getUrl()).isEqualTo(VALID_URL_2),
                () -> assertThat(course.getLocation()).isEqualTo(VALID_LOCATION_2),
                () -> assertThat(course.getCost()).isEqualTo(VALID_COST_2),
                () -> assertThat(course.getCostType()).isEqualTo(VALID_CostType_2),
                () -> assertThat(course.getDates().getRegistrationStartDate()).isEqualTo(VALID_DATES_2.getRegistrationStartDate()),
                () -> assertThat(course.getOnoffline()).isEqualTo(VALID_ONOFFLINE_2),
                () -> assertThat(course.getPrerequisites()).isEqualTo(VALID_PREREQUISITES_2)
        );
    }

}
