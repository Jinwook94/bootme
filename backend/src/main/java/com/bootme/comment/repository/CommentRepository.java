package com.bootme.comment.repository;

import com.bootme.comment.domain.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Long>, QuerydslPredicateExecutor<Comment> {

    @Query("SELECT COALESCE(MAX(c.groupNum), 0) FROM Comment c WHERE c.post.id = :postId")
    int findMaxGroupByPost(@Param("postId") Long postId);

    List<Comment> findByPost_Id(Long postId);

}
