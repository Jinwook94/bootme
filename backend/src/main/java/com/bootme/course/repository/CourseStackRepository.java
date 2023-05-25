package com.bootme.course.repository;

import com.bootme.course.domain.CourseStack;
import com.bootme.stack.domain.Stack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CourseStackRepository extends JpaRepository<CourseStack, Long> {

    @Query("SELECT cs.stack FROM CourseStack cs WHERE cs.course.id = :courseId")
    List<Stack> findStacksByCourseId(@Param("courseId") Long courseId);

}
