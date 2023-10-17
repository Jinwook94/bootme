package com.bootme.course.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.stack.domain.Stack;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "course_stack")
public class CourseStack extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_stack_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "course_id")
    private Course course;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "stack_id")
    private Stack stack;

    @Builder
    public CourseStack(Course course, Stack stack) {
        this.course = course;
        this.stack = stack;
    }

}
