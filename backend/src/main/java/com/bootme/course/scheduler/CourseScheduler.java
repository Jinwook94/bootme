package com.bootme.course.scheduler;

import com.bootme.course.repository.CourseRepository;
import com.bootme.member.domain.BookmarkCourse;
import com.bootme.member.repository.BookmarkCourseRepository;
import com.bootme.notification.domain.Notification;
import com.bootme.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

    // todo: findAll() 성능 이슈 가능성 -> 리팩토링
    @Transactional
    public void notifyBookmarkCourses(String event, LocalDate date) {
        try (Stream<BookmarkCourse> bookmarkCourses = bookmarkCourseRepository.findAll().stream()) {
            List<Notification> notifications = bookmarkCourses
                    .filter(bc -> bc.getCourse().isEventOnDate(event, date))
                    .map(bc -> Notification.of(bc.getMember(), event, bc))
                    .collect(Collectors.toList());

            notificationRepository.saveAll(notifications);
        }
    }

}