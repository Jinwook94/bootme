package com.bootme.util.fixture;

import com.bootme.course.domain.*;
import com.bootme.course.domain.Stack;
import com.bootme.course.dto.CompanyRequest;
import com.bootme.course.dto.CompanyResponse;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;

import java.time.LocalDate;
import java.util.*;

public class CourseFixture {

    public static final String VALID_TITLE_1 = "네이버 서버 개발자 부트캠프 1기";
    public static final String VALID_TITLE_2 = "카카오 프론트엔드 개발자 부트캠프 2기";
    public static final String VALID_TITLE_3 = "라인 서버 개발자 부트캠프 3기";
    public static final String VALID_TITLE_4 = "쿠팡 서버 개발자 부트캠프 4기";
    public static final String VALID_TITLE_5 = "우아한형제들 프론트엔드 개발자 부트캠프 5기";
    public static final String VALID_TITLE_6 = "구글 서버 개발자 부트캠프 6기";
    public static final String VALID_TITLE_7 = "페이스북 서버 개발자 부트캠프 7기";
    public static final String VALID_TITLE_8 = "마이크로소프트 프론트엔드 개발자 부트캠프 8기";
    public static final String VALID_TITLE_9 = "아마존 서버 개발자 부트캠프 9기";
    public static final String VALID_NAME_1 = "네이버 서버 개발자 부트캠프";
    public static final String VALID_NAME_2 = "카카오 프론트엔드 개발자 부트캠프";
    public static final String VALID_NAME_3 = "라인 서버 개발자 부트캠프 부트캠프";
    public static final int VALID_GENERATION_1 = 1;
    public static final int VALID_GENERATION_2 = 2;
    public static final int VALID_GENERATION_3 = 3;
    public static final String VALID_URL_1 = "www.boot.naver.com";
    public static final String VALID_URL_2 = "www.boot.kakao.com";
    public static final String VALID_URL_3 = "www.boot.line.com";
    public static final String VALID_LOCATION_1 = "서울시 강남구";
    public static final String VALID_LOCATION_2 = "서울시 관악구";
    public static final String VALID_LOCATION_3 = "서울시 중구";
    public static final int VALID_COST_1 = 0;
    public static final int VALID_COST_2 = 0;
    public static final int VALID_COST_3 = 1000000;
    public static final String VALID_CostType_1 = "무료";
    public static final String VALID_CostType_2 = "무료 (국비)";
    public static final String VALID_CostType_3 = "유료";
    public static final int VALID_PERIOD_1 = 90;
    public static final int VALID_PERIOD_2 = 28;
    public static final int VALID_PERIOD_3 = 210;
    public static final Dates VALID_DATES_1 = Dates.builder()
            .registrationStartDate(LocalDate.of(2023, 1, 1))
            .registrationEndDate(LocalDate.of(2024, 2, 31))
            .courseStartDate(LocalDate.of(2024, 2, 1))
            .courseEndDate(LocalDate.of(2024, 4, 30))
            .build();
    public static final Dates VALID_DATES_2 = Dates.builder()
            .registrationStartDate(LocalDate.of(2022, 1, 1))
            .registrationEndDate(LocalDate.of(2022, 1, 31))
            .courseStartDate(LocalDate.of(2022, 2, 1))
            .courseEndDate(LocalDate.of(2022, 2, 28))
            .build();
    public static final Dates VALID_DATES_3 = Dates.builder()
            .registrationStartDate(LocalDate.of(2023, 2, 23))
            .registrationEndDate(LocalDate.of(2024, 1, 1))
            .courseStartDate(LocalDate.of(2024, 2, 1))
            .courseEndDate(LocalDate.of(2024, 7, 31))
            .build();
    public static final String VALID_ONOFFLINE_1 = "온라인";
    public static final String VALID_ONOFFLINE_2 = "오프라인";
    public static final String VALID_ONOFFLINE_3 = "온오프라인혼합";
    public static final Category CATEGORY1 = Category.builder()
            .superCategory(new ArrayList<>(Arrays.asList("웹", "모바일 앱")))
            .subCategory(new ArrayList<>(Arrays.asList("백엔드", "안드로이드")))
            .build();
    public static final Category CATEGORY2 = Category.builder()
            .superCategory(new ArrayList<>(Arrays.asList("웹", "게임")))
            .subCategory(new ArrayList<>(Arrays.asList("프론트엔드", "데이터")))
            .build();
    public static final Category CATEGORY3 = Category.builder()
            .superCategory(new ArrayList<>(Arrays.asList("임베디드", "보안")))
            .subCategory(new ArrayList<>(Arrays.asList("백엔드", "iOS")))
            .build();
    public static final Map<String, List<String>> VALID_CATEGORY_1 = categoryToMap(CATEGORY1);
    public static final Map<String, List<String>> VALID_CATEGORY_2 = categoryToMap(CATEGORY2);
    public static final Map<String, List<String>> VALID_CATEGORY_3 = categoryToMap(CATEGORY3);
    public static final Stack STACK1 = Stack.builder()
            .languages(new ArrayList<>(Arrays.asList("Java", "Python", "JavaScript")))
            .frameworks(new ArrayList<>(Arrays.asList("Spring", "Django", "Nodejs")))
            .build();
    public static final Stack STACK2 = Stack.builder()
            .languages(new ArrayList<>(Arrays.asList("JavaScript", "HTML", "CSS")))
            .frameworks(new ArrayList<>(Arrays.asList("React", "Vue")))
            .build();
    public static final Stack STACK3 = Stack.builder()
            .languages(new ArrayList<>(Arrays.asList("Java", "Python")))
            .frameworks(new ArrayList<>(Arrays.asList("Spring", "Django")))
            .build();
    public static final Map<String, List<String>> VALID_STACK_1 = stackToMap(STACK1);
    public static final Map<String, List<String>> VALID_STACK_2 = stackToMap(STACK2);
    public static final Map<String, List<String>> VALID_STACK_3 = stackToMap(STACK3);
    public static final String VALID_PREREQUISITES_1 = "노베이스";
    public static final String VALID_PREREQUISITES_2 = "프로그래밍언어기초";
    public static final String VALID_PREREQUISITES_3 = "코딩테스트풀이가능";
    public static final int VALID_CLICKS_1 = 1;
    public static final int VALID_CLICKS_2 = 2;
    public static final int VALID_CLICKS_3 = 3;
    public static final int VALID_BOOKMARKS_1 = 4;
    public static final int VALID_BOOKMARKS_2 = 5;
    public static final int VALID_BOOKMARKS_3 = 6;
    public static final String VALID_COM_NAME_1 = "네이버";
    public static final String VALID_COM_NAME_2 = "카카오";
    public static final String VALID_COM_NAME_3 = "라인";
    public static final String VALID_COM_SERVICE_NAME_1 = "네이버앱";
    public static final String VALID_COM_SERVICE_NAME_2 = "카카오톡";
    public static final String VALID_COM_SERVICE_NAME_3 = "라인앱";
    public static final String VALID_COM_URL_1 = "www.naver.com";
    public static final String VALID_COM_URL_2 = "www.kakao.com";
    public static final String VALID_COM_URL_3 = "www.line.com";
    public static final String VALID_COM_SERVICE_URL_1 = "www.app.naver.com";
    public static final String VALID_COM_SERVICE_URL_2 = "www.talk.kakao.com";
    public static final String VALID_COM_SERVICE_URL_3 = "www.app.line.com";
    public static final String VALID_COM_LOGO_URL_1 = "https://logoproject.naver.com/img/img_story_renewal.png";
    public static final String VALID_COM_LOGO_URL_2 = "https://t1.kakaocdn.net/kakaocorp/kakaocorp/admin/1b904e28017800001.png";
    public static final String VALID_COM_LOGO_URL_3 = "https://vos.line-scdn.net/strapi-cluster-instance-bucket-84/brand_02_8089c34e51.jpeg";

