package com.bootme.post.domain;

import com.bootme.comment.domain.Comment;
import com.bootme.common.domain.BaseEntity;
import com.bootme.bookmark.domain.Bookmarkable;
import com.bootme.common.domain.Clickable;
import com.bootme.vote.domain.Votable;
import com.bootme.common.exception.AuthenticationException;
import com.bootme.member.domain.Member;
import com.bootme.post.dto.PostRequest;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

import static com.bootme.common.exception.ErrorType.NOT_WRITER;
import static jakarta.persistence.CascadeType.PERSIST;
import static jakarta.persistence.FetchType.LAZY;

/**
 * Indexes:
 *  - idx_likes_created (likes DESC, created_at DESC)
 *  - idx_created_likes (created_at DESC, likes DESC)
 *  - idx_status (status)
 */
@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "post")
public class Post extends BaseEntity implements Votable, Clickable, Bookmarkable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    private String topic;

    private Title title;

    private Content content;

    @OneToMany(mappedBy = "post", fetch = LAZY, cascade = PERSIST)
    private List<Comment> comments = new ArrayList<>();

    private int likes;

    private int clicks;

    private int bookmarks;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Builder
    public Post(Member member, String topic, String title, String content,
                int likes, int clicks, int bookmarks, PostStatus status) {
        this.member = member;
        this.topic = topic;
        this.title = new Title(title);
        this.content = new Content(content);
        this.status = status;
        this.likes = likes;
        this.clicks = clicks;
        this.bookmarks = bookmarks;
    }

    public static Post of(PostRequest postRequest, Member member) {
        return Post.builder()
                .member(member)
                .topic(postRequest.getTopic())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
                .clicks(0)
                .bookmarks(0)
                .status(PostStatus.DISPLAY)
                .build();
    }

    public void modifyPost(PostRequest postRequest) {
        this.topic = postRequest.getTopic();
        this.title = new Title(postRequest.getTitle());
        this.content = new Content(postRequest.getContent());
    }

    public void softDeletePost() {
        this.status = PostStatus.DELETED;
    }

    public void assertAuthor(Long supposedAuthorId) {
        if (!this.member.getId().equals(supposedAuthorId)) {
            throw new AuthenticationException(NOT_WRITER, String.valueOf(supposedAuthorId));
        }
    }

    public boolean isWriterOfPost(Member member) {
        return this.member.equals(member);
    }

    public String getWriterNickname() {
        String nickname = this.member.getNickname();
        String name = this.member.getName();
        if (nickname != null && !nickname.isEmpty()) {
            return nickname;
        } else {
            return name;
        }
    }

    @Override
    public void incrementLikes() {
        this.likes += 1;
    }

    @Override
    public void decrementLikes() {
        this.likes -= 1;
        }

    @Override
    public void incrementClicks() {
        this.clicks += 1;
    }

    @Override
    public void incrementBookmarks() {
        this.bookmarks += 1;
    }

}
