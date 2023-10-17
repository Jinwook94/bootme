package com.bootme.course.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

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
