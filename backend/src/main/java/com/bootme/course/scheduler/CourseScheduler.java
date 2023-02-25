package com.bootme.course.scheduler;

import com.bootme.course.domain.Course;
import com.bootme.course.repository.CourseRepository;
import com.bootme.member.domain.BookmarkCourse;
import com.bootme.member.domain.Member;
import com.bootme.member.repository.BookmarkCourseRepository;
import com.bootme.notification.domain.Notification;
import com.bootme.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
public class CourseScheduler {

    private final CourseRepository courseRepository;
    private final BookmarkCourseRepository bookmarkCourseRepository;
    private final NotificationRepository notificationRepository;

    @Scheduled(cron = "00 55 09 * * *", zone = "Asia/Seoul")
    public void updateIsRegisterOpen() {
        Iterable<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            boolean updatedStatus = course.checkRegisterOpen();
            if (course.isRegisterOpen() != updatedStatus) {
                courseRepository.updateIsRegisterOpen(course.getId(), updatedStatus);
            }
        }
    }

    @Scheduled(cron = "00 00 10 * * *", zone = "Asia/Seoul")
    public void notifyCourseRegistrationStart() {
        final String EVENT = "registrationStart";
        LocalDate now = LocalDate.now();
        Iterable<BookmarkCourse> bookmarkCourses = bookmarkCourseRepository.findAll();

        for (BookmarkCourse bookmarkCourse : bookmarkCourses) {
            Member member = bookmarkCourse.getMember();
            Course course = bookmarkCourse.getCourse();
            boolean registrationStartsToday = course.getDates().isRegistrationStartsOn(now);
            if (registrationStartsToday) {
                Notification notification = Notification.of(member, EVENT, bookmarkCourse);
                notificationRepository.save(notification);
            }
        }
    }

    @Scheduled(cron = "00 00 10 * * *", zone = "Asia/Seoul")
    public void notifyCourseRegistrationEndInThreeDays() {
        final String EVENT = "registrationEndInThreeDays";
        LocalDate threeDaysLater = LocalDate.now().plusDays(3);
        Iterable<BookmarkCourse> bookmarkCourses = bookmarkCourseRepository.findAll();

        for (BookmarkCourse bookmarkCourse : bookmarkCourses) {
            Member member = bookmarkCourse.getMember();
            Course course = bookmarkCourse.getCourse();
            boolean registrationEndsInThreeDays = course.getDates().isRegistrationEndsOn(threeDaysLater);
            if (registrationEndsInThreeDays) {
                Notification notification = Notification.of(member, EVENT, bookmarkCourse);
                notificationRepository.save(notification);
            }
        }
    }

    @Scheduled(cron = "00 00 10 * * *", zone = "Asia/Seoul")
    public void notifyCourseRegistrationEnd() {
        final String EVENT = "registrationEnd";
        LocalDate now = LocalDate.now();
        Iterable<BookmarkCourse> bookmarkCourses = bookmarkCourseRepository.findAll();

        for (BookmarkCourse bookmarkCourse : bookmarkCourses) {
            Member member = bookmarkCourse.getMember();
            Course course = bookmarkCourse.getCourse();
            boolean registrationEndsToday = course.getDates().isRegistrationEndsOn(now);
            if (registrationEndsToday) {
                Notification notification = Notification.of(member, EVENT, bookmarkCourse);
                notificationRepository.save(notification);
            }
        }
    }

}
