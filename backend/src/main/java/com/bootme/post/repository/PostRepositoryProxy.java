package com.bootme.post.repository;

import com.bootme.common.util.CustomPageImpl;
import com.bootme.post.domain.PostDocument;
import com.bootme.post.dto.PostResponse;
import lombok.RequiredArgsConstructor;

import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.*;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.stereotype.Component;

import java.util.List;

import static com.bootme.common.enums.SortOption.CREATED_AT;
import static com.bootme.common.enums.SortOption.LIKES;

@Component
@RequiredArgsConstructor
public class PostRepositoryProxy {

    private final PostElasticsearchRepository postElasticsearchRepository;

    @Cacheable(
            value = "posts",
            key = "(#topic == '' ? 'none' : #topic) + ':' + " +
                    "(#search == '' ? 'none' : #search) + ':' + " +
                    "#sort + ':' + #page + ':' + #size")
    public CustomPageImpl<PostResponse> getPostPage(int page, int size, String sort, String topic, String search) {
        Pageable pageable = getSortedPageable(page, size, sort);
        SearchPage<PostDocument> searchPage = postElasticsearchRepository.findAllPosts(topic, search, pageable);
        return mapToCustomPageImpl(searchPage);
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

    // Redis 데이터 수신시 역직렬화 문제로 인해 Page 타입이 아닌 CustomPageImpl 타입 반환
    private CustomPageImpl<PostResponse> mapToCustomPageImpl(SearchPage<PostDocument> searchPage) {
        List<PostResponse> postResponses = searchPage.getContent()
                                            .stream()
                                            .map(SearchHit::getContent)
                                            .map(PostResponse::fromPostDocument)
                                            .toList();

        return new CustomPageImpl<>(
                postResponses,
                searchPage.getPageable().getPageNumber(),
                searchPage.getPageable().getPageSize(),
                searchPage.getTotalElements()
        );
    }

}