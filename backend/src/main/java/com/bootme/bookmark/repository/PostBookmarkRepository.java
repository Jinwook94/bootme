package com.bootme.bookmark.repository;

import com.bootme.bookmark.domain.PostBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {
    boolean existsByBookmark_Member_IdAndPost_Id(Long memberId, Long postId);
}
