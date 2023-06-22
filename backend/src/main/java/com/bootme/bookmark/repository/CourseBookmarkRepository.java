package com.bootme.bookmark.repository;

import com.bootme.bookmark.domain.CourseBookmark;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseBookmarkRepository extends JpaRepository<CourseBookmark, Long> {

    boolean existsByBookmark_Member_IdAndCourse_Id(Long memberId, Long courseId);

    Optional<CourseBookmark> findByBookmark_Member_IdAndCourse_Id(Long memberId, Long courseId);

    List<CourseBookmark> findByBookmark_Id(Long bookmarkId);

}
