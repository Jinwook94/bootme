package com.bootme.course.dto;

import com.bootme.course.domain.*;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

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

    private Category categories;

    private Stack stacks;

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
                         String location, Category categories, Stack stacks, Integer cost,
                         Integer period, Dates dates, boolean recommended, boolean free, boolean kdt,
                         boolean online, boolean tested, boolean prerequisiteRequired) {
        this.title = title;
        this.name = name;
        this.generation = generation;
        this.url = url;
        this.companyName = companyName;
        this.location = location;
        this.categories = categories;
        this.stacks = stacks;
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
