package com.bootme.course.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;

@Getter
public class CompanyRequest {

    @NotBlank(message = "회사명을 입력해주세요.")
    private String name;

    @NotBlank(message = "회사 서비스명을 입력해주세요.")
    private String serviceName;

    @NotBlank(message = "회사 URL을 입력해주세요.")
    private String url;

    @NotBlank(message = "회사 서비스의 URL을 입력해주세요.")
    private String serviceUrl;

    @NotBlank(message = "회사 로고 이미지 URL을 입력해주세요.")
    private String logoUrl;

    public CompanyRequest(){
    }

    @Builder
    public CompanyRequest(String name, String serviceName, String url,
                          String serviceUrl, String logoUrl) {
        this.name = name;
        this.serviceName = serviceName;
        this.url = url;
        this.serviceUrl = serviceUrl;
        this.logoUrl = logoUrl;
    }

}
