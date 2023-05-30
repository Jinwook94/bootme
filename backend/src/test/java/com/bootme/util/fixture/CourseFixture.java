package com.bootme.util.fixture;

import com.bootme.course.domain.*;
import com.bootme.course.dto.*;

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
    public static final Integer VALID_GENERATION_1 = 1;
    public static final Integer VALID_GENERATION_2 = 2;
    public static final Integer VALID_GENERATION_3 = 3;
    public static final String VALID_URL_1 = "www.boot.naver.com";
    public static final String VALID_URL_2 = "www.boot.kakao.com";
    public static final String VALID_URL_3 = "www.boot.line.com";
    public static final String VALID_LOCATION_1 = "서울시 강남구";
    public static final String VALID_LOCATION_2 = "서울시 관악구";
    public static final String VALID_LOCATION_3 = "서울시 중구";
    public static final Integer VALID_COST_1 = 0;
    public static final Integer VALID_COST_2 = 500;
    public static final Integer VALID_COST_3 = 700;
    public static final Integer VALID_PERIOD_1 = 90;
    public static final Integer VALID_PERIOD_2 = 28;
    public static final Integer VALID_PERIOD_3 = 210;
    public static final Dates VALID_DATES_1 = Dates.builder()
            .registrationStartDate(LocalDate.of(2023, 1, 1))
            .registrationEndDate(LocalDate.of(2024, 1, 31))
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
    public static final String VALID_DETAIL_1 = "<p> 수정된 코스 상세 정보 1 <p>";
    public static final String VALID_DETAIL_2 = "<p> 수정된 코스 상세 정보 2 <p>";
    public static final String VALID_DETAIL_3 = "<p> 수정된 코스 상세 정보 3 <p>";
    public static final String 웹 = "웹";
    public static final String 모바일_앱 = "모바일 앱";
    public static final String 게임 = "게임";
    public static final String AI = "AI";
    public static final String 데브옵스 = "데브옵스";
    public static final String 백엔드 = "백엔드";
    public static final String 안드로이드 = "안드로이드";
    public static final String 프론트엔드 = "프론트엔드";
    public static final String iOS = "iOS";
    public static final String 풀스택 = "풀스택";
    public static final List<String> VALID_SUPER_CATEGORIES_1 = new ArrayList<>(Arrays.asList(웹, 모바일_앱));
    public static final List<String> VALID_SUPER_CATEGORIES_2 = new ArrayList<>(Arrays.asList(게임, AI));
    public static final List<String> VALID_SUPER_CATEGORIES_3 = new ArrayList<>(List.of(AI, 데브옵스));
    public static final List<String> VALID_SUB_CATEGORIES_1 = new ArrayList<>(Arrays.asList(백엔드, 안드로이드));
    public static final List<String> VALID_SUB_CATEGORIES_2 = new ArrayList<>(Arrays.asList(프론트엔드, 안드로이드, iOS));
    public static final List<String> VALID_SUB_CATEGORIES_3 = new ArrayList<>(List.of(풀스택));
    public static final List<String> VALID_CATEGORIES_1 = new ArrayList<>() {{
        addAll(VALID_SUPER_CATEGORIES_1);
        addAll(VALID_SUB_CATEGORIES_1);
    }};
    public static final List<String> VALID_CATEGORIES_2 = new ArrayList<>() {{
        addAll(VALID_SUPER_CATEGORIES_2);
        addAll(VALID_SUB_CATEGORIES_2);
    }};
    public static final List<String> VALID_CATEGORIES_3 = new ArrayList<>() {{
        addAll(VALID_SUPER_CATEGORIES_3);
        addAll(VALID_SUB_CATEGORIES_3);
    }};
    public static final String JavaScript = "JavaScript";
    public static final String TypeScript = "TypeScript";
    public static final String Java = "Java";
    public static final String Kotlin = "Kotlin";
    public static final String Swift = "Swift";
    public static final String React = "React";
    public static final String Spring = "Spring";
    public static final String Django = "Django";
    public static final String Nodejs = "Node.js";
    public static final String Vuejs = "Vue.js";
    public static final List<String> VALID_LANGUAGES_1 = new ArrayList<>(Arrays.asList(JavaScript, Java));
    public static final List<String> VALID_LANGUAGES_2 = new ArrayList<>(Arrays.asList(TypeScript, Kotlin));
    public static final List<String> VALID_LANGUAGES_3 = new ArrayList<>(Arrays.asList(Kotlin, Swift));
    public static final List<String> VALID_FRAMEWORKS_1 = new ArrayList<>(Arrays.asList(React, Spring));
    public static final List<String> VALID_FRAMEWORKS_2 = new ArrayList<>(Arrays.asList(Django, Spring));
    public static final List<String> VALID_FRAMEWORKS_3 = new ArrayList<>(Arrays.asList(Nodejs, Vuejs));
    public static final List<String> VALID_STACKS_1 = new ArrayList<>() {{
        addAll(VALID_LANGUAGES_1);
        addAll(VALID_FRAMEWORKS_1);
    }};
    public static final List<String> VALID_STACKS_2 = new ArrayList<>() {{
        addAll(VALID_LANGUAGES_2);
        addAll(VALID_FRAMEWORKS_2);
    }};
    public static final List<String> VALID_STACKS_3 = new ArrayList<>() {{
        addAll(VALID_LANGUAGES_3);
        addAll(VALID_FRAMEWORKS_3);
    }};
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
        List<List<String>> courses = List.of(
                new ArrayList<>(Arrays.asList(VALID_TITLE_1, VALID_TITLE_2, VALID_TITLE_3)),
                new ArrayList<>(Arrays.asList(VALID_TITLE_4, VALID_TITLE_5, VALID_TITLE_6)),
                new ArrayList<>(Arrays.asList(VALID_TITLE_7, VALID_TITLE_8, VALID_TITLE_9))
        );
        return CompanyResponse.builder()
                .id(ids[index])
                .name(names[index])
                .serviceName(serviceNames[index])
                .url(urls[index])
                .serviceUrl(serviceUrls[index])
                .logoUrl(logoUrls[index])
                .courses(courses.get(index))
                .build();
    }

    public static CourseRequest getCourseRequest(int index) {
        index--;
        String[] titles = {VALID_TITLE_1, VALID_TITLE_2, VALID_TITLE_3};
        String[] names = {VALID_NAME_1, VALID_NAME_2, VALID_NAME_3};
        Integer[] generations = {VALID_GENERATION_1, VALID_GENERATION_2, VALID_GENERATION_3};
        String[] urls = {VALID_URL_1, VALID_URL_2, VALID_URL_3};
        String[] companies = {VALID_COM_NAME_1, VALID_COM_NAME_2, VALID_COM_NAME_3};
        String[] locations = {VALID_LOCATION_1, VALID_LOCATION_2, VALID_LOCATION_3};
        List<List<String>> superCategories = List.of(VALID_SUPER_CATEGORIES_1, VALID_SUPER_CATEGORIES_2, VALID_SUPER_CATEGORIES_3);
        List<List<String>> subCategories = List.of(VALID_SUPER_CATEGORIES_1, VALID_SUPER_CATEGORIES_2, VALID_SUPER_CATEGORIES_3);
        List<List<String>> languages = List.of(VALID_LANGUAGES_1, VALID_LANGUAGES_2, VALID_LANGUAGES_3);
        List<List<String>> frameworks = List.of(VALID_FRAMEWORKS_1, VALID_FRAMEWORKS_2, VALID_FRAMEWORKS_3);
        Integer[] costs = {VALID_COST_1, VALID_COST_2, VALID_COST_3};
        Integer[] periods = {VALID_PERIOD_1, VALID_PERIOD_2, VALID_PERIOD_3};
        Dates[] dates = {VALID_DATES_1, VALID_DATES_2, VALID_DATES_3};
        boolean[] recommendeds = {true, false, true};
        boolean[] frees = {true, false, true};
        boolean[] kdts = {true, false, true};
        boolean[] onlines = {true, false, true};
        boolean[] testeds = {true, false, true};
        boolean[] prerequisiteRequireds = {true, false, true};

        return CourseRequest.builder()
                .title(titles[index])
                .name(names[index])
                .generation(generations[index])
                .url(urls[index])
                .companyName(companies[index])
                .location(locations[index])
                .superCategories(superCategories.get(index))
                .subCategories(subCategories.get(index))
                .languages(languages.get(index))
                .frameworks(frameworks.get(index))
                .cost(costs[index])
                .period(periods[index])
                .dates(dates[index])
                .recommended(recommendeds[index])
                .free(frees[index])
                .kdt(kdts[index])
                .online(onlines[index])
                .tested(testeds[index])
                .prerequisiteRequired(prerequisiteRequireds[index])
                .build();
    }

    public static CourseDetailRequest getCourseDetailRequest(int index) {
        index--;
        String[] details = {VALID_DETAIL_1, VALID_DETAIL_2, VALID_DETAIL_3};
        return new CourseDetailRequest(details[index]);
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
        List<List<String>> superCategories = List.of(VALID_SUPER_CATEGORIES_1, VALID_SUPER_CATEGORIES_2, VALID_SUPER_CATEGORIES_3);
        List<List<String>> subCategories = List.of(VALID_SUB_CATEGORIES_1, VALID_SUB_CATEGORIES_2, VALID_SUB_CATEGORIES_3);
        List<List<String>> languages = List.of(VALID_LANGUAGES_1, VALID_LANGUAGES_2, VALID_LANGUAGES_3);
        List<List<String>> frameworks = List.of(VALID_FRAMEWORKS_1, VALID_FRAMEWORKS_2, VALID_FRAMEWORKS_3);
        int[] costs = {VALID_COST_1, VALID_COST_2, VALID_COST_3};
        int[] periods = {VALID_PERIOD_1, VALID_PERIOD_2, VALID_PERIOD_3};
        Dates[] dates = {VALID_DATES_1, VALID_DATES_2, VALID_DATES_3};
        boolean[] recommendeds = {true, false, true};
        boolean[] frees = {true, false, true};
        boolean[] kdts = {true, false, true};
        boolean[] onlines = {true, false, true};
        boolean[] testeds = {true, false, true};
        boolean[] prerequisiteRequireds = {true, false, true};
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
                .superCategories(superCategories.get(index))
                .subCategories(subCategories.get(index))
                .languages(languages.get(index))
                .frameworks(frameworks.get(index))
                .cost(costs[index])
                .period(periods[index])
                .dates(dates[index])
                .isRecommended(recommendeds[index])
                .isFree(frees[index])
                .isKdt(kdts[index])
                .isOnline(onlines[index])
                .isTested(testeds[index])
                .isPrerequisiteRequired(prerequisiteRequireds[index])
                .clicks(clicks[index])
                .bookmarks(bookmarks[index])
                .build();
    }

    public static CourseDetailResponse getCourseDetailResponse(int index) {
        index--;
        Long[] ids = {1L, 2L, 3L};
        String[] titles = {VALID_TITLE_1, VALID_TITLE_2, VALID_TITLE_3};
        String[] names = {VALID_NAME_1, VALID_NAME_2, VALID_NAME_3};
        int[] generations = {VALID_GENERATION_1, VALID_GENERATION_2, VALID_GENERATION_3};
        String[] urls = {VALID_URL_1, VALID_URL_2, VALID_URL_3};
        CompanyResponse[] companies = {getCompanyResponse(1), getCompanyResponse(2), getCompanyResponse(3)};
        String[] locations = {VALID_LOCATION_1, VALID_LOCATION_2, VALID_LOCATION_3};
        List<List<String>> superCategories = List.of(VALID_SUPER_CATEGORIES_1, VALID_SUPER_CATEGORIES_2, VALID_SUPER_CATEGORIES_3);
        List<List<String>> subCategories = List.of(VALID_SUB_CATEGORIES_1, VALID_SUB_CATEGORIES_2, VALID_SUB_CATEGORIES_3);
        List<List<String>> languages = List.of(VALID_LANGUAGES_1, VALID_LANGUAGES_2, VALID_LANGUAGES_3);
        List<List<String>> frameworks = List.of(VALID_FRAMEWORKS_1, VALID_FRAMEWORKS_2, VALID_FRAMEWORKS_3);
        int[] costs = {VALID_COST_1, VALID_COST_2, VALID_COST_3};
        int[] periods = {VALID_PERIOD_1, VALID_PERIOD_2, VALID_PERIOD_3};
        Dates[] dates = {VALID_DATES_1, VALID_DATES_2, VALID_DATES_3};
        boolean[] recommendeds = {true, false, true};
        boolean[] frees = {true, false, true};
        boolean[] kdts = {true, false, true};
        boolean[] onlines = {true, false, true};
        boolean[] testeds = {true, false, true};
        boolean[] prerequisiteRequireds = {true, false, true};
        int[] clicks = {VALID_CLICKS_1, VALID_CLICKS_2, VALID_CLICKS_3};
        int[] bookmarks = {VALID_BOOKMARKS_1, VALID_BOOKMARKS_2, VALID_BOOKMARKS_3};

        return CourseDetailResponse.builder()
                .id(ids[index])
                .title(titles[index])
                .name(names[index])
                .generation(generations[index])
                .url(urls[index])
                .company(companies[index])
                .location(locations[index])
                .superCategories(superCategories.get(index))
                .subCategories(subCategories.get(index))
                .languages(languages.get(index))
                .frameworks(frameworks.get(index))
                .cost(costs[index])
                .period(periods[index])
                .dates(dates[index])
                .detail("<p> Course Detail <p>")
                .isRecommended(recommendeds[index])
                .isFree(frees[index])
                .isKdt(kdts[index])
                .isOnline(onlines[index])
                .isTested(testeds[index])
                .isPrerequisiteRequired(prerequisiteRequireds[index])
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

    public static Course getCourse(int index) {
        index--;
        String[] titles = {VALID_TITLE_1, VALID_TITLE_2, VALID_TITLE_3};
        String[] names = {VALID_NAME_1, VALID_NAME_2, VALID_NAME_3};
        int[] generations = {VALID_GENERATION_1, VALID_GENERATION_2, VALID_GENERATION_3};
        String[] urls = {VALID_URL_1, VALID_URL_2, VALID_URL_3};
        Company[] companies = {getCompany(1), getCompany(2), getCompany(3)};
        String[] locations = {VALID_LOCATION_1, VALID_LOCATION_2, VALID_LOCATION_3};
        List<List<String>> categories = List.of(VALID_CATEGORIES_1, VALID_CATEGORIES_2, VALID_CATEGORIES_3);
        int[] costs = {VALID_COST_1, VALID_COST_2, VALID_COST_3};
        int[] periods = {VALID_PERIOD_1, VALID_PERIOD_2, VALID_PERIOD_3};
        Dates[] dates = {VALID_DATES_1, VALID_DATES_2, VALID_DATES_3};
        boolean[] recommendeds = {true, false, true};
        boolean[] frees = {true, false, true};
        boolean[] kdts = {true, false, true};
        boolean[] onlines = {true, false, true};
        boolean[] testeds = {true, false, true};
        boolean[] prerequisiteRequireds = {true, false, true};
        int[] clicks = {VALID_CLICKS_1, VALID_CLICKS_2, VALID_CLICKS_3};
        int[] bookmarks = {VALID_BOOKMARKS_1, VALID_BOOKMARKS_2, VALID_BOOKMARKS_3};

        return Course.builder()
                .title(titles[index])
                .name(names[index])
                .generation(generations[index])
                .url(urls[index])
                .company(companies[index])
                .location(locations[index])
                .categories(categories.get(index))
                .cost(costs[index])
                .period(periods[index])
                .dates(dates[index])
                .isRecommended(recommendeds[index])
                .isFree(frees[index])
                .isKdt(kdts[index])
                .isOnline(onlines[index])
                .isTested(testeds[index])
                .isPrerequisiteRequired(prerequisiteRequireds[index])
                .clicks(clicks[index])
                .bookmarks(bookmarks[index])
                .build();
    }

}
