package com.bootme.post.domain;

import com.bootme.common.exception.ValidationException;
import lombok.Getter;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Lob;
import java.util.Objects;

import static com.bootme.common.exception.ErrorType.*;

@Getter
@Embeddable
public class Content {

    private static final int MAX_CONTENT_LENGTH = 50000;
    private static final int EXCERPT_LENGTH = 200;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String value;

    protected Content() {
    }

    public Content(String value) {
        validate(value);
        this.value = removeSpanStyle(value);
    }

    private void validate(String value) {
        // 최대 입력값 계산 수정 필요
        if (value.length() > MAX_CONTENT_LENGTH) {
            throw new ValidationException(POST_CONTENT_MAX_LENGTH, value);
        }
    }

    // react-quill 디폴트로 긴 텍스트에 폰트 컬러 스타일을 적용하는데, 이 스타일을 제거
    private String removeSpanStyle(String value) {
        Document doc = Jsoup.parse(value);

        // 폰트 컬러 스타일 있는 span 선택
        Elements spanTags = doc.select("span[style=color: rgb(38, 55, 71);]");

        // 스타일 삭제
        for (Element span : spanTags) {
            span.removeAttr("style");
        }

        return doc.body().html();
    }

    public String getExcerpt() {
        if ("<p><br></p>".equals(value)) {
            return "";
        }

        Document doc = Jsoup.parse(value);
        Elements iframeTags = doc.getElementsByTag("iframe");
        Elements videoTags = doc.getElementsByTag("video");
        Elements imgTags = doc.getElementsByTag("img");

        String firstIframe;
        if (!iframeTags.isEmpty()) {
            firstIframe = iframeTags.first().toString();
            return firstIframe;
        }

        String firstVideo;
        if (!videoTags.isEmpty()) {
            firstVideo = videoTags.first().toString();
            return firstVideo;
        }

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
