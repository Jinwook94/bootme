package com.bootme.stack.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
public class StackRequest {

    private String name;
    private String type;
    private String icon;

    @Builder
    public StackRequest(String name, String type, String icon) {
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

}
