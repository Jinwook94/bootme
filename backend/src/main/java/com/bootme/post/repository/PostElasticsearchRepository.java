package com.bootme.post.repository;

import com.bootme.post.domain.PostDocument;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.elasticsearch.core.document.Document;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.*;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

import static com.bootme.post.domain.PostStatus.DELETED;

@RequiredArgsConstructor
@Repository
public class PostElasticsearchRepository {

    private final ElasticsearchOperations elasticsearchOperations;
    private static final String INDEX_NAME_POST = "post";
    private static final String POST_ID = "postId";
    private static final String STATUS = "status";
    private static final String STATUS_DISPLAY = "DISPLAY";
    private static final String TOPIC = "topic";
    private static final String TITLE = "title";
    private static final String CONTENT = "content";

    public Optional<PostDocument> findById(String id) {
        return Optional.ofNullable(
                elasticsearchOperations.get(id, PostDocument.class, IndexCoordinates.of(INDEX_NAME_POST))
        );
    }

    public Optional<PostDocument> findByPostId(Long postId) {
        Criteria criteria = new Criteria(POST_ID).is(postId);
        CriteriaQuery query = new CriteriaQuery(criteria);
        PostDocument postDocument = Objects.requireNonNull(elasticsearchOperations
                .searchOne(query, PostDocument.class, IndexCoordinates.of(INDEX_NAME_POST))).getContent();

        return Optional.of(postDocument);
    }

    public void save(PostDocument postDocument) {
        elasticsearchOperations.save(postDocument, IndexCoordinates.of(INDEX_NAME_POST));
    }

    public void modifyPost(PostDocument postDocument) {
        Document document = Document.from(postDocument.toMap());

        UpdateQuery updateQuery = UpdateQuery.builder(postDocument.getId())
                                    .withDocument(document)
                                    .build();

        elasticsearchOperations.update(updateQuery, IndexCoordinates.of(INDEX_NAME_POST));
    }

    public void softDeletePost(PostDocument postDocument) {
        Optional<PostDocument> existingDocument = Optional.ofNullable(
                elasticsearchOperations.get(postDocument.getId(), PostDocument.class, IndexCoordinates.of(INDEX_NAME_POST)));

        existingDocument.ifPresent(doc -> {
            doc.setStatus(DELETED.toString());
            elasticsearchOperations.save(doc, IndexCoordinates.of(INDEX_NAME_POST));
        });
    }

    public SearchPage<PostDocument> findAllPosts(String topic, String search, Pageable pageable) {
        Criteria criteria = buildCriteria(topic, search);
        CriteriaQuery query = new CriteriaQuery(criteria);
        query.setTrackTotalHits(true);
        query.setPageable(pageable);

        SearchHits<PostDocument> searchHits = elasticsearchOperations.search(query, PostDocument.class, IndexCoordinates.of(INDEX_NAME_POST));
        return SearchHitSupport.searchPageFor(searchHits, pageable);
    }

    private Criteria buildCriteria(String topic, String search) {
        if (!topic.isEmpty() && !search.isEmpty()) {
            return createStatusCriteria().subCriteria(createTopicAndSearchCriteria(topic, search));
        } else if (!topic.isEmpty()) {
            return createStatusCriteria().subCriteria(createTopicCriteria(topic));
        } else if (!search.isEmpty()){
            return createStatusCriteria().subCriteria(createSearchCriteria(search));
        } else {
            return new Criteria().subCriteria(createStatusCriteria());
        }
    }

    private Criteria createStatusCriteria() {
        return new Criteria(STATUS).is(STATUS_DISPLAY);
    }

    private Criteria createTopicAndSearchCriteria(String topic, String search) {
        Criteria topicCriteria = createTopicCriteria(topic);
        Criteria searchCriteria = createSearchCriteria(search);
        return topicCriteria.subCriteria(searchCriteria);
    }

    private Criteria createTopicCriteria(String topic) {
        return new Criteria(TOPIC).is(topic);
    }

    private Criteria createSearchCriteria(String search) {
        String[] searchWords = search.split("\\s+");

        Criteria searchCriteria = new Criteria();
        if (searchWords.length > 0) {
            // 첫 번째 단어에 대한 criteria 설정
            searchCriteria = new Criteria().subCriteria(
                    new Criteria(TOPIC).contains(searchWords[0])
                            .or(new Criteria(TITLE).contains(searchWords[0]))
                            .or(new Criteria(CONTENT).contains(searchWords[0]))
            );
            // "AND" 조건을 위해 나머지 단어 criteria 들을 subCriteria 사용하여 연결
            for (int i = 1; i < searchWords.length; i++) {
                String word = searchWords[i];
                searchCriteria = searchCriteria.subCriteria(
                        new Criteria().subCriteria(
                                new Criteria(TOPIC).contains(word)
                                        .or(new Criteria(TITLE).contains(word))
                                        .or(new Criteria(CONTENT).contains(word))
                        )
                );
            }
        }
        return searchCriteria;
    }

}
