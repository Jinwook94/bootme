package com.bootme.course.domain;

import com.bootme.course.dto.CourseRequest;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "course_id")
    private Long id;

    private String title;

    private String name;

    private int generation;

    private String url;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "company_id")
    private Company company;

    private String location;

    @Enumerated(EnumType.STRING)
    private OnOffline onoffline;

    @Embedded
    private Category categories;

    @Embedded
    private Stack stacks;

    @Enumerated(EnumType.STRING)
    private Prerequisites prerequisites;

    private int cost;

    @Enumerated(EnumType.STRING)
    private CostType costType;

    private int period;

    @Embedded
    private Dates dates;

    private boolean isRecommended;

    private boolean isTested;


    @Builder
    public Course(String title, String name, int generation, String url, Company company,
                  String location, OnOffline onoffline, Category categories, Stack stacks, Prerequisites prerequisites,
                  int cost, CostType costType, int period, Dates dates, boolean isRecommended, boolean isTested) {
        this.title = title;
        this.name = name;
        this.generation = generation;
        this.url = url;
        this.company = company;
        this.location = location;
        this.onoffline = onoffline;
        this.categories = categories;
        this.stacks = stacks;
        this.prerequisites = prerequisites;
        this.cost = cost;
        this.costType = costType;
        this.period = period;
        this.dates = dates;
        this.isRecommended = isRecommended;
        this.isTested = isTested;
    }

    public static Course of(CourseRequest courseRequest, Company company, Category categories, Stack stacks) {
        return Course.builder()
                .title(courseRequest.getTitle())
                .name(courseRequest.getName())
                .generation(courseRequest.getGeneration())
                .url(courseRequest.getUrl())
                .company(company)
                .location(courseRequest.getLocation())
                .onoffline(Enum.valueOf(OnOffline.class, courseRequest.getOnOffline()))
                .categories(categories)
                .stacks(stacks)
                .prerequisites(Enum.valueOf(Prerequisites.class, courseRequest.getPrerequisites()))
                .cost(courseRequest.getCost())
                .costType(Enum.valueOf(CostType.class, courseRequest.getCostType()))
                .period(courseRequest.getPeriod())
                .dates(courseRequest.getDates())
                .isRecommended(courseRequest.isRecommended())
                .isTested(courseRequest.isTested())
                .build();
    }

    public Map<String, List<String>> categoryToMap() {
        Map<String, List<String>> categories = new HashMap<>();
        if (this.categories != null) {
            categories.put("super", this.categories.getSuperCategory());
            categories.put("sub", this.categories.getSubCategory());
        }
        return categories;
    }

    public Map<String, List<String>> stackToMap() {
        Map<String, List<String>> stacks = new HashMap<>();
        if (this.stacks != null) {
            stacks.put("languages", this.stacks.getLanguages());
            stacks.put("frameworks", this.stacks.getFrameworks());
        }
        return stacks;
    }

    public void modifyCourse(CourseRequest courseRequest) {
        this.title = courseRequest.getTitle();
        this.name = courseRequest.getName();
        this.generation = courseRequest.getGeneration();
        this.url = courseRequest.getUrl();
        this.location = courseRequest.getLocation();
        this.onoffline = Enum.valueOf(OnOffline.class, courseRequest.getOnOffline());
        this.categories = courseRequest.getCategories();
        this.stacks = courseRequest.getStacks();
        this.prerequisites = Enum.valueOf(Prerequisites.class, courseRequest.getPrerequisites());
        this.cost = courseRequest.getCost();
        this.costType = Enum.valueOf(CostType.class, courseRequest.getCostType());
        this.period = courseRequest.getPeriod();
        this.dates = courseRequest.getDates();
        this.isRecommended = courseRequest.isRecommended();
        this.isTested = courseRequest.isTested();
    }

    public void modifyCompany(Company company){
        this.company = company;
    }

    public boolean isRegisterOpen() {
        boolean afterStart = LocalDate.now().isEqual(dates.getRegistrationStartDate()) || LocalDate.now().isAfter(dates.getRegistrationStartDate());
        boolean beforeEnd = LocalDate.now().isEqual(dates.getRegistrationEndDate()) || LocalDate.now().isBefore(dates.getRegistrationEndDate());
        return afterStart && beforeEnd;
    }

}
