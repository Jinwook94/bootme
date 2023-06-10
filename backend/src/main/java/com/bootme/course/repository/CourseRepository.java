package com.bootme.course.repository;

import com.bootme.course.domain.Course;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, QuerydslPredicateExecutor<Course> {

    boolean existsById(Long id);

    boolean existsByTitle(String title);

    Page<Course> findByIdIn(List<Long> ids, Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Course c SET c.isRegisterOpen = CASE WHEN (CURRENT_DATE >= c.dates.registrationStartDate AND CURRENT_DATE <= c.dates.registrationEndDate) THEN true ELSE false END")
    int updateAllCoursesRegistrationStatus();

}
