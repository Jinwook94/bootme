    package com.bootme.bookmark.repository;

    import com.bootme.bookmark.domain.CourseBookmark;
    import org.springframework.data.jpa.repository.JpaRepository;

    public interface CourseBookmarkRepository extends JpaRepository<CourseBookmark, Long> {
        boolean existsByBookmark_Member_IdAndCourse_Id(Long memberId, Long courseId);
    }
