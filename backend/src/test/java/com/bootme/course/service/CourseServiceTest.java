package com.bootme.course.service;

import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.Company;
import com.bootme.course.domain.Course;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.repository.CompanyRepository;
import com.bootme.course.repository.CourseRepository;
import com.bootme.course.repository.CourseStackRepository;
import com.bootme.stack.domain.Stack;
import com.bootme.stack.repository.StackRepository;
import com.bootme.util.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_COURSE;
import static com.bootme.util.fixture.CourseFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("CourseService 클래스의")
class CourseServiceTest extends ServiceTest {

    @Autowired
    private CourseRepository courseRepository;

    @Autowired
    private CompanyRepository companyRepository;

    @Autowired
    private CourseStackRepository courseStackRepository;

    @Autowired
    private StackRepository stackRepository;

    private Course course;
    private Company company1;
    private Company company2;
    private Company company3;

    @BeforeEach
    void setup(){
        company1 = getCompany(1);
        company2 = getCompany(2);
        company3 = getCompany(3);
        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);
        course = getCourse(1);
        company1.addCourse(course);
        stackRepository.save(Stack.of("TypeScript"));
        stackRepository.save(Stack.of("Kotlin"));
        stackRepository.save(Stack.of("Swift"));
        stackRepository.save(Stack.of("Django"));
        stackRepository.save(Stack.of("Spring"));
        stackRepository.save(Stack.of("Node.js"));
        stackRepository.save(Stack.of("Vue.js"));
    }

    @Test
    @DisplayName("addCourse()는 코스를 추가한다.")
    void addCourse() {
        //given
        long count = courseRepository.count();

        //when
        Long id = courseService.addCourse(getCourseRequest(2));
        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id)));
        List<Stack> stacks = courseStackRepository.findStacksByCourseId(id);

        //then
        assertAll(
                () -> assertThat(courseRepository.count()).isEqualTo(count + 1),
                () -> assertThat(foundCourse.getTitle()).isEqualTo(VALID_TITLE_2),
                () -> assertThat(foundCourse.getUrl()).isEqualTo(VALID_URL_2),
                () -> assertThat(foundCourse.getCompany().getName()).isEqualTo(getCompany(2).getName()),
                () -> assertThat(foundCourse.getLocation()).isEqualTo(VALID_LOCATION_2),
                () -> assertThat(foundCourse.getCost()).isEqualTo(VALID_COST_2),
                () -> assertThat(foundCourse.getDates()).isEqualTo(VALID_DATES_2),
                () -> assertThat(foundCourse.isRecommended()).isFalse(),
                () -> assertThat(foundCourse.isFree()).isFalse(),
                () -> assertThat(foundCourse.isKdt()).isFalse(),
                () -> assertThat(foundCourse.isOnline()).isFalse(),
                () -> assertThat(foundCourse.isTested()).isFalse(),
                () -> assertThat(foundCourse.isPrerequisiteRequired()).isFalse(),
                () -> assertThat(Stack.getLanguages(stacks)).isEqualTo(VALID_LANGUAGES_2),
                () -> assertThat(Stack.getFrameworks(stacks)).isEqualTo(VALID_FRAMEWORKS_2)
        );
    }

    @Test
    @DisplayName("findById()는 코스 정보를 반환한다.")
    void findById() {
        //given
        Long id = courseRepository.save(course).getId();

        //when
        CourseResponse courseResponse = courseService.findById(id);
        List<Stack> stacks = courseStackRepository.findStacksByCourseId(id);

        //then
        assertAll(
                () -> assertThat(courseResponse.getId()).isEqualTo(course.getId()),
                () -> assertThat(courseResponse.getUrl()).isEqualTo(course.getUrl()),
                () -> assertThat(courseResponse.getTitle()).isEqualTo(course.getTitle()),
                () -> assertThat(courseResponse.getLocation()).isEqualTo(course.getLocation()),
                () -> assertThat(courseResponse.getClicks()).isEqualTo(course.getClicks()),
                () -> assertThat(courseResponse.getBookmarks()).isEqualTo(course.getBookmarks()),
                () -> assertThat(courseResponse.isRecommended()).isTrue(),
                () -> assertThat(courseResponse.isFree()).isTrue(),
                () -> assertThat(courseResponse.isKdt()).isTrue(),
                () -> assertThat(courseResponse.isOnline()).isTrue(),
                () -> assertThat(courseResponse.isTested()).isTrue(),
                () -> assertThat(courseResponse.isPrerequisiteRequired()).isTrue(),
                () -> assertThat(courseResponse.getSuperCategories()).isEqualTo(course.getCategories().getSuperCategories()),
                () -> assertThat(courseResponse.getSubCategories()).isEqualTo(course.getCategories().getSubCategories()),
                () -> assertThat(courseResponse.getLanguages()).isEqualTo(Stack.getLanguages(stacks)),
                () -> assertThat(courseResponse.getFrameworks()).isEqualTo(Stack.getFrameworks(stacks))
        );
    }

    @Test
    @DisplayName("findAll()은 모든 코스 정보를 반환한다.")
    void findAll (){
        //given
        courseService.addCourse(getCourseRequest(2));
        courseService.addCourse(getCourseRequest(3));
        MultiValueMap<String, String> filters = new LinkedMultiValueMap<>();

        //when
        Page<CourseResponse> courseResponses = courseService.findAll(1, 10, "popular", filters);

        //then: setUp()의 company1.addCourse(course)에서 코스 한 개 등록되었으므로 총 3개의 코스
        assertThat(courseResponses).hasSize(3);
    }

    // todo: findAll() 필터링, 정렬, 페이징 단위테스트 코드 추가

    @Test
    @DisplayName("modifyCourse()는 코스 정보를 변경한다.")
    void modifyCourse(){
        //given
        Long id = courseRepository.save(course).getId();

        //when
        courseService.modifyCourse(id, getCourseRequest(2));
        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id)));

        //then
        assertAll(
                () -> assertThat(foundCourse.getTitle()).isEqualTo(VALID_TITLE_2),
                () -> assertThat(foundCourse.getUrl()).isEqualTo(VALID_URL_2),
                () -> assertThat(foundCourse.getLocation()).isEqualTo(VALID_LOCATION_2),
                () -> assertThat(foundCourse.getCost()).isEqualTo(VALID_COST_2),
                () -> assertThat(foundCourse.getDates()).isEqualTo(VALID_DATES_2)
        );
    }

    @Test
    @DisplayName("deleteCourse()는 코스를 삭제한다.")
    void deleteCourse(){
        //given
        courseRepository.save(course);
        Long id = course.getId();
        long count = courseRepository.count();

        //when
        courseService.deleteCourse(id);

        //then
        assertAll(
                () -> assertThat(courseRepository.findById(id)).isNotPresent(),
                () -> assertThat(courseRepository.count()).isEqualTo(count - 1),
                () -> assertThat(company1.getCourses()).doesNotContain(course)
        );
    }

}
