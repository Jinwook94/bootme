package com.bootme.course.dto;

import com.bootme.course.domain.Course;
import com.bootme.course.domain.Dates;
import com.bootme.course.domain.Tag;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Getter
public class CourseResponse {

    private Long id;
    private String title;
    private String name;
    private int generation;
    private String url;
    private String location;
    private String onOffline;
    private List<Tag> tags;
    private String prerequisites;
    private int cost;
    private String costType;
    private String period;
    private Dates dates;
    private CompanyResponse company;
    private boolean isRecommended;
    private boolean isTested;
    private boolean isRegisterOpen;

    public CourseResponse() {
    }

    @Builder
    public CourseResponse(Long id, String name, int generation, String title, String url,
                          String location, String onOffline, List<Tag> tags, String prerequisites,
                          int cost, String costType, String period, Dates dates, boolean isRecommended,
                          boolean isTested, boolean isRegisterOpen, CompanyResponse company) {
        this.id = id;
        this.name = name;
        this.generation = generation;
        this.title = title;
        this.url = url;
        this.location = location;
        this.onOffline = onOffline;
        this.tags = tags;
        this.prerequisites = prerequisites;
        this.cost = cost;
        this.costType = costType;
        this.period = period;
        this.dates = dates;
        this.isRecommended = isRecommended;
        this.isTested = isTested;
        this.isRegisterOpen = isRegisterOpen;
        this.company = company;
    }

    public static CourseResponse of(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .generation(course.getGeneration())
                .title(course.getTitle())
                .url(course.getUrl())
                .location(course.getLocation())
                .onOffline(course.getOnoffline().name())
                .tags(course.getTags())
                .prerequisites(course.getPrerequisites().name())
                .cost(course.getCost())
                .costType(course.getCostType().name())
                .period(course.getPeriod())
                .dates(course.getDates())
                .isRecommended(course.isRecommended())
                .isTested(course.isTested())
                .isRegisterOpen(course.isRegisterOpen())
                .company(CompanyResponse.of(course.getCompany()))
                .build();
    }
}
