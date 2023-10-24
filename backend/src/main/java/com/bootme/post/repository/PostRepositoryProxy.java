package com.bootme.post.repository;

import com.bootme.common.util.CustomPageImpl;
import com.bootme.post.domain.Post;
import com.bootme.post.dto.PostResponse;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bootme.common.enums.SortOption.CREATED_AT;
import static com.bootme.common.enums.SortOption.LIKES;
import static com.bootme.post.domain.QPost.post;

@Component
@RequiredArgsConstructor
public class PostRepositoryProxy {

    private final PostRepository postRepository;
    private final PostQuerydslRepositoryImpl customPostRepository;

    @Cacheable(
            value = "posts",
            key = "(#topic == '' ? 'none' : #topic) + ':' + " +
                    "(#search == '' ? 'none' : #search) + ':' + " +
                    "#sort + ':' + #page + ':' + #size")
    public CustomPageImpl<PostResponse> getPostPage(int page, int size, String sort, String topic, String search, Predicate predicate) {
        if (search != null && !search.isEmpty()) {
            return getPostPageWithSearch(page, size, sort, search, predicate);
        } else {
            return getPostPageWithoutSearch(page, size, sort, predicate);
        }
    }

    // LIKE 연산자의 한계로 Full-text Search 사용
    private CustomPageImpl<PostResponse> getPostPageWithSearch(int page, int size, String sort, String search, Predicate predicate) {
        Pageable pageable = getSearchSortedPageable(page, size, sort);
        List<Post> posts = customPostRepository.fetchPostsWithSearch(pageable, search, getOrderSpecifier(sort), predicate);
        long count = customPostRepository.countPostsForSearch(search, predicate);

        Page<Post> postsPage = new PageImpl<>(posts, pageable, count);
        return new CustomPageImpl<>(postsPage.map(postEntity -> PostResponse.of(postEntity, false, false)));
    }

    private CustomPageImpl<PostResponse> getPostPageWithoutSearch(int page, int size, String sort, Predicate predicate) {
        Pageable pageable = getSortedPageable(page, size, sort);
        return new CustomPageImpl<>(postRepository.findAll(predicate, pageable)
                .map(postEntity -> PostResponse.of(postEntity, false, false)));
    }

    private Pageable getSortedPageable(int page, int size, String sort) {
        Sort sorting;
        if (sort.equals(CREATED_AT.toString())) {
            sorting = Sort.by(
                    Sort.Order.desc(CREATED_AT.toString()),
                    Sort.Order.desc(LIKES.toString())
            );
        } else {
            sorting = Sort.by(
                    Sort.Order.desc(LIKES.toString()),
                    Sort.Order.desc(CREATED_AT.toString())
            );
        }
        return PageRequest.of(page - 1, size, sorting);
    }

    private Pageable getSearchSortedPageable(int page, int size, String sort) {
        Sort sorting;
        if (sort.equals(CREATED_AT.toString())) {
            sorting = Sort.by(
                    Sort.Order.desc("created_at"),
                    Sort.Order.desc(LIKES.toString())
            );
        } else {
            sorting = Sort.by(
                    Sort.Order.desc(LIKES.toString()),
                    Sort.Order.desc("created_at")
            );
        }
        return PageRequest.of(page - 1, size, sorting);
    }

    private OrderSpecifier<?> getOrderSpecifier(String sort) {
        if (sort.equals(CREATED_AT.toString())) {
            return post.createdAt.desc().nullsLast();
        } else if (sort.equals(LIKES.toString())) {
            return post.likes.desc().nullsLast();
        }
        return post.likes.desc().nullsLast();
    }

}