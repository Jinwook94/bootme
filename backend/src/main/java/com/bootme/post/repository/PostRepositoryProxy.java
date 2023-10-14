package com.bootme.post.repository;

import com.bootme.common.util.CustomPageImpl;
import com.bootme.post.dto.PostResponse;
import com.querydsl.core.types.Predicate;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import static com.bootme.common.enums.SortOption.CREATED_AT;
import static com.bootme.common.enums.SortOption.LIKES;

@Component
@RequiredArgsConstructor
public class PostRepositoryProxy {

    private final PostRepository postRepository;

    @Cacheable(
            value = "posts",
            key = "(#topic == '' ? 'none' : #topic) + ':' + " +
                    "(#search == '' ? 'none' : #search) + ':' + " +
                    "#sort + ':' + #page + ':' + #size")
    public CustomPageImpl<PostResponse> getPostPage(int page, int size, String sort, String topic, String search, Predicate predicate) {
        Pageable pageable = getSortedPageable(page, size, sort);
        return new CustomPageImpl<>(postRepository.findAll(predicate, pageable)
                .map(post -> PostResponse.of(post, false, false)));
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
        return PageRequest.of(page-1, size, sorting);
    }

}
