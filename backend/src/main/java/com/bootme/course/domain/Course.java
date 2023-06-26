package com.bootme.course.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.bookmark.domain.Bookmarkable;
import com.bootme.common.domain.Clickable;
import com.bootme.course.dto.CourseRequest;
import com.bootme.notification.domain.NotificationEventType;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.bootme.notification.domain.NotificationEventType.*;
import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.FetchType.LAZY;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Course extends BaseEntity implements Clickable, Bookmarkable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String name;

    private int generation;

    @Column(nullable = false)
    private String url;

    @ManyToOne(fetch = LAZY)
    @JoinColumn(name = "company_id")
    private Company company;

    private String location;

    @Embedded
    private Categories categories;

    @OneToMany(mappedBy = "course", cascade = PERSIST)
    private List<CourseStack> courseStacks = new ArrayList<>();

    private int cost;

    private int period;

    @Embedded
    private Dates dates;

    @Lob
    private String detail;

    private boolean isRecommended;

    private boolean isFree;

    private boolean isKdt;

    private boolean isOnline;

    private boolean isTested;

    private boolean isPrerequisiteRequired;

    private boolean isRegisterOpen;

    private int clicks;

    private int bookmarks;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private CourseStatus status;

    @Builder
    public Course(String title, String name, int generation, String url, Company company, String location,
                  List<String> categories, List<CourseStack> courseStacks, int cost, int period, Dates dates,
                  String detail, boolean isRecommended, boolean isFree, boolean isKdt, boolean isOnline,
                  boolean isTested, boolean isPrerequisiteRequired, int clicks, int bookmarks, CourseStatus status) {
        this.title = title;
        this.name = name;
        this.generation = generation;
        this.url = url;
        this.company = company;
        this.location = location;
        this.categories = new Categories(categories);
        this.courseStacks = courseStacks == null ? new ArrayList<>() : new ArrayList<>(courseStacks);
        this.cost = cost;
        this.period = period;
        this.dates = dates;
        this.detail = detail;
        this.isRecommended = isRecommended;
        this.isFree = isFree;
        this.isKdt = isKdt;
        this.isOnline = isOnline;
        this.isTested = isTested;
        this.isPrerequisiteRequired = isPrerequisiteRequired;
        this.clicks = clicks;
        this.bookmarks = bookmarks;
        this.status = status;
        this.isRegisterOpen = checkRegisterOpen();
    }

    public static Course of(CourseRequest courseRequest, Company company) {
        return Course.builder()
                .title(courseRequest.getTitle())
                .name(courseRequest.getName())
                .generation(courseRequest.getGeneration())
                .url(courseRequest.getUrl())
                .company(company)
                .location(courseRequest.getLocation())
                .categories(getCategories(courseRequest))
                .cost(courseRequest.getCost())
                .period(courseRequest.getPeriod())
                .dates(courseRequest.getDates())
                .isRecommended(courseRequest.isRecommended())
                .isFree(courseRequest.isFree())
                .isKdt(courseRequest.isKdt())
                .isOnline(courseRequest.isOnline())
                .isTested(courseRequest.isTested())
                .isPrerequisiteRequired(courseRequest.isPrerequisiteRequired())
                .clicks(0)
                .bookmarks(0)
                .status(CourseStatus.DISPLAY)
                .build();
    }

    public void modifyCourse(CourseRequest courseRequest) {
        this.title = courseRequest.getTitle();
        this.name = courseRequest.getName();
        this.generation = courseRequest.getGeneration();
        this.url = courseRequest.getUrl();
        this.location = courseRequest.getLocation();
        this.categories = new Categories(getCategories(courseRequest));
        this.cost = courseRequest.getCost();
        this.period = courseRequest.getPeriod();
        this.dates = courseRequest.getDates();
        this.isRecommended = courseRequest.isRecommended();
        this.isFree = courseRequest.isFree();
        this.isKdt = courseRequest.isKdt();
        this.isOnline = courseRequest.isOnline();
        this.isTested = courseRequest.isTested();
        this.isPrerequisiteRequired = courseRequest.isPrerequisiteRequired();
    }

    public void modifyCourseDetail(String detail){
        this.detail = detail;
    }

    public void modifyCompany(Company company){
        this.company = company;
    }

    private static ArrayList<String> getCategories(CourseRequest courseRequest) {
        List<String> superCategories = courseRequest.getSuperCategories();
        List<String> subCategories = courseRequest.getSubCategories();
        ArrayList<String> categories = new ArrayList<>();
        if(superCategories != null){
            categories.addAll(superCategories);
        }
        if(subCategories != null){
            categories.addAll(subCategories);
        }
        return categories;
    }

    public void softDeleteCourse() {
        this.status = CourseStatus.DELETED;
    }

    public boolean checkRegisterOpen() {
        boolean afterStart = LocalDate.now().isEqual(dates.getRegistrationStartDate()) || LocalDate.now().isAfter(dates.getRegistrationStartDate());
        boolean beforeEnd = LocalDate.now().isEqual(dates.getRegistrationEndDate()) || LocalDate.now().isBefore(dates.getRegistrationEndDate());
        return afterStart && beforeEnd;
    }

    public boolean isEventOnDate(NotificationEventType event, LocalDate date) {
        if (REGISTRATION_START.equals(event)) {
            return this.dates.isRegistrationStartsOn(date);
        } else if (REGISTRATION_END_IN_THREE_DAYS.equals(event)) {
            return this.dates.isRegistrationEndsInThreeDays(date);
        } else if (REGISTRATION_END.equals(event)) {
            return this.dates.isRegistrationEndsOn(date);
        }

        return false;
    }

    @Override
    public void incrementClicks() {
        this.clicks += 1;
    }

    @Override
    public void incrementBookmarks() {
        this.bookmarks += 1;
    }

}
