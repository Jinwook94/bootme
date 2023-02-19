package com.bootme.course.dto;

import com.bootme.course.domain.*;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.List;
import java.util.Map;

@Getter
public class CourseResponse {

    private Long id;
    private String title;
    private String name;
    private int generation;
    private String url;
    private String location;
    private String onOffline;
    private Map<String, List<String>> categories;
    private Map<String, List<String>> stacks;
    private String prerequisites;
    private int cost;
    private String costType;
    private int period;
    private Dates dates;
    private CompanyResponse company;
    private boolean isRecommended;
    private boolean isTested;
    private boolean isRegisterOpen;
    private int clicks;
    private int bookmarks;
    private long createdAt;
    private long modifiedAt;

    public CourseResponse() {
    }

    @Builder
    public CourseResponse(Long id, String name, int generation, String title, String url,
                          String location, String onOffline, Map<String, List<String>> categories,
                          Map<String, List<String>> stacks, String prerequisites, int cost, String costType,
                          int period, Dates dates, boolean isRecommended, boolean isTested, boolean isRegisterOpen,
                          int clicks, int bookmarks, long createdAt, long modifiedAt, CompanyResponse company) {
        this.id = id;
        this.name = name;
        this.generation = generation;
        this.title = title;
        this.url = url;
        this.location = location;
        this.onOffline = onOffline;
        this.categories = categories;
        this.stacks = stacks;
        this.prerequisites = prerequisites;
        this.cost = cost;
        this.costType = costType;
        this.period = period;
        this.dates = dates;
        this.isRecommended = isRecommended;
        this.isTested = isTested;
        this.isRegisterOpen = isRegisterOpen;
        this.clicks = clicks;
        this.bookmarks = bookmarks;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
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
                .onOffline(course.getOnoffline())
                .categories(course.categoryToMap())
                .stacks(course.stackToMap())
                .prerequisites(course.getPrerequisites())
                .cost(course.getCost())
                .costType(course.getCostType())
                .period(course.getPeriod())
                .dates(course.getDates())
                .isRecommended(course.isRecommended())
                .isTested(course.isTested())
                .isRegisterOpen(course.isRegisterOpen())
                .clicks(course.getClicks())
                .bookmarks(course.getBookmarks())
                .createdAt(convertLocalDateTimeToLong(course.getCreatedAt()))
                .modifiedAt(convertLocalDateTimeToLong(course.getModifiedAt()))
                .company(CompanyResponse.of(course.getCompany()))
                .build();
    }

    private static long convertLocalDateTimeToLong(LocalDateTime localDateTime){
        return localDateTime.toInstant(ZoneOffset.ofHours(9)).toEpochMilli();
    }

}
