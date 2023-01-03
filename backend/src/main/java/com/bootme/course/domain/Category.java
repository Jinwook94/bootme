package com.bootme.course.domain;

import lombok.Builder;
import lombok.Getter;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import java.util.ArrayList;
import java.util.List;

@Getter
@Embeddable
public class Category {

    @ElementCollection
    private List<String> superCategory = new ArrayList<>();

    @ElementCollection
    private List<String> subCategory = new ArrayList<>();

    public Category() {}

    @Builder
    public Category(List<String> superCategory, List<String> subCategory) {
        this.superCategory = superCategory;
        this.subCategory = subCategory;
    }

}
