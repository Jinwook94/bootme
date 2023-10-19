package com.bootme.stack.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.course.domain.CourseStack;
import com.bootme.member.domain.MemberStack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Stack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "stack_id")
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String type;

    @Column(nullable = false, length = 5000)
    private String icon;

    @OneToMany(mappedBy = "stack", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<MemberStack> memberStacks = new ArrayList<>();

    @OneToMany(mappedBy = "stack", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<CourseStack> courseStacks = new ArrayList<>();

    public Stack(String name, String type, String icon) {
        this.name = name;
        this.type = type;
        this.icon = icon;
    }

    public static Stack of(String stack, String icon, List<String> languages, List<String> frameworks){
        String type;

        if (languages.contains(stack)) {
            type = "language";
        } else if (frameworks.contains(stack)) {
            type = "framework";
        } else {
            throw new IllegalArgumentException("Invalid stack: " + stack);
        }

        return new Stack(stack, type, icon);
    }

    public static List<String> getLanguages(List<Stack> stacks) {
        return stacks.stream()
                .filter(stack -> stack.getType().equals("language"))
                .map(Stack::getName)
                .toList();
    }

    public static List<String> getFrameworks(List<Stack> stacks) {
        return stacks.stream()
                .filter(stack -> stack.getType().equals("framework"))
                .map(Stack::getName)
                .toList();
    }

}
