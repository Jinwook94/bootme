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
    private String url;
    private String companyName;
    private String location;
    private int cost;
    private String costType;
    private Dates dates;
    private String onOffline;
    private List<Tag> tags;
    private String prerequisites;
    private boolean recommended;
    private boolean tested;

    public CourseResponse() {
    }

    @Builder
    public CourseResponse(Long id, String title, String url, String companyName,
                          String location, int cost, String costType, Dates dates,
                          String onOffline, List<Tag> tags, String prerequisites,
                          boolean recommended, boolean tested) {
        this.id = id;
        this.title = title;
        this.url = url;
        this.companyName = companyName;
        this.location = location;
        this.cost = cost;
        this.costType = costType;
        this.dates = dates;
        this.onOffline = onOffline;
        this.tags = tags;
        this.prerequisites = prerequisites;
        this.recommended = recommended;
        this.tested = tested;
    }



    public static CourseResponse of(Course course) {
        return CourseResponse.builder()
                .id(course.getId())
                .title(course.getTitle())
                .url(course.getUrl())
                .companyName(course.getCompany().getName())
                .location(course.getLocation())
                .cost(course.getCost())
                .costType(course.getCostType().name())
                .dates(course.getDates())
                .onOffline(course.getOnoffline().name())
                .tags(course.getTags())
                .prerequisites(course.getPrerequisites().name())
                .recommended(course.isRecommended())
                .tested(course.isTested())
                .build();
    }
}
