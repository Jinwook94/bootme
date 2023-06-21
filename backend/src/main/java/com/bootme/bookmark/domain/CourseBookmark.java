package com.bootme.bookmark.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.course.domain.Course;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "course_bookmark")
public class CourseBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_bookmark_id")
    private Long id;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    @OneToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public CourseBookmark(Bookmark bookmark, Course course) {
        this.bookmark = bookmark;
        this.course = course;
    }

}
