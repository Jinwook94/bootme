package com.bootme.course.dto;

import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
public class CourseDetailRequest {

    @NotNull
    private String detail;

    public CourseDetailRequest() {
    }

    public CourseDetailRequest(String detail) {
        this.detail = detail;
    }
}
