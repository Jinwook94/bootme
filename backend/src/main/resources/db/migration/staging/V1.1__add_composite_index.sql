CREATE INDEX idx_likes_created ON post (likes DESC, created_at DESC);
CREATE INDEX idx_created_likes ON post (created_at DESC, likes DESC);