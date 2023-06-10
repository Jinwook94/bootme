package com.bootme.post.service;

import com.querydsl.core.types.Predicate;
import org.springframework.util.MultiValueMap;

public interface PostPredicate {

    Predicate build(MultiValueMap<String, String> params);

}
