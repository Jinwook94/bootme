package com.bootme.bookmark.repository;

import com.bootme.bookmark.domain.PostBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PostBookmarkRepository extends JpaRepository<PostBookmark, Long> {

    boolean existsByBookmark_Member_IdAndPost_Id(Long memberId, Long postId);

    Optional<PostBookmark> findByBookmark_Member_IdAndPost_Id(Long memberId, Long postId);

    List<PostBookmark> findByBookmark_Id(Long bookmarkId);

}