    public static CompanyRequest getCompanyRequest(int index) {
        index--;
        String[] names = {VALID_COM_NAME_1, VALID_COM_NAME_2, VALID_COM_NAME_3};
        String[] serviceNames = {VALID_COM_SERVICE_NAME_1, VALID_COM_SERVICE_NAME_2, VALID_COM_SERVICE_NAME_3};
        String[] urls = {VALID_COM_URL_1, VALID_COM_URL_2, VALID_COM_URL_3};
        String[] serviceUrls = {VALID_COM_SERVICE_URL_1, VALID_COM_SERVICE_URL_2, VALID_COM_SERVICE_URL_3};
        String[] logoUrls = {VALID_COM_LOGO_URL_1, VALID_COM_LOGO_URL_2, VALID_COM_LOGO_URL_3};

        return CompanyRequest.builder()
                .name(names[index])
                .serviceName(serviceNames[index])
                .url(urls[index])
                .serviceUrl(serviceUrls[index])
                .logoUrl(logoUrls[index])
                .build();
    }

    public static CompanyResponse getCompanyResponse(int index) {
        index--;
        Long[] ids = {1L, 2L, 3L};
        String[] names = {VALID_COM_NAME_1, VALID_COM_NAME_2, VALID_COM_NAME_3};
        String[] serviceNames = {VALID_COM_SERVICE_NAME_1, VALID_COM_SERVICE_NAME_2, VALID_COM_SERVICE_NAME_3};
        String[] urls = {VALID_COM_URL_1, VALID_COM_URL_2, VALID_COM_URL_3};
        String[] serviceUrls = {VALID_COM_SERVICE_URL_1, VALID_COM_SERVICE_URL_2, VALID_COM_SERVICE_URL_3};
        String[] logoUrls = {VALID_COM_LOGO_URL_1, VALID_COM_LOGO_URL_2, VALID_COM_LOGO_URL_3};
        List[] courses = new ArrayList[]{
                new ArrayList<>(Arrays.asList(VALID_TITLE_1, VALID_TITLE_2, VALID_TITLE_3)),
                new ArrayList<>(Arrays.asList(VALID_TITLE_4, VALID_TITLE_5, VALID_TITLE_6)),
                new ArrayList<>(Arrays.asList(VALID_TITLE_7, VALID_TITLE_8, VALID_TITLE_9))
        };
        return CompanyResponse.builder()
                .id(ids[index])
                .name(names[index])
                .serviceName(serviceNames[index])
                .url(urls[index])
                .serviceUrl(serviceUrls[index])
                .logoUrl(logoUrls[index])
                .courses(courses[index])
                .build();
    }

