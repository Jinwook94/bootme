package com.bootme.course.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Embeddable
@Getter
public class Stack {

    @ElementCollection
    private List<String> languages = new ArrayList<>();

    @ElementCollection
    private List<String> frameworks = new ArrayList<>();

    public Stack() {}

    @Builder
    public Stack(List<String> languages, List<String> frameworks) {
        this.languages = languages;
        this.frameworks = frameworks;
    }

}
