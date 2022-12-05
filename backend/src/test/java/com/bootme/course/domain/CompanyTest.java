package com.bootme.course.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;

import static com.bootme.util.fixture.CourseFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Company 엔티티 클래스의")
class CompanyTest {

    private Company company;
    private Course course;

    @BeforeEach
    void setup(){
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
        company = Company.builder()
                        .name("Google")
                        .url("www.google.com")
                        .courses(new ArrayList<>(Arrays.asList(course)))
                        .build();
    }

    @Test
    @DisplayName("updateName()은 회사의 이름을 업데이트한다.")
    void updateName() {
        company.updateName("Alphabet");

        assertThat(company.getName()).isEqualTo("Alphabet");
    }

    @Test
    @DisplayName("updateUrl()은 회사의 링크을 업데이트한다.")
    void updateUrl() {
        company.updateUrl("www.alphabet.com");

        assertThat(company.getUrl()).isEqualTo("www.alphabet.com");
    }

    @Test
    @DisplayName("deleteCourse()는 회사의 개설 코스에서 해당 코스를 삭제한다.")
    void deleteCourse(){
        company.deleteCourse(course);

        assertThat(company.getCourses().contains(course)).isFalse();
        assertThat(company.getCourses().size()).isEqualTo(0);
    }

}