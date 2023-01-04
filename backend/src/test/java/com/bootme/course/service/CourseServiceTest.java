package com.bootme.course.service;

import com.bootme.course.domain.Company;
import com.bootme.course.domain.Course;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.exception.CourseNotFoundException;
import com.bootme.course.repository.CompanyRepository;
import com.bootme.course.repository.CourseRepository;
import com.bootme.util.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_COURSE;
import static com.bootme.util.fixture.CourseFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CourseService 클래스의")
class CourseServiceTest extends ServiceTest {

    @Autowired
    private CourseService courseService;

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CompanyRepository companyRepository;

    private Course course;
    private Company company1;
    private Company company2;
    private Company company3;

    @BeforeEach
    public void setup(){
        company1 = getCompany(0);
        company2 = getCompany(1);
        company3 = getCompany(2);
        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);
        course = getCourse(0);
    }

    @Test
    @DisplayName("addCourse()는 코스를 추가한다.")
    public void addCourse() {
        //given
        long count = courseRepository.count();

        //when
        Long id = courseService.addCourse(getCourseRequest(1));
        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(NOT_FOUND_COURSE));

        //then
        assertAll(
                () -> assertThat(courseRepository.count()).isEqualTo(count + 1),
                () -> assertThat(foundCourse.getTitle()).isEqualTo(VALID_TITLE_2),
                () -> assertThat(foundCourse.getUrl()).isEqualTo(VALID_URL_2),
                () -> assertThat(foundCourse.getCompany().getName()).isEqualTo(getCompany(1).getName()),
                () -> assertThat(foundCourse.getLocation()).isEqualTo(VALID_LOCATION_2),
                () -> assertThat(foundCourse.getCost()).isEqualTo(VALID_COST_2),
                () -> assertThat(foundCourse.getCostType()).isEqualTo(VALID_CostType_2),
                () -> assertThat(foundCourse.getDates()).isEqualTo(VALID_DATES_2),
                () -> assertThat(foundCourse.getOnoffline()).isEqualTo(VALID_ONOFFLINE_2),
                // repository 에서 찾아온 리스트는 persistenceBag 에 담겨있기 때문에 아래와 같이 검증
                () -> assertThat(foundCourse.getPrerequisites()).isEqualTo(VALID_PREREQUISITES_2)
        );
    }

    @Test
    @DisplayName("findById()는 코스 정보를 반환한다.")
    public void findById() {
        //given
        Long id = courseRepository.save(course).getId();

        //when
        CourseResponse courseResponse = courseService.findById(id);


        //then
        assertAll(
                () -> assertThat(courseResponse.getId()).isEqualTo(course.getId()),
                () -> assertThat(courseResponse.getUrl()).isEqualTo(course.getUrl()),
                () -> assertThat(courseResponse.getTitle()).isEqualTo(course.getTitle()),
                () -> assertThat(courseResponse.getLocation()).isEqualTo(course.getLocation())
        );
    }

    @Test
    @DisplayName("findAll()은 모든 코스 정보를 반환한다.")
    public void findAll (){
        //given
        courseService.addCourse(getCourseRequest(0));
        courseService.addCourse(getCourseRequest(1));
        courseService.addCourse(getCourseRequest(2));

        //when
        List<CourseResponse> courseResponses = courseService.findAll();

        //then
        assertThat(courseResponses.size()).isEqualTo(3);
    }

    @Test
    @DisplayName("modifyCourse()는 코스 정보를 변경한다.")
    public void modifyCourse(){
        //given
        Long id = courseRepository.save(course).getId();

        //when
        courseService.modifyCourse(id, getCourseRequest(1));
        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new CourseNotFoundException(NOT_FOUND_COURSE));

        //then
        assertAll(
                () -> assertThat(foundCourse.getTitle()).isEqualTo(VALID_TITLE_2),
                () -> assertThat(foundCourse.getUrl()).isEqualTo(VALID_URL_2),
                () -> assertThat(foundCourse.getLocation()).isEqualTo(VALID_LOCATION_2),
                () -> assertThat(foundCourse.getCost()).isEqualTo(VALID_COST_2),
                () -> assertThat(foundCourse.getCostType()).isEqualTo(VALID_CostType_2),
                () -> assertThat(foundCourse.getDates()).isEqualTo(VALID_DATES_2),
                () -> assertThat(foundCourse.getOnoffline()).isEqualTo(VALID_ONOFFLINE_2),
                // repository 에서 찾아온 리스트는 persistenceBag 에 담겨있기 때문에 아래와 같이 검증
                () -> assertThat(foundCourse.getPrerequisites()).isEqualTo(VALID_PREREQUISITES_2)
        );
    }

    @Test
    @DisplayName("deleteCourse()는 코스를 삭제한다.")
    public void deleteCourse(){
        //given
        courseRepository.save(course);
        Long id = course.getId();
        long count = courseRepository.count();

        //when
        courseService.deleteCourse(id);

        //then
        assertAll(
                () -> assertThat(courseRepository.findById(id).isEmpty()).isTrue(),
                () -> assertThat(courseRepository.count()).isEqualTo(count - 1),
                () -> assertThat(company1.getCourses().contains(course)).isFalse()
        );
    }

}
