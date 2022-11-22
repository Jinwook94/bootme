package com.bootme.course.dto;

import com.bootme.course.domain.Company;
import com.bootme.course.domain.Course;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CompanyResponse {

    private Long id;
    private String url;
    private String name;
    private List<Course> courses;

    public CompanyResponse() {
    }

    @Builder
    public CompanyResponse(Long id, String url, String name, List<Course> courses) {
        this.id = id;
        this.url = url;
        this.name = name;
        this.courses = courses;
    }

    public static CompanyResponse of(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .url(company.getUrl())
                .name(company.getName())
                .courses(company.getCourses())
                .build();
    }
}
