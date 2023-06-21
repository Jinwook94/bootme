package com.bootme.bookmark.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.post.domain.Post;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
@Table(name = "post_bookmark")
public class PostBookmark extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_bookmark_id")
    private Long id;

    @OneToOne
    @JoinColumn(name = "post_id")
    private Post post;

    public PostBookmark(Post post) {
        this.post = post;
    }

}
