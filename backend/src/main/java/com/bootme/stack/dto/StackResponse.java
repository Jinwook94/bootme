package com.bootme.stack.dto;

import com.bootme.stack.domain.Stack;
import lombok.Builder;
import lombok.Getter;

@Getter
public class StackResponse {

    private String name;
    private String type;
    private String icon;

    @Builder
    public StackResponse(String name, String type, String icon) {
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    public static StackResponse of(Stack stack) {
        return StackResponse.builder()
                .name(stack.getName())
                .type(stack.getType())
                .icon(stack.getIcon())
                .build();
    }

}