    public static CourseRequest getCourseRequest(int index) {
        index--;
        String[] titles = {VALID_TITLE_1, VALID_TITLE_2, VALID_TITLE_3};
        String[] names = {VALID_NAME_1, VALID_NAME_2, VALID_NAME_3};
        int[] generations = {VALID_GENERATION_1, VALID_GENERATION_2, VALID_GENERATION_3};
        String[] urls = {VALID_URL_1, VALID_URL_2, VALID_URL_3};
        String[] companies = {VALID_COM_NAME_1, VALID_COM_NAME_2, VALID_COM_NAME_3};
        String[] locations = {VALID_LOCATION_1, VALID_LOCATION_2, VALID_LOCATION_3};
        String[] onOfflines = {VALID_ONOFFLINE_1, VALID_ONOFFLINE_2, VALID_ONOFFLINE_3};
        Category[] categories = {CATEGORY1, CATEGORY2, CATEGORY3};
        Stack[] stacks = {STACK1, STACK2, STACK3};
        String[] prerequisites = {VALID_PREREQUISITES_1, VALID_PREREQUISITES_2, VALID_PREREQUISITES_3};
        int[] costs = {VALID_COST_1, VALID_COST_2, VALID_COST_3};
        String [] costTypes = {VALID_CostType_1, VALID_CostType_2, VALID_CostType_3};
        int[] periods = {VALID_PERIOD_1, VALID_PERIOD_2, VALID_PERIOD_3};
        Dates[] dates = {VALID_DATES_1, VALID_DATES_2, VALID_DATES_3};
        boolean[] recommendeds = {true, false, true};
        boolean[] testeds = {true, true, false};

        return CourseRequest.builder()
                .title(titles[index])
                .name(names[index])
                .generation(generations[index])
                .url(urls[index])
                .companyName(companies[index])
                .location(locations[index])
                .onOffline(onOfflines[index])
                .categories(categories[index])
                .stacks(stacks[index])
                .prerequisites(prerequisites[index])
                .cost(costs[index])
                .costType(costTypes[index])
                .period(periods[index])
                .dates(dates[index])
                .isRecommended(recommendeds[index])
                .isTested(testeds[index])
                .build();
    }

