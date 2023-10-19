package com.bootme.course.dto;

import com.bootme.course.domain.Company;
import com.bootme.course.domain.Course;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CompanyResponse {

    private Long id;
    private String name;
    private String serviceName;
    private String url;
    private String serviceUrl;
    private String logoUrl;
    private List<String> courses;

    public CompanyResponse() {
    }

    @Builder
    public CompanyResponse(Long id, String name, String serviceName, String url,
                           String serviceUrl, String logoUrl, List<String> courses) {
        this.id = id;
        this.name = name;
        this.serviceName = serviceName;
        this.url = url;
        this.serviceUrl = serviceUrl;
        this.logoUrl = logoUrl;
        this.courses = courses;
    }

    public static CompanyResponse of(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .name(company.getName())
                .serviceName(company.getServiceName())
                .url(company.getUrl())
                .serviceUrl(company.getServiceUrl())
                .logoUrl(company.getLogoUrl())
                .courses(getCourseTitles(company))
                .build();
    }

    private static List<String> getCourseTitles(Company company) {
        return company.getCourses().stream()
                .map(Course::getTitle)
                .toList();
    }

}
