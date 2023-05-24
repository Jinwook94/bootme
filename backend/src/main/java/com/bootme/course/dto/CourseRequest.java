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

    @NotBlank(message = "강의 공간을 입해주세요 - 온라인, 오프라인, 온오프라인혼합")
    private String onOffline;

    private Category categories;

    private Stack stacks;

    @NotBlank(message = "선수 조건을 입력해주세요")
    private String prerequisites;

    @NotNull(message = "코스 참여 비용을 입력해주세요.")
    private Integer cost;

    @NotBlank(message = "코스 비용 타입을 입력해주세요. - 무료, 무료국비, 유료")
    private String costType;

    @NotNull(message = "코스 진행 기간을 입력해주세요")
    private Integer period;

    private Dates dates;

    private boolean recommended;

    private boolean tested;

    public CourseRequest() {
    }

    @Builder
    public CourseRequest(String title, String name, Integer generation, String url, String companyName,
                         String location, String onOffline, Category categories, Stack stacks, String prerequisites,
                         Integer cost, String costType, Integer period, Dates dates, boolean recommended, boolean tested) {
        this.title = title;
        this.name = name;
        this.generation = generation;
        this.url = url;
        this.companyName = companyName;
        this.location = location;
        this.onOffline = onOffline;
        this.categories = categories;
        this.stacks = stacks;
        this.prerequisites = prerequisites;
        this.cost = cost;
        this.costType = costType;
        this.period = period;
        this.dates = dates;
        this.recommended = recommended;
        this.tested = tested;
    }
}
