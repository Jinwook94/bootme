package com.bootme.bookmark.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.course.domain.Course;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "course_bookmark")
public class CourseBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_bookmark_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public CourseBookmark(Course course) {
        this.course = course;
    }

}
