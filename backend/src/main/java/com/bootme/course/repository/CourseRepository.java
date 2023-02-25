package com.bootme.course.repository;

import com.bootme.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long> {

    @Modifying
    @Transactional
    @Query("UPDATE Course c SET c.clicks = c.clicks + 1 WHERE c.id = :id")
    void incrementClicks(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Course c SET c.bookmarks = c.bookmarks + 1 WHERE c.id = :id")
    void incrementBookmarks(@Param("id") Long id);

    @Modifying
    @Transactional
    @Query("UPDATE Course c SET c.isRegisterOpen = :isOpen WHERE c.id = :id")
    void updateIsRegisterOpen(@Param("id") Long id, @Param("isOpen") boolean isOpen);

}
