package com.bootme.course.domain;

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

    private String url;

    @OneToMany(mappedBy = "company")
    private List<Course> courses = new ArrayList<>();

    @Builder
    public Company(String name, String url, List<Course> courses) {
        this.name = name;
        this.url = url;
        this.courses = courses;
    }

    public void updateName(String name){
        this.name = name;
    }

    public void updateUrl(String url){
        this.url = url;
    }

    public void addCourse(Course course) {
        courses.add(course);
        course.updateCompany(this);
    }

    public void deleteCourse(Course course){
        courses.remove(course);
    }
}