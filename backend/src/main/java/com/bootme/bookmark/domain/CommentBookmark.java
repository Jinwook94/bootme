package com.bootme.bookmark.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.comment.domain.Comment;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "comment_bookmark")
public class CommentBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_bookmark_id")
    private Long id;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    @OneToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public CommentBookmark(Bookmark bookmark, Comment comment) {
        this.bookmark = bookmark;
        this.comment = comment;
    }

}
