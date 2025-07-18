-- PostgreSQL Full Text Search를 위한 GIN 인덱스 추가
CREATE INDEX idx_post_fulltext ON post USING GIN (to_tsvector('simple', COALESCE(topic, '') || ' ' || title || ' ' || content));
