package com.bootme.course.dto;

import com.bootme.course.domain.*;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

@Getter
public class CourseRequest {

    @NotBlank(message = "커리큘럼 타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "커리큘럼 url을 입력해주세요.")
    private String url;

    @NotBlank(message = "커리큘럼 개설 회사를 입력해주세요")
    private String companyName;

    @NotBlank(message = "장소를 입력해주세요.")
    private String location;

    @NotNull(message = "커리큘럼 참여 비용을 입력해주세요.")
    private int cost;

    @NotBlank(message = "커리큘럼 비용 타입을 입력해주세요. - 무료, 무료국비, 유료")
    private String costType;

    private Dates dates;

    @NotBlank(message = "강의 공간을 입해주세요 - 온라인, 오프라인, 온오프라인혼합")
    private String onOffline;

    private List<Tag> tags;

    @NotBlank(message = "선수 조건을 입력해주세요")
    private String prerequisites;

    @NotNull(message = "추천 커리큘럼 여부를 입력해주세요")
    private boolean recommended;

    @NotNull(message = "코딩테스트 진행 여부를 입력해주세요")
    private boolean tested;


    public CourseRequest() {
    }

    @Builder
    public CourseRequest(String title, String url, String companyName,
                         String location, int cost, String costType,
                         Dates dates, String onOffline, List<Tag> tags,
                         String prerequisites, boolean recommended, boolean tested) {
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

}
