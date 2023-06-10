package com.bootme.post.service;

import com.bootme.post.domain.QPost;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Component
public class PostTopicPredicate implements PostPredicate {

    public Predicate build(MultiValueMap<String, String> params) {

        List<String> topicParams = params.get("topic");
        if(topicParams == null || topicParams.isEmpty() || topicParams.get(0).isEmpty()) {
            return null;
        }
        String searchParam = topicParams.get(0);
        searchParam = URLDecoder.decode(searchParam, StandardCharsets.UTF_8);

        QPost post = QPost.post;
        BooleanBuilder builder = new BooleanBuilder();
        builder.or(post.topic.containsIgnoreCase(searchParam));

        return builder.getValue();
    }

}
