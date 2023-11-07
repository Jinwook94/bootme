package com.bootme.course.repository;

import com.bootme.course.domain.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface CourseRepository extends JpaRepository<Course, Long>, QuerydslPredicateExecutor<Course> {

    boolean existsById(Long id);

    boolean existsByTitle(String title);

}
