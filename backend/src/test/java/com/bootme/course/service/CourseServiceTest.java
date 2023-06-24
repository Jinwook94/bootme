package com.bootme.course.service;

import com.bootme.common.enums.SortOption;
import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.domain.Company;
import com.bootme.course.domain.Course;
import com.bootme.course.dto.CourseDetailResponse;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.repository.CompanyRepository;
import com.bootme.course.repository.CourseRepository;
import com.bootme.course.repository.CourseStackRepository;
import com.bootme.course.domain.CourseStatus;
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
import java.util.Objects;
import java.util.stream.Collectors;

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
        stackRepository.save(Stack.of("JavaScript", "svg", LANGUAGES, FRAMEWORKS));
        stackRepository.save(Stack.of("TypeScript", "svg", LANGUAGES, FRAMEWORKS));
        stackRepository.save(Stack.of("Java", "svg", LANGUAGES, FRAMEWORKS));
        stackRepository.save(Stack.of("Kotlin", "svg", LANGUAGES, FRAMEWORKS));
        stackRepository.save(Stack.of("Swift", "svg", LANGUAGES, FRAMEWORKS));
        stackRepository.save(Stack.of("React", "svg", LANGUAGES, FRAMEWORKS));
        stackRepository.save(Stack.of("Django", "svg", LANGUAGES, FRAMEWORKS));
        stackRepository.save(Stack.of("Spring", "svg", LANGUAGES, FRAMEWORKS));
        stackRepository.save(Stack.of("Node.js", "svg", LANGUAGES, FRAMEWORKS));
        stackRepository.save(Stack.of("Vue.js", "svg", LANGUAGES, FRAMEWORKS));
        company1 = getCompany(1);
        company2 = getCompany(2);
        company3 = getCompany(3);
        companyRepository.save(company1);
        companyRepository.save(company2);
        companyRepository.save(company3);
        course = courseRepository.findById(courseService.addCourse(getCourseRequest(1))).orElseThrow();
        company1.addCourse(course);
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
        CourseDetailResponse courseResponse = courseService.findById(id, 1L);
        List<Stack> stacks = courseStackRepository.findStacksByCourseId(id);
        List<String> languages = stacks.stream()
                .filter(stack -> Objects.equals(stack.getType(), "language"))
                .map(Stack::getName)
                .collect(Collectors.toList());
        List<String> frameworks = stacks.stream()
                .filter(stack -> Objects.equals(stack.getType(), "framework"))
                .map(Stack::getName)
                .collect(Collectors.toList());

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
                () -> assertThat(courseResponse.getLanguages()).isEqualTo(languages),
                () -> assertThat(courseResponse.getFrameworks()).isEqualTo(frameworks)
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
        Page<CourseResponse> courseResponses = courseService.findAll(1L, 1, 10, "popular", filters);

        //then: setUp()의 company1.addCourse(course)에서 코스 한 개 등록되었으므로 총 3개의 코스
        assertThat(courseResponses).hasSize(3);
    }

    @Test
    @DisplayName("findAll()은 필터 옵션이 선택된 경우 코스를 필터링하여 반환한다.")
    void findAll_filtering_1 (){
        //given
        courseService.addCourse(getCourseRequest(2));
        courseService.addCourse(getCourseRequest(3));
        MultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
        filters.add("superCategories", AI);

        //when
        Page<CourseResponse> courseResponses = courseService.findAll(1L,1, 10, "popular", filters);

        CourseResponse courseResponse1 = courseResponses.getContent().get(0);
        CourseResponse courseResponse2 = courseResponses.getContent().get(1);

        //then
        assertAll(
                () -> assertThat(courseResponses).hasSize(2),  // 3개의 코스 중 두 번쨰, 세 번째 코스가 superCategory 항목으로 AI를 포함하고 있음
                () -> assertThat(courseResponse1.getTitle()).isEqualTo(VALID_TITLE_2),
                () -> assertThat(courseResponse2.getTitle()).isEqualTo(VALID_TITLE_3)
        );
    }

    @Test
    @DisplayName("findAll()은 필터 옵션이 선택된 경우 코스를 필터링하여 반환한다.")
    void findAll_filtering_2 (){
        //given
        CourseRequest courseRequest = getCourseRequest(2);
        courseService.addCourse(courseRequest);
        courseService.addCourse(getCourseRequest(3));
        MultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
        filters.add("frameworks", Django);

        //when
        Page<CourseResponse> courseResponses = courseService.findAll(1L, 1, 10, "popular", filters);
        CourseResponse courseResponse = courseResponses.getContent().get(0);

        //then
        assertAll(
                () -> assertThat(courseResponses).hasSize(1),
                () -> assertThat(courseResponse.getTitle()).isEqualTo(VALID_TITLE_2)
        );
    }

    @Test
    @DisplayName("findAll()은 검색어가 입력된 경우 입력된 검색어로 코스를 필터링 하여 반환한다.")
    void findAll_searching (){
        //given
        courseService.addCourse(getCourseRequest(2));
        courseService.addCourse(getCourseRequest(3));
        MultiValueMap<String, String> filters = new LinkedMultiValueMap<>();
        filters.add("search", "서버");

        //when
        Page<CourseResponse> courseResponses = courseService.findAll(1L,1, 10, "popular", filters);

        CourseResponse courseResponse1 = courseResponses.getContent().get(0);
        CourseResponse courseResponse2 = courseResponses.getContent().get(1);

        //then
        assertAll(
                () -> assertThat(courseResponses).hasSize(2),  // 3개의 코스 중 첫 번쨰, 세 번째 코스가 타이틀에 항목으로 "서버"를 포함하고 있음
                () -> assertThat(courseResponse1.getTitle()).isEqualTo(VALID_TITLE_1),
                () -> assertThat(courseResponse2.getTitle()).isEqualTo(VALID_TITLE_3)
        );
    }

    @Test
    @DisplayName("findAll()은 정렬 옵션이 선택된 경우 코스를 정렬하여 반환한다.")
    void findAll_sorting (){
        //given
        courseService.addCourse(getCourseRequest(2));
        courseService.addCourse(getCourseRequest(3));

        MultiValueMap<String, String> filters = new LinkedMultiValueMap<>();

        //when: 정렬 기준을 등록순(newest)으로 설정함
        Page<CourseResponse> courseResponses = courseService.findAll(1L, 1, 10, SortOption.CREATED_AT.getValue(), filters);
        CourseResponse courseResponse1 = courseResponses.getContent().get(0);
        CourseResponse courseResponse2 = courseResponses.getContent().get(1);
        CourseResponse courseResponse3 = courseResponses.getContent().get(2);

        //then: 가장 늦게 등록된 세번째 코스부터 반환됨
        assertAll(
                () -> assertThat(courseResponse1.getTitle()).isEqualTo(VALID_TITLE_3),
                () -> assertThat(courseResponse2.getTitle()).isEqualTo(VALID_TITLE_2),
                () -> assertThat(courseResponse3.getTitle()).isEqualTo(VALID_TITLE_1)
        );
    }

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
    @DisplayName("modifyCourseDetail()은 코스 정보 HTML 을 변경한다.")
    void modifyCourseDetail(){
        //given
        Long id = courseRepository.save(course).getId();

        //when
        courseService.modifyCourseDetail(id, getCourseDetailRequest(2));
        Course foundCourse = courseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException(NOT_FOUND_COURSE, String.valueOf(id)));

        //then
        assertThat(foundCourse.getDetail()).isEqualTo(VALID_DETAIL_2);
    }

    @Test
    @DisplayName("deleteCourse()는 코스의 status 를 DELETED 로 변경한다.")
    void deleteCourse(){
        //given
        courseRepository.save(course);
        Long id = course.getId();
//        List<CourseStack> courseStacks = courseStackRepository.findByCourseId(id);
//        courseStackRepository.deleteAll(courseStacks);

        //when
        courseService.deleteCourse(id);

        //then
        assertAll(
                () -> assertThat(courseRepository.findById(id).get().getStatus()).isEqualTo(CourseStatus.DELETED)
        );
    }

}
