package com.bootme.course.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

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
                .url(VALID_URL)
                .title(VALID_TITLE)
                .company(VALID_COMPANY)
                .location(VALID_LOCATION)
                .tags(VALID_TAGS)
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
    @DisplayName("addCourse()는 회사가 개설한 코스를 추가한다.")
    void addCourse(){
        //given
        Course newCourse = Course.builder()
                .url(VALID_URL)
                .title(VALID_TITLE)
                .company(VALID_COMPANY)
                .location(VALID_LOCATION)
                .tags(VALID_TAGS)
                .build();

        //when
        company.addCourse(newCourse);

        //then
        assertAll(
                () -> assertThat(company.getCourses().contains(newCourse)).isTrue(),
                () -> assertThat(newCourse.getCompany()).isEqualTo(company)
        );
    }

    @Test
    @DisplayName("deleteCourse()는 회사의 개설 코스에서 해당 코스를 삭제한다.")
    void deleteCourse(){
        company.deleteCourse(course);

        assertThat(company.getCourses().contains(course)).isFalse();
        assertThat(company.getCourses().size()).isEqualTo(0);
    }

}