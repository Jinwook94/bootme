package com.bootme.course.service;

import com.querydsl.core.types.Predicate;
import org.springframework.util.MultiValueMap;

public interface CoursePredicate {

    Predicate build(MultiValueMap<String, String> params);

}
