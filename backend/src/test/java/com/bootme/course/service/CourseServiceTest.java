package com.bootme.course.service;

import com.bootme.common.exception.ErrorType;
import com.bootme.course.domain.Company;
import com.bootme.course.domain.Course;
import com.bootme.course.domain.Tag;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.exception.CourseNotFoundException;
import com.bootme.course.repository.CourseRepository;
import com.bootme.util.ServiceTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

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

    private Course course;
    private Company company;

    @BeforeEach
    public void setup(){
        company = Company.builder()
                .url("www.naver.com")
                .name("네이버")
                .courses(new ArrayList<>())
                .build();
        course = Course.builder()
                .url(VALID_URL_1)
                .title(VALID_TITLE_1)
                .company(company)
                .location(VALID_LOCATION_1)
                .tags(VALID_TAGS_1)
                .build();
    }

    @Test
    @DisplayName("addCourse()는 코스를 추가한다.")
    public void addCourse() {
        //given
        CourseRequest courseRequest = CourseRequest.builder()
                                                    .url(course.getUrl())
                                                    .title(course.getTitle())
                                                    .company(course.getCompany())
                                                    .location(course.getLocation())
                                                    .tags(course.getTags())
                                                    .build();
        long count = courseRepository.count();

        //when
        Long id = courseService.addCourse(courseRequest);
        Course foundCourse = courseRepository.findById(id)
                            .orElseThrow(() -> new CourseNotFoundException(NOT_FOUND_COURSE));

        //then
        assertAll(
                () -> assertThat(courseRepository.count()).isEqualTo(count + 1),
                () -> assertThat(foundCourse.getUrl()).isEqualTo(courseRequest.getUrl()),
                () -> assertThat(foundCourse.getTitle()).isEqualTo(courseRequest.getTitle()),
                () -> assertThat(foundCourse.getCompany()).isEqualTo(courseRequest.getCompany()),
                () -> assertThat(foundCourse.getLocation()).isEqualTo(courseRequest.getLocation()),
                // repo 에서 찾아온 리스트는 persistenceBag 에 담겨있기 때문에 아래와 같이 검증
                () -> assertThat(foundCourse.getTags()).containsExactly(백엔드, Java, Spring)
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
                () -> assertThat(courseResponse.getCompany()).isEqualTo(course.getCompany()),
                () -> assertThat(courseResponse.getLocation()).isEqualTo(course.getLocation()),
                () -> assertThat(courseResponse.getTags()).isEqualTo(course.getTags())
        );
    }

    @Test
    @DisplayName("modifyCourse()는 코스 정보를 변경한다.")
    public void modifyCourse(){
        //given
        Long id = courseRepository.save(course).getId();
        CourseRequest courseRequest = CourseRequest.builder()
                                                    .url(VALID_URL_2)
                                                    .title(VALID_TITLE_2)
                                                    .location(VALID_LOCATION_2)
                                                    .tags(VALID_TAGS_2)
                                                    .build();
        //when
        courseService.modifyCourse(id, courseRequest);
        Course foundCourse = courseRepository.findById(id)
                            .orElseThrow(() -> new CourseNotFoundException(NOT_FOUND_COURSE));

        //then
        assertAll(
                () -> assertThat(foundCourse.getUrl()).isEqualTo(courseRequest.getUrl()),
                () -> assertThat(foundCourse.getTitle()).isEqualTo(courseRequest.getTitle()),
                () -> assertThat(foundCourse.getLocation()).isEqualTo(courseRequest.getLocation()),
                () -> assertThat(foundCourse.getTags()).isEqualTo(courseRequest.getTags())
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