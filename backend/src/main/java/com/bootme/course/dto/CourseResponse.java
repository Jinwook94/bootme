package com.bootme.course.dto;

import com.bootme.course.domain.*;
import com.bootme.stack.domain.Stack;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@Getter
public class CourseResponse {

    private Long id;
    private String title;
    private String name;
    private int generation;
    private String url;
    private String location;
    private List<String> superCategories;
    private List<String> subCategories;
    private List<String> languages;
    private List<String> frameworks;
    private int cost;
    private int period;
    private Dates dates;
    private CompanyResponse company;
    private boolean isRecommended;
    private boolean isFree;
    private boolean isKdt;
    private boolean isOnline;
    private boolean isTested;
    private boolean isPrerequisiteRequired;
    private boolean isRegisterOpen;
    private int clicks;
    private int bookmarks;
    private long createdAt;
    private long modifiedAt;

    public CourseResponse() {
    }

    @Builder
    public CourseResponse(Long id, String name, int generation, String title, String url, String location,
                          List<String> superCategories, List<String> subCategories, List<String> languages, List<String> frameworks,
                          int cost, int period, Dates dates, boolean isRecommended, boolean isFree, boolean isKdt,
                          boolean isOnline, boolean isTested, boolean isPrerequisiteRequired, boolean isRegisterOpen,
                          int clicks, int bookmarks, long createdAt, long modifiedAt, CompanyResponse company) {
        this.id = id;
        this.name = name;
        this.generation = generation;
        this.title = title;
        this.url = url;
        this.location = location;
        this.superCategories = superCategories;
        this.subCategories = subCategories;
        this.languages = languages;
        this.frameworks = frameworks;
        this.cost = cost;
        this.period = period;
        this.dates = dates;
        this.isRecommended = isRecommended;
        this.isFree = isFree;
        this.isKdt = isKdt;
        this.isOnline = isOnline;
        this.isTested = isTested;
        this.isPrerequisiteRequired = isPrerequisiteRequired;
        this.isRegisterOpen = isRegisterOpen;
        this.clicks = clicks;
        this.bookmarks = bookmarks;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
        this.company = company;
    }

    public static CourseResponse of(Course course, List<Stack> stacks) {
        return CourseResponse.builder()
                .id(course.getId())
                .name(course.getName())
                .generation(course.getGeneration())
                .title(course.getTitle())
                .url(course.getUrl())
                .location(course.getLocation())
                .superCategories(course.getCategories().getSuperCategories())
                .subCategories(course.getCategories().getSubCategories())
                .languages(Stack.getLanguages(stacks))
                .frameworks(Stack.getFrameworks(stacks))
                .cost(course.getCost())
                .period(course.getPeriod())
                .dates(course.getDates())
                .isRecommended(course.isRecommended())
                .isFree(course.isFree())
                .isKdt(course.isKdt())
                .isOnline(course.isOnline())
                .isTested(course.isTested())
                .isPrerequisiteRequired(course.isPrerequisiteRequired())
                .isRegisterOpen(course.isRegisterOpen())
                .clicks(course.getClicks())
                .bookmarks(course.getBookmarks())
                .createdAt(convertLocalDateTimeToLong(course.getCreatedAt()))
                .modifiedAt(convertLocalDateTimeToLong(course.getModifiedAt()))
                .company(CompanyResponse.of(course.getCompany()))
                .build();
    }

    private static long convertLocalDateTimeToLong(LocalDateTime localDateTime){
        ZonedDateTime zonedDateTime = localDateTime.atZone(ZoneId.of("Asia/Seoul"));
        return zonedDateTime.toInstant().toEpochMilli();
    }

}
