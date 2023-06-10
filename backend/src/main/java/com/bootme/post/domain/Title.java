package com.bootme.post.domain;

import com.bootme.common.exception.ValidationException;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;

import static com.bootme.common.exception.ErrorType.POST_TITLE_EMPTY;
import static com.bootme.common.exception.ErrorType.POST_TITLE_MAX_LENGTH;

@Getter
@Embeddable
public class Title {

    private static final int MAX_TITLE_LENGTH = 300;

    @Column(name = "title", nullable = false, length = 500)
    private String value;

    protected Title() {
    }

    public Title(String value) {
        validate(value);
        this.value = value;
    }

    private void validate(String value) {
        if (value == null || value.isBlank()) {
            throw new ValidationException(POST_TITLE_EMPTY, value);
        }
        if (value.length() > MAX_TITLE_LENGTH) {
            throw new ValidationException(POST_TITLE_MAX_LENGTH, value);
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Title)) {
            return false;
        }
        Title title = (Title) o;
        return Objects.equals(value, title.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

}
