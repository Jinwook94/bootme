package com.bootme.course.domain;

import com.bootme.common.domain.BaseEntity;
import com.bootme.course.dto.CourseRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Course extends BaseEntity {

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

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    private String location;

    @Embedded
    private Categories categories;

    @OneToMany(mappedBy = "course", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private List<CourseStack> courseStacks = new ArrayList<>();

    private int cost;

    private int period;

    @Embedded
    private Dates dates;

    private boolean isRecommended;

    private boolean isFree;

    private boolean isKdt;

    private boolean isOnline;

    private boolean isTested;

    private boolean isPrerequisiteRequired;

    private boolean isRegisterOpen;

    private int clicks;

    private int bookmarks;

    @Builder
    public Course(String title, String name, int generation, String url, Company company, String location,
                  List<String> categories, List<CourseStack> courseStacks, int cost, int period, Dates dates, boolean isRecommended, boolean isFree,
                  boolean isKdt, boolean isOnline, boolean isTested, boolean isPrerequisiteRequired, int clicks, int bookmarks) {
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
        this.isRecommended = isRecommended;
        this.isFree = isFree;
        this.isKdt = isKdt;
        this.isOnline = isOnline;
        this.isTested = isTested;
        this.isPrerequisiteRequired = isPrerequisiteRequired;
        this.clicks = clicks;
        this.bookmarks = bookmarks;
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

    public void modifyCompany(Company company){
        this.company = company;
    }

    public boolean checkRegisterOpen() {
        boolean afterStart = LocalDate.now().isEqual(dates.getRegistrationStartDate()) || LocalDate.now().isAfter(dates.getRegistrationStartDate());
        boolean beforeEnd = LocalDate.now().isEqual(dates.getRegistrationEndDate()) || LocalDate.now().isBefore(dates.getRegistrationEndDate());
        return afterStart && beforeEnd;
    }

}
