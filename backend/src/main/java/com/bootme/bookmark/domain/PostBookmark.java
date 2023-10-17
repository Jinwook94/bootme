package com.bootme.bookmark.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.post.domain.Post;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "post_bookmark")
public class PostBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_bookmark_id")
    private Long id;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @JoinColumn(name = "bookmark_id")
    private Bookmark bookmark;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public PostBookmark(Bookmark bookmark, Post post) {
        this.bookmark = bookmark;
        this.post = post;
    }

}
