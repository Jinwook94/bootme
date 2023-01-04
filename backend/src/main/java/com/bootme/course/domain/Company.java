package com.bootme.course.domain;

import com.bootme.course.dto.CompanyRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "company_id")
    private Long id;

    private String name;

    private String serviceName;

    private String url;

    private String serviceUrl;

    private String logoUrl;

    @OneToMany(mappedBy = "company")
    private List<Course> courses = new ArrayList<>();

    @Builder
    public Company(String name, String serviceName, String url, String serviceUrl, String logoUrl, List<Course> courses) {
        this.name = name;
        this.serviceName = serviceName;
        this.url = url;
        this.serviceUrl = serviceUrl;
        this.logoUrl = logoUrl;
        this.courses = courses;
    }

    public Long addCourse(Course course){
        course.modifyCompany(this);
        this.courses.add(course);
        return course.getId();
    }

    public void modifyCompany(CompanyRequest companyRequest){
        this.name = companyRequest.getName();
        this.serviceName = companyRequest.getServiceName();
        this.url = companyRequest.getUrl();
        this.serviceUrl = companyRequest.getServiceUrl();
        this.logoUrl = companyRequest.getLogoUrl();
    }

    public void deleteCourse(Course course){
        courses.remove(course);
    }
}
