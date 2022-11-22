package com.bootme.course.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Getter
public class CompanyRequest {

    @NotBlank(message = "회사 url을 입력해주세요.")
    private String url;

    @NotBlank(message = "회사명을 입력해주세요.")
    private String name;

    public CompanyRequest(){
    }

    @Builder
    public CompanyRequest(String url, String name) {
        this.url = url;
        this.name = name;
    }

}