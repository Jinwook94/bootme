package com.bootme.comment.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.vote.domain.Votable;
import com.bootme.common.exception.AuthenticationException;
import com.bootme.common.exception.ValidationException;
import com.bootme.member.domain.Member;
import com.bootme.post.domain.Post;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.bootme.common.exception.ErrorType.*;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Comment extends BaseEntity implements Votable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private Long id;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "parent_id")
    private Comment parent;

    @OneToMany(mappedBy = "parent", cascade = PERSIST)
    private List<Comment> children = new ArrayList<>();

    @Lob
    @Column(columnDefinition = "TEXT")
    private String content;

    private int groupNum;

    private int levelNum;

    private int orderNum;

    private int likes;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CommentStatus status;

    @Builder
    public Comment(Post post, Member member, Comment parent, String content,
                   int groupNum, int levelNum, int orderNum) {
        validateContent(content);
        this.post = post;
        this.member = member;
        this.parent = parent;
        this.content = content;
        this.groupNum = groupNum;
        this.levelNum = levelNum;
        this.orderNum = orderNum;
        this.status = CommentStatus.DISPLAY;
        if (parent != null) {
            parent.getChildren().add(this);
        }
    }

    private static final int MAX_CONTENT_LENGTH = 2000;

    // todo: 최대 입력값 계산 수정 필요
    private void validateContent(String content) {
        Document doc = Jsoup.parse(content);
        Elements allElements = doc.getAllElements();

        boolean isEmpty = allElements.stream().allMatch(element ->
                element.ownText().trim().isEmpty() &&
                        element.children().stream().allMatch(child -> child.ownText().trim().isEmpty() && !Objects.equals(child.tagName(), "img"))
        );

        if (isEmpty) {
            throw new ValidationException(COMMENT_EMPTY, content);
        }
        if (content.length() > MAX_CONTENT_LENGTH) {
            throw new ValidationException(POST_COMMENT_MAX_LENGTH, content);
        }
    }

    public void assertAuthor(Long supposedAuthorId) {
        if (!this.member.getId().equals(supposedAuthorId)) {
            throw new AuthenticationException(NOT_WRITER, String.valueOf(supposedAuthorId));
        }
    }

    public boolean isWriterOfComment(Member member) {
        return this.member.equals(member);
    }

    public Optional<Long> getParentId() {
        return Optional.ofNullable(parent).map(Comment::getId);
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

    public void modifyContent(String content) {
        this.content = content;
    }

    public void softDeleteComment() {
        this.status = CommentStatus.DELETED;
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
