package com.bootme.course.domain;

import com.bootme.course.dto.CourseRequest;
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

    private String title;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private Company company;

    private String location;

    private int cost;

    @Enumerated(EnumType.STRING)
    private CostType costType;

    @Embedded
    private Dates dates;

    @Enumerated(EnumType.STRING)
    private OnOffline onoffline;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Tag> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Prerequisites prerequisites;

    private boolean isRecommended;

    private boolean isTested;

    @Builder
    public Course(String title, String url, Company company, String location, int cost,
                  CostType costType, Dates dates, OnOffline onoffline, List<Tag> tags,
                  Prerequisites prerequisites, boolean isRecommended, boolean isTested) {
        this.title = title;
        this.url = url;
        this.company = company;
        this.location = location;
        this.cost = cost;
        this.costType = costType;
        this.dates = dates;
        this.onoffline = onoffline;
        this.tags = tags;
        this.prerequisites = prerequisites;
        this.isRecommended = isRecommended;
        this.isTested = isTested;
    }

    public void modifyCourse(CourseRequest courseRequest){
        this.title = courseRequest.getTitle();
        this.url = courseRequest.getUrl();
        this.location = courseRequest.getLocation();
        this.cost = courseRequest.getCost();
        this.costType = Enum.valueOf(CostType.class, courseRequest.getCostType());
        this.dates = courseRequest.getDates();
        this.onoffline = Enum.valueOf(OnOffline.class, courseRequest.getOnOffline());
        this.tags = courseRequest.getTags();
        this.prerequisites = Enum.valueOf(Prerequisites.class, courseRequest.getPrerequisites());
        this.isRecommended = courseRequest.isRecommended();
        this.isTested = courseRequest.isTested();
    }

}
