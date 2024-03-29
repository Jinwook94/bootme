package com.bootme.course.dto;

import com.bootme.course.domain.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CourseRequest {

    @NotBlank(message = "코스 타이틀을 입력해주세요. (ex. 백엔드 부트캠프 4기")
    private String title;

    @NotBlank(message = "코스 이름을 입력해주세요. (ex. 백엔드 부트캠프)")
    private String name;

    @NotNull(message = "코스 회차를 입력해주세요.")
    private Integer generation;

    @NotBlank(message = "코스 URL을 입력해주세요.")
    private String url;

    @NotBlank(message = "코스 개설 회사를 입력해주세요")
    private String companyName;

    @NotBlank(message = "코스 진행 장소를 입력해주세요.")
    private String location;

    private List<String> superCategories = new ArrayList<>();

    private List<String> subCategories = new ArrayList<>();

    private List<String> languages = new ArrayList<>();

    private List<String> frameworks = new ArrayList<>();

    @NotNull(message = "코스 참여 비용을 입력해주세요.")
    private Integer cost;

    @NotNull(message = "코스 진행 기간을 입력해주세요")
    private Integer period;

    private Dates dates;

    private boolean recommended;

    private boolean free;

    private boolean kdt;

    private boolean online;

    private boolean tested;

    private boolean prerequisiteRequired;

    public CourseRequest() {
    }

    @Builder
    public CourseRequest(String title, String name, Integer generation, String url, String companyName,
                         String location, List<String> superCategories, List<String> subCategories, List<String> languages,
                         List<String> frameworks, Integer cost, Integer period, Dates dates, boolean recommended,
                         boolean free, boolean kdt, boolean online, boolean tested, boolean prerequisiteRequired) {
        this.title = title;
        this.name = name;
        this.generation = generation;
        this.url = url;
        this.companyName = companyName;
        this.location = location;
        this.superCategories = superCategories;
        this.subCategories = subCategories;
        this.languages = languages;
        this.frameworks = frameworks;
        this.cost = cost;
        this.period = period;
        this.dates = dates;
        this.recommended = recommended;
        this.free = free;
        this.kdt = kdt;
        this.online = online;
        this.tested = tested;
        this.prerequisiteRequired = prerequisiteRequired;
    }

}
