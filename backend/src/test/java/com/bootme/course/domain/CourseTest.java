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
        course = getCourse(1);
    }

    @Test
    @DisplayName("modifyCourse()는 코스 정보를 변경한다.")
    void modifyCourse() {
        course.modifyCourse(getCourseRequest(2));

        assertAll(
                () -> assertThat(course.getTitle()).isEqualTo(VALID_TITLE_2),
                () -> assertThat(course.getUrl()).isEqualTo(VALID_URL_2),
                () -> assertThat(course.getLocation()).isEqualTo(VALID_LOCATION_2),
                () -> assertThat(course.getCost()).isEqualTo(VALID_COST_2),
                () -> assertThat(course.getDates().getRegistrationStartDate()).isEqualTo(VALID_DATES_2.getRegistrationStartDate()),
                () -> assertThat(course.isRecommended()).isFalse(),
                () -> assertThat(course.isFree()).isFalse(),
                () -> assertThat(course.isKdt()).isFalse(),
                () -> assertThat(course.isOnline()).isFalse(),
                () -> assertThat(course.isTested()).isFalse(),
                () -> assertThat(course.isPrerequisiteRequired()).isFalse()
        );
    }

    @Test
    @DisplayName("modifyCompany()는 코스의 개설 회사를 변경한다.")
    void modifyCompany(){
        Company newCompany = getCompany(2);
        course.modifyCompany(newCompany);

        assertThat(course.getCompany()).isEqualTo(newCompany);
    }

}
