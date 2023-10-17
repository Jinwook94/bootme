package com.bootme.course.domain;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Embeddable
public class Categories {

    private static final List<String> SUPER_CATEGORIES = List.of("웹", "모바일 앱", "AI", "데이터", "데브옵스", "게임");
    private static final List<String> SUB_CATEGORIES = List.of("프론트엔드", "백엔드", "풀스택", "안드로이드", "iOS");

    @ElementCollection(fetch = FetchType.LAZY)
    @CollectionTable(name = "course_category", joinColumns = @JoinColumn(name = "course_id"))
    @Column(name = "name")
    private List<String> values;

    protected Categories() {}

    public Categories(List<String> categories) {
        this.values = new ArrayList<>(categories);
    }

    public List<String> getSuperCategories() {
        return classifyCategories(SUPER_CATEGORIES);
    }

    public List<String> getSubCategories() {
        return classifyCategories(SUB_CATEGORIES);
    }

    private List<String> classifyCategories(List<String> targetCategories) {
        List<String> filteredCategories = new ArrayList<>();
        for (String value : values) {
            if (targetCategories.contains(value)) {
                filteredCategories.add(value);
            }
        }
        return filteredCategories;
    }

}