    public static CourseResponse getCourseResponse(int index) {
        index--;
        Long[] ids = {1L, 2L, 3L};
        String[] titles = {VALID_TITLE_1, VALID_TITLE_2, VALID_TITLE_3};
        String[] names = {VALID_NAME_1, VALID_NAME_2, VALID_NAME_3};
        int[] generations = {VALID_GENERATION_1, VALID_GENERATION_2, VALID_GENERATION_3};
        String[] urls = {VALID_URL_1, VALID_URL_2, VALID_URL_3};
        CompanyResponse[] companies = {getCompanyResponse(1), getCompanyResponse(2), getCompanyResponse(3)};
        String[] locations = {VALID_LOCATION_1, VALID_LOCATION_2, VALID_LOCATION_3};
        String[] onOfflines = {VALID_ONOFFLINE_1, VALID_ONOFFLINE_2, VALID_ONOFFLINE_3};
        Map<String, List<String>>[] categories = (Map<String, List<String>>[]) new Map[]{VALID_CATEGORY_1, VALID_CATEGORY_2, VALID_CATEGORY_3};
        Map<String, List<String>>[] stacks = (Map<String, List<String>>[]) new Map[]{VALID_STACK_1, VALID_STACK_2, VALID_STACK_3};
        String[] prerequisites = {VALID_PREREQUISITES_1, VALID_PREREQUISITES_2, VALID_PREREQUISITES_3};
        int[] costs = {VALID_COST_1, VALID_COST_2, VALID_COST_3};
        String[] costTypes = {VALID_CostType_1, VALID_CostType_2, VALID_CostType_3};
        int[] periods = {VALID_PERIOD_1, VALID_PERIOD_2, VALID_PERIOD_3};
        Dates[] dates = {VALID_DATES_1, VALID_DATES_2, VALID_DATES_3};
        boolean[] recommendeds = {true, false, true};
        boolean[] testeds = {true, true, false};
        int[] clicks = {VALID_CLICKS_1, VALID_CLICKS_2, VALID_CLICKS_3};
        int[] bookmarks = {VALID_BOOKMARKS_1, VALID_BOOKMARKS_2, VALID_BOOKMARKS_3};

        return CourseResponse.builder()
                .id(ids[index])
                .title(titles[index])
                .name(names[index])
                .generation(generations[index])
                .url(urls[index])
                .company(companies[index])
                .location(locations[index])
                .onOffline(onOfflines[index])
                .categories(categories[index])
                .stacks(stacks[index])
                .prerequisites(prerequisites[index])
                .cost(costs[index])
                .costType(costTypes[index])
                .period(periods[index])
                .dates(dates[index])
                .isRecommended(recommendeds[index])
                .isTested(testeds[index])
                .clicks(clicks[index])
                .bookmarks(bookmarks[index])
                .build();
    }

