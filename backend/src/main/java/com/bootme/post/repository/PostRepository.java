package com.bootme.post.repository;

import com.bootme.post.domain.Post;
import com.bootme.post.domain.PostStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>, QuerydslPredicateExecutor<Post> {

    long countByStatus(PostStatus status);

}
