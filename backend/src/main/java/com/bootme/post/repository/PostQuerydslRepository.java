package com.bootme.post.repository;

import com.bootme.post.domain.Post;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PostQuerydslRepository {

    List<Post> fetchPostsWithSearch(Pageable pageable, String search, OrderSpecifier<?> orderSpecifier, Predicate predicate);
    long countPostsForSearch(String search, Predicate predicate);

}
