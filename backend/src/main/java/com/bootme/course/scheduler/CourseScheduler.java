package com.bootme.course.scheduler;

import com.bootme.bookmark.domain.CourseBookmark;
import com.bootme.bookmark.repository.CourseBookmarkRepository;
import com.bootme.course.domain.Course;
import com.bootme.course.repository.CourseRepository;
import com.bootme.notification.domain.Notification;
import com.bootme.notification.domain.NotificationEventType;
import com.bootme.notification.domain.NotificationFactory;
import com.bootme.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

import static com.bootme.notification.domain.NotificationEventType.*;

@RequiredArgsConstructor
@Component
@Slf4j
public class CourseScheduler {

    private CourseScheduler self;
    private final CourseRepository courseRepository;
    private final CourseBookmarkRepository courseBookmarkRepository;
    private final NotificationFactory notificationFactory;
    private final NotificationService notificationService;

    @Autowired
    public void setCourseScheduler(@Lazy CourseScheduler courseScheduler) {
        this.self = courseScheduler;
    }

    @Scheduled(cron = "00 55 09 * * *", zone = "Asia/Seoul")
    @Transactional
    public void updateIsRegisterOpen() {
        List<Course> courses = courseRepository.findAll();
        courses.forEach(Course::updateRegistrationStatus);
        courseRepository.saveAll(courses);
        log.info("@Scheduled 코스 접수중 여부 업데이트 완료");
    }

    @Scheduled(cron = "00 00 10 * * *", zone = "Asia/Seoul")
    public void notifyCourseRegistrationStart() {
        self.notifyBookmarkCourses(REGISTRATION_START, LocalDate.now());
        log.info("@Scheduled 코스 접수 시작 알림 전송 완료");
    }

    @Scheduled(cron = "00 00 10 * * *", zone = "Asia/Seoul")
    public void notifyCourseRegistrationEndInThreeDays() {
        self.notifyBookmarkCourses(REGISTRATION_END_IN_THREE_DAYS, LocalDate.now().plusDays(3));
        log.info("@Scheduled 코스 접수 마감 3일전 알림 전송 완료");
    }

    @Scheduled(cron = "00 00 10 * * *", zone = "Asia/Seoul")
    public void notifyCourseRegistrationEnd() {
        self.notifyBookmarkCourses(REGISTRATION_END, LocalDate.now());
        log.info("@Scheduled 코스 접수 마감 당일 알림 전송 완료");
    }

    // todo: N+1 문제 → JOIN FETCH 사용한 해결 필요
    @Transactional
    public void notifyBookmarkCourses(NotificationEventType event, LocalDate date) {
        List<CourseBookmark> courseBookmarks = courseBookmarkRepository.findAll();

        List<Notification> notifications = courseBookmarks.stream()
                .filter(cb -> cb.getCourse().isEventOnDate(event, date))
                .map(cb -> notificationFactory.createCourseBookmarkNotification(cb.getBookmark().getMember(), event, cb))
                .toList();

        notificationService.sendNotifications(notifications);
    }

}