package com.bootme.bookmark.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.post.domain.Comment;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "comment_bookmark")
public class CommentBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_bookmark_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "comment_id")
    private Comment comment;

    public CommentBookmark(Long id, Comment comment) {
        this.id = id;
        this.comment = comment;
    }

}
