package com.bootme.course.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.bootme.util.fixture.CourseFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertAll;

@DisplayName("Company 엔티티 클래스의")
class CompanyTest {

    private Company company;
    private Course course;

    @BeforeEach
    void setup(){
        course = VALID_COURSE_1;
        company = VALID_COMPANY_1;
    }

    @Test
    @DisplayName("modifyCompany()는 회사의 정보를 변경한다.")
    void updateName() {
        company.modifyCompany(VALID_COMPANY_REQUEST_2);

        assertAll(
                () -> assertThat(company.getName()).isEqualTo(VALID_COM_NAME_2),
                () -> assertThat(company.getServiceName()).isEqualTo(VALID_COM_SERVICE_NAME_2),
                () -> assertThat(company.getUrl()).isEqualTo(VALID_COM_URL_2),
                () -> assertThat(company.getServiceUrl()).isEqualTo(VALID_COM_SERVICE_URL_2),
                () -> assertThat(company.getLogoUrl()).isEqualTo(VALID_COM_LOGO_URL_2)
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
