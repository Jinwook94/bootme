package com.bootme.bookmark.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.member.domain.Member;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "bookmark")
public class Bookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "bookmark__id")
    private Long id;

    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private BookmarkType type;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "course_bookmark_id")
    private CourseBookmark courseBookmark;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "post_bookmark_id")
    private PostBookmark postBookmark;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "comment_bookmark_id")
    private CommentBookmark commentBookmark;

    @Builder
    public Bookmark(BookmarkType type, Member member, CourseBookmark courseBookmark,
                    PostBookmark postBookmark, CommentBookmark commentBookmark) {
        this.type = type;
        this.member = member;
        this.courseBookmark = courseBookmark;
        this.postBookmark = postBookmark;
        this.commentBookmark = commentBookmark;
    }

}