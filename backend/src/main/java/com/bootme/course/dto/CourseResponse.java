package com.bootme.course.dto;

import com.bootme.course.domain.Company;
import com.bootme.course.domain.Course;
import com.bootme.course.domain.Tag;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CourseResponse {

    private Long id;
    private String url;
    private String title;
    private String company;
    private String location;
    private List<Tag> tags;

    public CourseResponse() {
    }

    @Builder
    private CourseResponse(Long id, String url, String title, String company,
                           String location, List<Tag> tags) {
        this.id = id;
        this.url = url;
        this.title = title;
        this.company = company;
        this.location = location;
        this.tags = tags;
    }

    public static CourseResponse of(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .url(course.getUrl())
                .title(course.getTitle())
                .company(course.getCompany().getName())
                .location(course.getLocation())
                .tags(course.getTags())
                .build();
    }
}
