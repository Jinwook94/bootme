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
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private String url;

    private String title;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String location;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Tag> tags = new ArrayList<>();

    @Builder
    public Course(String url, String title, Company company, String location, List<Tag> tags) {
        this.url = url;
        this.title = title;
        this.company = company;
        this.location = location;
        this.tags = tags;
    }

    public void updateUrl(String url){
        this.url = url;
    }

    public void updateTitle(String title){
        this.title = title;
    }

    public void updateLocation(String location){
        this.location = location;
    }

    public void updateTag(List<Tag> tags){
        this.tags = tags;
    }

}