    public static Company getCompany(int index) {
        index--;
        String[] names = {VALID_COM_NAME_1, VALID_COM_NAME_2, VALID_COM_NAME_3};
        String[] serviceNames = {VALID_COM_SERVICE_NAME_1, VALID_COM_SERVICE_NAME_2, VALID_COM_SERVICE_NAME_3};
        String[] urls = {VALID_COM_URL_1, VALID_COM_URL_2, VALID_COM_URL_3};
        String[] serviceUrls = {VALID_COM_SERVICE_URL_1, VALID_COM_SERVICE_URL_2, VALID_COM_SERVICE_URL_3};
        String[] logoUrls = {VALID_COM_LOGO_URL_1, VALID_COM_LOGO_URL_2, VALID_COM_LOGO_URL_3};
        List<Course> courses = new ArrayList<>();

        return Company.builder()
                .name(names[index])
                .serviceName(serviceNames[index])
                .url(urls[index])
                .serviceUrl(serviceUrls[index])
                .logoUrl(logoUrls[index])
                .courses(courses)
                .build();
    }

    public static Company[] getCompanies() {
        String[] names = {VALID_COM_NAME_1, VALID_COM_NAME_2, VALID_COM_NAME_3};
        String[] serviceNames = {VALID_COM_SERVICE_NAME_1, VALID_COM_SERVICE_NAME_2, VALID_COM_SERVICE_NAME_3};
        String[] urls = {VALID_COM_URL_1, VALID_COM_URL_2, VALID_COM_URL_3};
        String[] serviceUrls = {VALID_COM_SERVICE_URL_1, VALID_COM_SERVICE_URL_2, VALID_COM_SERVICE_URL_3};
        String[] logoUrls = {VALID_COM_LOGO_URL_1, VALID_COM_LOGO_URL_2, VALID_COM_LOGO_URL_3};

        Company[] companies = new Company[names.length];
        for (int i = 0; i < names.length; i++) {
            companies[i] = Company.builder()
                    .name(names[i])
                    .serviceName(serviceNames[i])
                    .url(urls[i])
                    .serviceUrl(serviceUrls[i])
                    .logoUrl(logoUrls[i])
                    .courses(new ArrayList<>())
                    .build();
        }
        return companies;
    }

    public static Course getCourse(int index) {
        index--;
        String[] titles = {VALID_TITLE_1, VALID_TITLE_2, VALID_TITLE_3};
        String[] names = {VALID_NAME_1, VALID_NAME_2, VALID_NAME_3};
        int[] generations = {VALID_GENERATION_1, VALID_GENERATION_2, VALID_GENERATION_3};
        String[] urls = {VALID_URL_1, VALID_URL_2, VALID_URL_3};
        Company[] companies = {getCompany(1), getCompany(2), getCompany(3)};
        String[] locations = {VALID_LOCATION_1, VALID_LOCATION_2, VALID_LOCATION_3};
        String[] onofflines = {VALID_ONOFFLINE_1, VALID_ONOFFLINE_2, VALID_ONOFFLINE_3};
        Category[] categories = {CATEGORY1, CATEGORY2, CATEGORY3};
        Stack[] stacks = {STACK1, STACK2, STACK3};
        String[] prerequisites = {VALID_PREREQUISITES_1, VALID_PREREQUISITES_2, VALID_PREREQUISITES_3};
        int[] costs = {VALID_COST_1, VALID_COST_2, VALID_COST_3};
        String[] costTypes = {VALID_CostType_1, VALID_CostType_2, VALID_CostType_3};
        int[] periods = {VALID_PERIOD_1, VALID_PERIOD_2, VALID_PERIOD_3};
        Dates[] dates = {VALID_DATES_1, VALID_DATES_2, VALID_DATES_3};
        boolean[] recommendeds = {true, false, true};
        boolean[] testeds = {true, true, false};
        int[] clicks = {VALID_CLICKS_1, VALID_CLICKS_2, VALID_CLICKS_3};
        int[] bookmarks = {VALID_BOOKMARKS_1, VALID_BOOKMARKS_2, VALID_BOOKMARKS_3};

        return Course.builder()
                .title(titles[index])
                .name(names[index])
                .generation(generations[index])
                .url(urls[index])
                .company(companies[index])
                .location(locations[index])
                .onoffline(onofflines[index])
                .categories(categories[index])
                .stacks(stacks[index])
                .prerequisites(prerequisites[index])
                .cost(costs[index])
                .costType(costTypes[index])
                .period(periods[index])
                .dates(dates[index])
                .isRecommended(recommendeds[index])
                .isTested(testeds[index])
                .clicks(clicks[index])
                .bookmarks(bookmarks[index])
                .build();

    }

