package com.bootme.post.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.common.exception.AuthenticationException;
import com.bootme.member.domain.Member;
import com.bootme.post.dto.PostRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static com.bootme.common.exception.ErrorType.NOT_WRITER;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Post extends BaseEntity implements Votable {

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

    private int views;

    private int bookmarks;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private PostStatus status;

    @Builder
    public Post(Member member, String topic, String title, String content,
                int likes, int views, int bookmarks, PostStatus status) {
        this.member = member;
        this.topic = topic;
        this.title = new Title(title);
        this.content = new Content(content);
        this.status = status;
        this.likes = likes;
        this.views = views;
        this.bookmarks = bookmarks;
    }

    public static Post of(PostRequest postRequest, Member member) {
        return Post.builder()
                .member(member)
                .topic(postRequest.getTopic())
                .title(postRequest.getTitle())
                .content(postRequest.getContent())
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

    public void validateWriter(Long memberId, Long writerId) {
        if (!Objects.equals(memberId, writerId)) {
            throw new AuthenticationException(NOT_WRITER, String.valueOf(memberId));
        }
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

}
