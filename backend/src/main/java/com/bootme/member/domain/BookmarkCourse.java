package com.bootme.member.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.course.domain.Course;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "bookmark_courses")
public class BookmarkCourse extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark_course_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    @Builder
    public BookmarkCourse(Member member, Course course) {
        this.member = member;
        this.course = course;
    }

}