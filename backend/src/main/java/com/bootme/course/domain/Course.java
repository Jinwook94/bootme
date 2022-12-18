package com.bootme.course.domain;

import com.bootme.course.dto.CourseRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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

    private String name;

    private int generation;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private Company company;

    private String location;

    @Enumerated(EnumType.STRING)
    private OnOffline onoffline;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    private List<Tag> tags = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private Prerequisites prerequisites;

    private int cost;

    @Enumerated(EnumType.STRING)
    private CostType costType;

    private String period;

    @Embedded
    private Dates dates;

    private boolean isRecommended;

    private boolean isTested;

    @Builder
    public Course(String title, String name, int generation, String url, Company company,
                  String location, OnOffline onoffline, List<Tag> tags, Prerequisites prerequisites,
                  int cost, CostType costType, String period, Dates dates, boolean isRecommended, boolean isTested) {
        this.title = title;
        this.name = name;
        this.generation = generation;
        this.url = url;
        this.company = company;
        this.location = location;
        this.onoffline = onoffline;
        this.tags = tags;
        this.prerequisites = prerequisites;
        this.cost = cost;
        this.costType = costType;
        this.period = period;
        this.dates = dates;
        this.isRecommended = isRecommended;
        this.isTested = isTested;
    }

    public static Course of(CourseRequest courseRequest, Company company){
        return Course.builder()
                .title(courseRequest.getTitle())
                .name(courseRequest.getName())
                .generation(courseRequest.getGeneration())
                .url(courseRequest.getUrl())
                .company(company)
                .location(courseRequest.getLocation())
                .onoffline(Enum.valueOf(OnOffline.class, courseRequest.getOnOffline()))
                .tags(courseRequest.getTags())
                .prerequisites(Enum.valueOf(Prerequisites.class, courseRequest.getPrerequisites()))
                .cost(courseRequest.getCost())
                .costType(Enum.valueOf(CostType.class, courseRequest.getCostType()))
                .period(courseRequest.getPeriod())
                .dates(courseRequest.getDates())
                .isRecommended(courseRequest.isRecommended())
                .isTested(courseRequest.isTested())
                .build();
    }

    public void modifyCourse(CourseRequest courseRequest){
        this.title = courseRequest.getTitle();
        this.name = courseRequest.getName();
        this.generation = courseRequest.getGeneration();
        this.url = courseRequest.getUrl();
        this.location = courseRequest.getLocation();
        this.onoffline = Enum.valueOf(OnOffline.class, courseRequest.getOnOffline());
        this.tags = courseRequest.getTags();
        this.prerequisites = Enum.valueOf(Prerequisites.class, courseRequest.getPrerequisites());
        this.cost = courseRequest.getCost();
        this.costType = Enum.valueOf(CostType.class, courseRequest.getCostType());
        this.period = courseRequest.getPeriod();
        this.dates = courseRequest.getDates();
        this.isRecommended = courseRequest.isRecommended();
        this.isTested = courseRequest.isTested();
    }

    public boolean isRegisterOpen(){
        boolean afterStart = LocalDate.now().isEqual(dates.getRegistrationStartDate()) || LocalDate.now().isAfter(dates.getRegistrationStartDate());
        boolean beforeEnd = LocalDate.now().isEqual(dates.getRegistrationEndDate()) || LocalDate.now().isBefore(dates.getRegistrationEndDate());
        return afterStart && beforeEnd;
    }

}
