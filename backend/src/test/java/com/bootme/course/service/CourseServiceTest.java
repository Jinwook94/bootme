package com.bootme.course.service;

import com.bootme.course.domain.Company;
import com.bootme.course.domain.Course;
import com.bootme.course.domain.Dates;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.exception.CourseNotFoundException;
import com.bootme.course.repository.CompanyRepository;
import com.bootme.course.repository.CourseRepository;
import com.bootme.util.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_COURSE;
import static com.bootme.course.domain.Tag.*;
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

    @BeforeEach
    public void setup(){
        company1 = Company.builder()
                .url("www.naver.com")
                .name("네이버")
                .courses(new ArrayList<>())
                .build();
        company2 = Company.builder()
                .url("www.kakao.com")
                .name("카카오")
                .courses(new ArrayList<>())
                .build();
        companyRepository.save(company1);
        companyRepository.save(company2);
        course = Course.builder()
                .title(VALID_TITLE_1)
                .url(VALID_URL_1)
                .company(company1)
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
    @DisplayName("addCourse()는 코스를 추가한다.")
    public void addCourse() {
        //given
        CourseRequest courseRequest = CourseRequest.builder()
                                        .title(VALID_TITLE_2)
                                        .url(VALID_URL_2)
                                        .companyName(company2.getName())
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
        long count = courseRepository.count();

        //when
        Long id = courseService.addCourse(courseRequest);
        Course foundCourse = courseRepository.findById(id)
                            .orElseThrow(() -> new CourseNotFoundException(NOT_FOUND_COURSE));

        //then
        assertAll(
                () -> assertThat(courseRepository.count()).isEqualTo(count + 1),
                () -> assertThat(foundCourse.getTitle()).isEqualTo(VALID_TITLE_2),
                () -> assertThat(foundCourse.getUrl()).isEqualTo(VALID_URL_2),
                () -> assertThat(foundCourse.getCompany()).isEqualTo(companyRepository.findByName(company2.getName()).orElseThrow()),
                () -> assertThat(foundCourse.getLocation()).isEqualTo(VALID_LOCATION_2),
                () -> assertThat(foundCourse.getCost()).isEqualTo(VALID_COST_2),
                () -> assertThat(foundCourse.getCostType()).isEqualTo(VALID_CostType_2),
                () -> assertThat(foundCourse.getDates().getRegistrationStartDate()).isEqualTo(LocalDate.of(2022, 1, 1)),
                () -> assertThat(foundCourse.getOnoffline()).isEqualTo(VALID_ONOFFLINE_2),
                // repository 에서 찾아온 리스트는 persistenceBag 에 담겨있기 때문에 아래와 같이 검증
                () -> assertThat(foundCourse.getTags()).containsExactly(프론트엔드, Javascript, React),
                () -> assertThat(foundCourse.getPrerequisites()).isEqualTo(VALID_PREREQUISITES_2),
                () -> assertThat(foundCourse.isRecommended()).isEqualTo(VALID_ISRECOMMENDED_2),
                () -> assertThat(foundCourse.isTested()).isEqualTo(VALID_ISTESTED_2));
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
                () -> assertThat(courseResponse.getCompanyName()).isEqualTo(course.getCompany().getName()),
                () -> assertThat(courseResponse.getLocation()).isEqualTo(course.getLocation()),
                () -> assertThat(courseResponse.getTags()).isEqualTo(course.getTags())
        );
    }

    @Test
    @DisplayName("findAll()은 모든 코스 정보를 반환한다.")
    public void findAll (){
        //given
        CourseRequest courseRequest = CourseRequest.builder()
                .title(VALID_TITLE_2)
                .url(VALID_URL_2)
                .companyName(company2.getName())
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
        courseService.addCourse(courseRequest);
        courseService.addCourse(courseRequest);
        courseService.addCourse(courseRequest);
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
        CourseRequest courseRequest = CourseRequest.builder()
                                    .title(VALID_TITLE_2)
                                    .url(VALID_URL_2)
                                    .location(VALID_LOCATION_2)
                                    .cost(VALID_COST_2)
                                    .costType(VALID_CostType_2.name())
                                    .dates(Dates.builder()
                                            .registrationStartDate(LocalDate.of(2022, 12, 5))
                                            .registrationEndDate(LocalDate.of(2023, 1, 10))
                                            .courseStartDate(LocalDate.of(2023, 1, 20))
                                            .courseEndDate(LocalDate.of(2023, 7, 31))
                                            .build())
                                    .onOffline(VALID_ONOFFLINE_2.name())
                                    .tags(VALID_TAGS_2)
                                    .prerequisites(VALID_PREREQUISITES_2.name())
                                    .recommended(VALID_ISRECOMMENDED_2)
                                    .tested(VALID_ISTESTED_2)
                                    .build();
        //when
        courseService.modifyCourse(id, courseRequest);
        Course foundCourse = courseRepository.findById(id)
                            .orElseThrow(() -> new CourseNotFoundException(NOT_FOUND_COURSE));

        //then
        assertAll(
                () -> assertThat(foundCourse.getTitle()).isEqualTo(VALID_TITLE_2),
                () -> assertThat(foundCourse.getUrl()).isEqualTo(VALID_URL_2),
                () -> assertThat(foundCourse.getCompany()).isEqualTo(companyRepository.findByName(company1.getName()).orElseThrow()),
                () -> assertThat(foundCourse.getLocation()).isEqualTo(VALID_LOCATION_2),
                () -> assertThat(foundCourse.getCost()).isEqualTo(VALID_COST_2),
                () -> assertThat(foundCourse.getCostType()).isEqualTo(VALID_CostType_2),
                () -> assertThat(foundCourse.getDates().getRegistrationStartDate()).isEqualTo(LocalDate.of(2022, 12, 5)),
                () -> assertThat(foundCourse.getOnoffline()).isEqualTo(VALID_ONOFFLINE_2),
                // repository 에서 찾아온 리스트는 persistenceBag 에 담겨있기 때문에 아래와 같이 검증
                () -> assertThat(foundCourse.getTags()).containsExactly(프론트엔드, Javascript, React),
                () -> assertThat(foundCourse.getPrerequisites()).isEqualTo(VALID_PREREQUISITES_2),
                () -> assertThat(foundCourse.isRecommended()).isEqualTo(VALID_ISRECOMMENDED_2),
                () -> assertThat(foundCourse.isTested()).isEqualTo(VALID_ISTESTED_2)
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
        Optional<Course> deletedCourse = courseRepository.findById(id);

        //then
        assertThat(deletedCourse).isEmpty();
        assertThat(courseRepository.count()).isEqualTo(count - 1);
    }

}