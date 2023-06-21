package com.bootme.course.scheduler;

import com.bootme.bookmark.domain.CourseBookmark;
import com.bootme.bookmark.repository.CourseBookmarkRepository;
import com.bootme.course.repository.CourseRepository;
import com.bootme.notification.domain.Notification;
import com.bootme.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.AbstractMap;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Component
@Slf4j
public class CourseScheduler {

    private final CourseRepository courseRepository;
    private final CourseBookmarkRepository courseBookmarkRepository;
    private final NotificationRepository notificationRepository;

    private static final String REGISTRATION_START = "registrationStart";
    private static final String REGISTRATION_END_IN_THREE_DAYS = "registrationEndInThreeDays";
    private static final String REGISTRATION_END = "registrationEnd";

    @Scheduled(cron = "00 55 09 * * *", zone = "Asia/Seoul")
    public void updateIsRegisterOpen() {
        courseRepository.updateAllCoursesRegistrationStatus();
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

    // todo: findAll() 성능 이슈
    public void notifyBookmarkCourses(String event, LocalDate date) {
        try (Stream<CourseBookmark> courseBookmarks = courseBookmarkRepository.findAll().stream()) {
            List<Notification> notifications = courseBookmarks
                    .map(cb -> new AbstractMap.SimpleEntry<>(cb.getBookmark(), cb))
                    .filter(entry -> entry.getValue().getCourse().isEventOnDate(event, date))
                    .map(entry -> Notification.of(entry.getKey().getMember(), event, entry.getValue()))
                    .collect(Collectors.toList());

            notificationRepository.saveAll(notifications);
        }
    }


}