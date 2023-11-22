package com.bootme.bookmark.repository;

import com.bootme.bookmark.domain.CourseBookmark;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface CourseBookmarkRepository extends JpaRepository<CourseBookmark, Long> {

    boolean existsByBookmark_Member_IdAndCourse_Id(Long memberId, Long courseId);

    Optional<CourseBookmark> findByBookmark_Member_IdAndCourse_Id(Long memberId, Long courseId);

    List<CourseBookmark> findByBookmark_Id(Long bookmarkId);

    @Query("SELECT cb FROM CourseBookmark cb " +
            "JOIN FETCH cb.course c " +
            "JOIN FETCH cb.bookmark b " +
            "JOIN FETCH b.member m " +
            "WHERE c.dates.registrationStartDate <= :date " +
            "AND c.dates.registrationEndDate >= :date")
    List<CourseBookmark> findAllWithCourseAndMemberOnDate(LocalDate date);

}
