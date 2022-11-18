package com.bootme.course.domain;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bootme.course.domain.Tag.*;
import static com.bootme.util.fixture.CourseFixture.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Course 엔티티 클래스의")
class CourseTest {

    private Course course;


    @BeforeEach
    void setUp() {
        course = Course.builder()
                .url(VALID_URL_1)
                .title(VALID_TITLE_1)
                .company(VALID_COMPANY_1)
                .location(VALID_LOCATION_1)
                .tags(VALID_TAGS_1)
                .build();
    }

    @Test
    @DisplayName("updateUrl()은 코스 링크를 변경한다.")
    void updateUrl() {
        course.updateUrl("변경된 URL");

        assertThat(course.getUrl()).isEqualTo("변경된 URL");
    }


    @Test
    @DisplayName("updateTitle()은 코스 타이틀을 변경한다.")
    void updateTitle() {
        course.updateTitle("변경된 타이틀");

        assertThat(course.getTitle()).isEqualTo("변경된 타이틀");
    }

    @Test
    @DisplayName("updateLocation()은 코스 진행 장소를 변경한다.")
    void updateLocation() {
        course.updateLocation("변경된 장소");

        assertThat(course.getLocation()).isEqualTo("변경된 장소");
    }


    @Test
    @DisplayName("updateTag()은 코스 태그를 변경한다.")
    void updateTag() {
        List<Tag> modifiedTags = new ArrayList<>(Arrays.asList(백엔드, Javascript, Nodejs));
        course.updateTag(modifiedTags);

        assertThat(course.getTags()).isEqualTo(modifiedTags);
    }

}