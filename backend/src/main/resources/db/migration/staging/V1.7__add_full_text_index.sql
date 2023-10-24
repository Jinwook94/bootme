-- FULLTEXT 인덱스 추가
ALTER TABLE post ADD FULLTEXT ft_index_topic_title_content (topic, title, content);