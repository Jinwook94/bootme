package com.bootme.member.dto;

import lombok.Getter;

@Getter
public class UpdateImageRequest {

    private String profileImage;

    public UpdateImageRequest() {
    }

    public UpdateImageRequest(String profileImage) {
        this.profileImage = profileImage;
    }

}
