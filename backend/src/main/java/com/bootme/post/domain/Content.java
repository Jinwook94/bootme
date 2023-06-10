package com.bootme.post.domain;

import com.bootme.common.exception.ValidationException;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Objects;

import static com.bootme.common.exception.ErrorType.*;

@Getter
@Embeddable
public class Content {

    private static final int MAX_CONTENT_LENGTH = 10000;
    private static final int EXCERPT_LENGTH = 200;

    @Lob
    @Column(name = "content", nullable = false)
    private String value;

    protected Content() {
    }

    public Content(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        // 최대 입력값 계산 수정 필요
        if (value.length() > MAX_CONTENT_LENGTH) {
            throw new ValidationException(POST_CONTENT_MAX_LENGTH, value);
        }
    }

    public String getExcerpt() {
        // 이미지 태그가 있다면 첫 번째 이미지 태그만 반환 -> 메서드 분리 필요
        Document doc = Jsoup.parse(value);
        Elements imgTags = doc.getElementsByTag("img");
        String firstImg;
        if (!imgTags.isEmpty()) {
            firstImg = imgTags.first().toString();
            return firstImg;
        }

        if (value.length() <= EXCERPT_LENGTH) {
            return value;
        } else {
            return value.substring(0, EXCERPT_LENGTH) + "...";
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Content)) {
            return false;
        }
        Content content = (Content) o;
        return Objects.equals(value, content.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
