package com.bootme.stack.domain;

import com.bootme.common.domain.BaseEntity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Stack extends BaseEntity {

    private static final List<String> LANGUAGES = Arrays.asList("JavaScript", "TypeScript", "Java", "Python", "Swift", "Kotlin");
    private static final List<String> FRAMEWORKS = Arrays.asList("React", "Vue.js", "Spring", "Node.js", "Django");

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stack_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    public Stack(String name, String type) {
        this.name = name;
        this.type = type;
    }

    public static Stack of(String stack){
        String type;

        if (LANGUAGES.contains(stack)) {
            type = "language";
        } else if (FRAMEWORKS.contains(stack)) {
            type = "framework";
        } else {
            throw new IllegalArgumentException("Invalid stack: " + stack);
        }

        return new Stack(stack, type);
    }

    public static List<String> getLanguages(List<Stack> stacks) {
        return classifyStacks(stacks, LANGUAGES);
    }

    public static List<String> getFrameworks(List<Stack> stacks) {
        return classifyStacks(stacks, FRAMEWORKS);
    }

    private static List<String> classifyStacks(List<Stack> stacks, List<String> identifiers) {
        List<String> classifiedStacks = new ArrayList<>();
        for (Stack stack : stacks) {
            if (identifiers.contains(stack.getName())) {
                classifiedStacks.add(stack.getName());
            }
        }
        return classifiedStacks;
    }

}
