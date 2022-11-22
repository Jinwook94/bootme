package com.bootme.course.dto;

import com.bootme.course.domain.Company;
import com.bootme.course.domain.Tag;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Getter
public class CourseRequest {

    @NotBlank(message = "url을 입력해주세요.")
    private String url;

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;

    @NotBlank(message = "회사를 입력해주세요.")
    private Company company;

    @NotBlank(message = "장소를 입력해주세요.")
    private String location;

    private List<Tag> tags;

    public CourseRequest() {
    }

    @Builder
    public CourseRequest(String url,
                         String title,
                         Company company,
                         String location,
                         List<Tag> tags) {
        this.url = url;
        this.title = title;
        this.company = company;
        this.location = location;
        this.tags = tags;
    }

}
