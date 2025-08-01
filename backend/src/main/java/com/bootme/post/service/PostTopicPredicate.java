package com.bootme.post.service;

import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.List;

import static com.bootme.post.domain.QPost.post;

@Component
public class PostTopicPredicate implements PostPredicate {

    public Predicate build(MultiValueMap<String, String> params) {

        List<String> topicParams = params.get("topic");
        if(topicParams == null || topicParams.isEmpty() || topicParams.get(0).isEmpty()) {
            return null;
        }
        String searchParam = topicParams.get(0);
        searchParam = URLDecoder.decode(searchParam, StandardCharsets.UTF_8);

        BooleanBuilder builder = new BooleanBuilder();
        builder.or(post.topic.contains(searchParam.toLowerCase()));

        return builder.getValue();
    }

}
