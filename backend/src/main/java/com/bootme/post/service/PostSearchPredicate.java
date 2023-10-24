package com.bootme.post.service;

import com.bootme.common.exception.ValidationException;
import com.bootme.post.domain.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.util.List;

import static com.bootme.common.exception.ErrorType.INVALID_SEARCH_QUERY;

@Component
public class PostSearchPredicate implements PostPredicate {

    public Predicate build(MultiValueMap<String, String> params) {

        List<String> searchParams = params.get("search");
        if(searchParams == null || searchParams.isEmpty() || searchParams.get(0).isEmpty()) {
            return null;
        }

        String searchParam = searchParams.get(0).trim();
        validateSearchQuery(searchParam);

        QPost post = QPost.post;

        BooleanBuilder builder = new BooleanBuilder();

        builder.or(post.topic.contains(searchParam.toLowerCase()));
        builder.or(post.title.value.contains(searchParam.toLowerCase()));
        builder.or(post.content.value.contains(searchParam.toLowerCase()));

        return builder.getValue();
    }

    private void validateSearchQuery(String searchParam) {
        int maxQueryLength = 255;
        if (searchParam.length() > maxQueryLength) {
            throw new ValidationException(INVALID_SEARCH_QUERY, "최대 255자까지 입력할 수 있습니다. 입력값 = " + searchParam);
        }

        if (searchParam.matches(".*\\s+.*")) {
            throw new ValidationException(INVALID_SEARCH_QUERY, "띄어쓰기를 입력할 수 없습니다. 입력값 = " + searchParam);
        }

        String regex = "^[a-zA-Z0-9ㄱ-ㅎㅏ-ㅣ가-힣 ]*$";
        if (!searchParam.matches(regex)) {
            throw new ValidationException(INVALID_SEARCH_QUERY, "한글, 알파벳, 숫자만 검색 가능합니다. 입력값 = " + searchParam);
        }

        String numbersAndEnglish = "^[a-zA-Z0-9 ]*$";
        String korean = "^[ㄱ-ㅎㅏ-ㅣ가-힣 ]*$";
        if (searchParam.matches(numbersAndEnglish) && searchParam.length() < 2) {
            throw new ValidationException(INVALID_SEARCH_QUERY, "영문 또는 숫자는 2자 이상 검색 가능합니다. 입력값 = " + searchParam);
        } else if (searchParam.matches(korean) && searchParam.length() < 2) {
            throw new ValidationException(INVALID_SEARCH_QUERY, "한글은 2자 이상 검색 가능합니다. 입력값 = " + searchParam);
        }
    }

}
