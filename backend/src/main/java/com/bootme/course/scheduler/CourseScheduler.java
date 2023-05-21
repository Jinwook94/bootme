package com.bootme.course.scheduler;

import com.bootme.course.domain.Course;
import com.bootme.course.repository.CourseRepository;
import com.bootme.member.domain.BookmarkCourse;
import com.bootme.member.domain.Member;
import com.bootme.member.repository.BookmarkCourseRepository;
import com.bootme.notification.domain.Notification;
import com.bootme.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@RequiredArgsConstructor
@Component
@Slf4j
public class CourseScheduler {

    private final CourseRepository courseRepository;
    private final BookmarkCourseRepository bookmarkCourseRepository;
    private final NotificationRepository notificationRepository;

    private static final String REGISTRATION_START = "registrationStart";
    private static final String REGISTRATION_END_IN_THREE_DAYS = "registrationEndInThreeDays";
    private static final String REGISTRATION_END = "registrationEnd";

    @Scheduled(cron = "00 55 09 * * *", zone = "Asia/Seoul")
    public void updateIsRegisterOpen() {
        Iterable<Course> courses = courseRepository.findAll();
        for (Course course : courses) {
            boolean updatedStatus = course.checkRegisterOpen();
            if (course.isRegisterOpen() != updatedStatus) {
                courseRepository.updateIsRegisterOpen(course.getId(), updatedStatus);
            }
        }
        log.info("@Scheduled 코스 접수중 여부 업데이트 완료");
    }

    @Scheduled(cron = "00 00 10 * * *", zone = "Asia/Seoul")
    public void notifyCourseRegistrationStart() {
        notifyBookmarkCourses(REGISTRATION_START, LocalDate.now());
        log.info("@Scheduled 코스 접수 시작 알림 전송 완료");
    }

    @Scheduled(cron = "00 00 10 * * *", zone = "Asia/Seoul")
    public void notifyCourseRegistrationEndInThreeDays() {
        notifyBookmarkCourses(REGISTRATION_END_IN_THREE_DAYS, LocalDate.now().plusDays(3));
        log.info("@Scheduled 코스 접수 마감 3일전 알림 전송 완료");
    }

    @Scheduled(cron = "00 00 10 * * *", zone = "Asia/Seoul")
    public void notifyCourseRegistrationEnd() {
        notifyBookmarkCourses(REGISTRATION_END, LocalDate.now());
        log.info("@Scheduled 코스 접수 마감 당일 알림 전송 완료");
    }

    private void notifyBookmarkCourses(String event, LocalDate date) {
        Iterable<BookmarkCourse> bookmarkCourses = bookmarkCourseRepository.findAll();

        for (BookmarkCourse bookmarkCourse : bookmarkCourses) {
            Member member = bookmarkCourse.getMember();
            Course course = bookmarkCourse.getCourse();

            boolean isEventToday = false;
            if (REGISTRATION_START.equals(event)) {
                isEventToday = course.getDates().isRegistrationStartsOn(date);
            } else if (REGISTRATION_END_IN_THREE_DAYS.equals(event)) {
                isEventToday = course.getDates().isRegistrationEndsOn(date);
            } else if (REGISTRATION_END.equals(event)) {
                isEventToday = course.getDates().isRegistrationEndsOn(date);
            }

            if (isEventToday) {
                Notification notification = Notification.of(member, event, bookmarkCourse);
                notificationRepository.save(notification);
            }
        }
    }
}