    public static Course[] getCourses() {
        String[] titles = {VALID_TITLE_1, VALID_TITLE_2, VALID_TITLE_3};
        String[] names = {VALID_NAME_1, VALID_NAME_2, VALID_NAME_3};
        int[] generations = {VALID_GENERATION_1, VALID_GENERATION_2, VALID_GENERATION_3};
        String[] urls = {VALID_URL_1, VALID_URL_2, VALID_URL_3};
        Company[] companies = {getCompanies()[0], getCompanies()[1], getCompanies()[2]};
        String[] locations = {VALID_LOCATION_1, VALID_LOCATION_2, VALID_LOCATION_3};
        String[] onofflines = {VALID_ONOFFLINE_1, VALID_ONOFFLINE_2, VALID_ONOFFLINE_3};
        Category[] categories = {CATEGORY1, CATEGORY2, CATEGORY3};
        Stack[] stacks = {STACK1, STACK2, STACK3};
        String[] prerequisites = {VALID_PREREQUISITES_1, VALID_PREREQUISITES_2, VALID_PREREQUISITES_3};
        int[] costs = {VALID_COST_1, VALID_COST_2, VALID_COST_3};
        String[] costTypes = {VALID_CostType_1, VALID_CostType_2, VALID_CostType_3};
        int[] periods = {VALID_PERIOD_1, VALID_PERIOD_2, VALID_PERIOD_3};
        Dates[] dates = {VALID_DATES_1, VALID_DATES_2, VALID_DATES_3};
        boolean[] recommendeds = {true, false, true};
        boolean[] testeds = {true, true, false};
        int[] clicks = {VALID_CLICKS_1, VALID_CLICKS_2, VALID_CLICKS_3};
        int[] bookmarks = {VALID_BOOKMARKS_1, VALID_BOOKMARKS_2, VALID_BOOKMARKS_3};

        Course[] courses = new Course[titles.length];
        for (int i = 0; i < titles.length; i++) {
            courses[i] = Course.builder()
                    .title(titles[i])
                    .name(names[i])
                    .generation(generations[i])
                    .url(urls[i])
                    .company(companies[i])
                    .location(locations[i])
                    .onoffline(onofflines[i])
                    .categories(categories[i])
                    .stacks(stacks[i])
                    .prerequisites(prerequisites[i])
                    .cost(costs[i])
                    .costType(costTypes[i])
                    .period(periods[i])
                    .dates(dates[i])
                    .isRecommended(recommendeds[i])
                    .isTested(testeds[i])
                    .clicks(clicks[i])
                    .bookmarks(bookmarks[i])
                    .build();
        }

        return courses;
    }

    private static Map<String, List<String>> categoryToMap(Category category) {
        Map<String, List<String>> categories = new HashMap<>();
        categories.put("super", category.getSuperCategory());
        categories.put("sub", category.getSubCategory());
        return categories;
    }

    private static Map<String, List<String>> stackToMap(Stack stack) {
        Map<String, List<String>> stacks = new HashMap<>();
        stacks.put("languages", stack.getLanguages());
        stacks.put("frameworks", stack.getFrameworks());
        return stacks;
    }

}
