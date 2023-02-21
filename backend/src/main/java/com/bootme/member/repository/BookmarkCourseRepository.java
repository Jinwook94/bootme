package com.bootme.member.repository;

import com.bootme.member.domain.BookmarkCourse;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface BookmarkCourseRepository extends JpaRepository<BookmarkCourse, Long> {

    List<BookmarkCourse> findByMemberId(Long memberId);

    Optional<BookmarkCourse> findByMemberIdAndCourseId(Long memberId, Long courseId);

    boolean existsByMemberIdAndCourseId(Long memberId, Long courseId);

}
