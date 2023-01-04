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
        course = getCourse(0);
        company = getCompany(0);
//        company.addCourse(course);
    }

    @Test
    @DisplayName("modifyCompany()는 회사의 정보를 변경한다.")
    void updateName() {
        company.modifyCompany(getCompanyRequest(1));

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
//        int size = company.getCourses().size();
        company.deleteCourse(course);

        assertThat(company.getCourses().contains(course)).isFalse();
//        assertThat(company.getCourses().size()).isEqualTo(size - 1);
    }

}
