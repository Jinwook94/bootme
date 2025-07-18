package com.bootme.post.repository;

import com.bootme.post.domain.Post;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.bootme.post.domain.QPost.post;

@RequiredArgsConstructor
@Repository
public class PostQuerydslRepositoryImpl implements PostQuerydslRepository {

    private final JPAQueryFactory queryFactory;

    public List<Post> fetchPostsWithSearch(Pageable pageable, String search, OrderSpecifier<?> orderSpecifier, Predicate predicate) {
        BooleanExpression expression = generateMatchAgainstExpression(post.topic, post.title.value, post.content.value, search);
        return queryFactory
                .selectFrom(post)
                .where(expression.and(predicate))
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .orderBy(orderSpecifier)
                .fetch();
    }

    public long countPostsForSearch(String search, Predicate predicate) {
        BooleanExpression expression = generateMatchAgainstExpression(post.topic, post.title.value, post.content.value, search);

        JPAQuery<Long> countQuery = queryFactory
                .select(post.count())
                .from(post)
                .where(expression.and(predicate));

        Long result = countQuery.fetchOne();
        return result != null ? result : 0L;
    }

    // 아래 형태 쿼리문 생성
    // MATCH(p1_0.topic, p1_0.title, p1_0.content) AGAINST ('+searchQuery')
    private BooleanExpression generateMatchAgainstExpression(StringPath topic, StringPath title, StringPath content, String searchWord) {
        String searchWithWildcard = searchWord + "*";
        return Expressions.booleanTemplate(
                "function('match_against', {0}, {1}, {2}, {3})",
                topic, title, content, searchWithWildcard
        );
    }

}
