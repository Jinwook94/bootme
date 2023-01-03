package com.bootme.util.fixture;

import com.bootme.course.domain.*;
import com.bootme.course.domain.Stack;
import com.bootme.course.dto.CompanyRequest;
import com.bootme.course.dto.CompanyResponse;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;

import java.time.LocalDate;
import java.util.*;

import static com.bootme.course.domain.CostType.*;
import static com.bootme.course.domain.OnOffline.*;
import static com.bootme.course.domain.Prerequisites.*;

public class CourseFixture {

    public static final String VALID_TITLE_1 = "네이버 서버 개발자 부트캠프 1기";
    public static final String VALID_TITLE_2 = "카카오 프론트엔드 개발자 부트캠프 2기";
    public static final String VALID_TITLE_3 = "라인 서버 개발자 부트캠프 3기";
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
    public static final CostType VALID_CostType_1 = 무료;
    public static final CostType VALID_CostType_2 = 무료국비;
    public static final CostType VALID_CostType_3 = 유료;
    public static final int VALID_PERIOD_1 = 90;
    public static final int VALID_PERIOD_2 = 28;
    public static final int VALID_PERIOD_3 = 210;
    public static final Dates VALID_DATES_1 = Dates.builder()
            .registrationStartDate(LocalDate.of(2021, 1, 1))
            .registrationEndDate(LocalDate.of(2021, 1, 31))
            .courseStartDate(LocalDate.of(2021, 2, 1))
            .courseEndDate(LocalDate.of(2021, 4, 30))
            .build();
    public static final Dates VALID_DATES_2 = Dates.builder()
            .registrationStartDate(LocalDate.of(2022, 1, 1))
            .registrationEndDate(LocalDate.of(2022, 1, 31))
            .courseStartDate(LocalDate.of(2022, 2, 1))
            .courseEndDate(LocalDate.of(2022, 2, 28))
            .build();
    public static final Dates VALID_DATES_3 = Dates.builder()
            .registrationStartDate(LocalDate.of(2023, 1, 1))
            .registrationEndDate(LocalDate.of(2023, 1, 31))
            .courseStartDate(LocalDate.of(2023, 2, 1))
            .courseEndDate(LocalDate.of(2023, 7, 31))
            .build();
    public static final OnOffline VALID_ONOFFLINE_1 = 온라인;
    public static final OnOffline VALID_ONOFFLINE_2 = 오프라인;
    public static final OnOffline VALID_ONOFFLINE_3 = 온오프라인혼합;
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
    public static final Prerequisites VALID_PREREQUISITES_1 = 노베이스;
    public static final Prerequisites VALID_PREREQUISITES_2 = 프로그래밍언어기초;
    public static final Prerequisites VALID_PREREQUISITES_3 = 코딩테스트풀이가능;
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

    public static final Company VALID_COMPANY_1 = Company.builder()
            .name(VALID_COM_NAME_1)
            .serviceName(VALID_COM_SERVICE_NAME_1)
            .url(VALID_COM_URL_1)
            .serviceUrl(VALID_COM_SERVICE_URL_1)
            .logoUrl(VALID_COM_LOGO_URL_1)
            .courses(new ArrayList<>())
            .build();

    public static final Company VALID_COMPANY_2 = Company.builder()
            .name(VALID_COM_NAME_2)
            .serviceName(VALID_COM_SERVICE_NAME_2)
            .url(VALID_COM_URL_2)
            .serviceUrl(VALID_COM_SERVICE_URL_2)
            .logoUrl(VALID_COM_LOGO_URL_2)
            .courses(new ArrayList<>())
            .build();

    public static final Company VALID_COMPANY_3 = Company.builder()
            .name(VALID_COM_NAME_3)
            .serviceName(VALID_COM_SERVICE_NAME_3)
            .url(VALID_COM_URL_3)
            .serviceUrl(VALID_COM_SERVICE_URL_3)
            .logoUrl(VALID_COM_LOGO_URL_3)
            .courses(new ArrayList<>())
            .build();

    public static final CompanyRequest VALID_COMPANY_REQUEST_1 = CompanyRequest.builder()
            .name(VALID_COM_NAME_1)
            .serviceName(VALID_COM_SERVICE_NAME_1)
            .url(VALID_COM_URL_1)
            .serviceUrl(VALID_COM_SERVICE_URL_1)
            .logoUrl(VALID_COM_LOGO_URL_1)
            .build();

    public static final CompanyRequest VALID_COMPANY_REQUEST_2 = CompanyRequest.builder()
            .name(VALID_COM_NAME_2)
            .serviceName(VALID_COM_SERVICE_NAME_2)
            .url(VALID_COM_URL_2)
            .serviceUrl(VALID_COM_SERVICE_URL_2)
            .logoUrl(VALID_COM_LOGO_URL_2)
            .build();

    public static final CompanyRequest VALID_COMPANY_REQUEST_3 = CompanyRequest.builder()
            .name(VALID_COM_NAME_3)
            .serviceName(VALID_COM_SERVICE_NAME_3)
            .url(VALID_COM_URL_3)
            .serviceUrl(VALID_COM_SERVICE_URL_3)
            .logoUrl(VALID_COM_LOGO_URL_3)
            .build();

    public static final CompanyResponse VALID_COMPANY_RESPONSE_1 = CompanyResponse.builder()
            .id(1L)
            .name(VALID_COM_NAME_1)
            .serviceName(VALID_COM_SERVICE_NAME_1)
            .url(VALID_COM_URL_1)
            .serviceUrl(VALID_COM_SERVICE_URL_1)
            .logoUrl(VALID_COM_LOGO_URL_1)
            .courses(new ArrayList<>(Arrays.asList("네이버 부트캠프", "카카오 부트캠프", "라인 부트캠프")))
            .build();

    public static final CompanyResponse VALID_COMPANY_RESPONSE_2 = CompanyResponse.builder()
            .id(2L)
            .name(VALID_COM_NAME_2)
            .serviceName(VALID_COM_SERVICE_NAME_2)
            .url(VALID_COM_URL_2)
            .serviceUrl(VALID_COM_SERVICE_URL_2)
            .logoUrl(VALID_COM_LOGO_URL_2)
            .courses(new ArrayList<>(Arrays.asList("쿠팡 부트캠프", "배민 부트캠프", "토스 부트캠프")))
            .build();

    public static final CompanyResponse VALID_COMPANY_RESPONSE_3 = CompanyResponse.builder()
            .id(3L)
            .name(VALID_COM_NAME_3)
            .serviceName(VALID_COM_SERVICE_NAME_3)
            .url(VALID_COM_URL_3)
            .serviceUrl(VALID_COM_SERVICE_URL_3)
            .logoUrl(VALID_COM_LOGO_URL_3)
            .courses(new ArrayList<>(Arrays.asList("페이스북 부트캠프", "아마존 부트캠프", "구글 부트캠프")))
            .build();

    public static final Course VALID_COURSE_1 = Course.builder()
            .title(VALID_TITLE_1)
            .name(VALID_NAME_1)
            .generation(VALID_GENERATION_1)
            .url(VALID_URL_1)
            .company(VALID_COMPANY_1)
            .location(VALID_LOCATION_1)
            .onoffline(VALID_ONOFFLINE_1)
            .categories(CATEGORY1)
            .stacks(STACK1)
            .prerequisites(VALID_PREREQUISITES_1)
            .cost(VALID_COST_1)
            .costType(VALID_CostType_1)
            .period(VALID_PERIOD_1)
            .dates(VALID_DATES_1)
            .isRecommended(true)
            .isTested(true)
            .build();

    public static final Course VALID_COURSE_2 = Course.builder()
            .title(VALID_TITLE_2)
            .name(VALID_NAME_2)
            .generation(VALID_GENERATION_2)
            .url(VALID_URL_2)
            .company(VALID_COMPANY_2)
            .location(VALID_LOCATION_2)
            .onoffline(VALID_ONOFFLINE_2)
            .categories(CATEGORY2)
            .stacks(STACK2)
            .prerequisites(VALID_PREREQUISITES_2)
            .cost(VALID_COST_2)
            .costType(VALID_CostType_2)
            .period(VALID_PERIOD_2)
            .dates(VALID_DATES_2)
            .isRecommended(false)
            .isTested(false)
            .build();

    public static final Course VALID_COURSE_3 = Course.builder()
            .title(VALID_TITLE_3)
            .name(VALID_NAME_3)
            .generation(VALID_GENERATION_3)
            .url(VALID_URL_3)
            .company(VALID_COMPANY_3)
            .location(VALID_LOCATION_3)
            .onoffline(VALID_ONOFFLINE_3)
            .categories(CATEGORY3)
            .stacks(STACK3)
            .prerequisites(VALID_PREREQUISITES_3)
            .cost(VALID_COST_3)
            .costType(VALID_CostType_3)
            .period(VALID_PERIOD_3)
            .dates(VALID_DATES_3)
            .isRecommended(true)
            .isTested(true)
            .build();

    public static final CourseRequest VALID_COURSE_REQUEST_1 = CourseRequest.builder()
            .title(VALID_TITLE_1)
            .name(VALID_NAME_1)
            .generation(VALID_GENERATION_1)
            .url(VALID_URL_1)
            .companyName(VALID_COMPANY_1.getName())
            .location(VALID_LOCATION_1)
            .onOffline(VALID_ONOFFLINE_1.name())
            .categories(CATEGORY1)
            .stacks(STACK1)
            .prerequisites(VALID_PREREQUISITES_1.name())
            .cost(VALID_COST_1)
            .costType(VALID_CostType_1.name())
            .period(VALID_PERIOD_1)
            .dates(VALID_DATES_1)
            .isRecommended(true)
            .isTested(true)
            .build();

    public static final CourseRequest VALID_COURSE_REQUEST_2 = CourseRequest.builder()
            .title(VALID_TITLE_2)
            .name(VALID_NAME_2)
            .generation(VALID_GENERATION_2)
            .url(VALID_URL_2)
            .companyName(VALID_COMPANY_2.getName())
            .location(VALID_LOCATION_2)
            .onOffline(VALID_ONOFFLINE_2.name())
            .categories(CATEGORY2)
            .stacks(STACK2)
            .prerequisites(VALID_PREREQUISITES_2.name())
            .cost(VALID_COST_2)
            .costType(VALID_CostType_2.name())
            .period(VALID_PERIOD_2)
            .dates(VALID_DATES_2)
            .isRecommended(false)
            .isTested(false)
            .build();

    public static final CourseRequest VALID_COURSE_REQUEST_3 = CourseRequest.builder()
            .title(VALID_TITLE_3)
            .name(VALID_NAME_3)
            .generation(VALID_GENERATION_3)
            .url(VALID_URL_3)
            .companyName(VALID_COMPANY_3.getName())
            .location(VALID_LOCATION_3)
            .onOffline(VALID_ONOFFLINE_3.name())
            .categories(CATEGORY3)
            .stacks(STACK3)
            .prerequisites(VALID_PREREQUISITES_3.name())
            .cost(VALID_COST_3)
            .costType(VALID_CostType_3.name())
            .period(VALID_PERIOD_3)
            .dates(VALID_DATES_3)
            .isRecommended(true)
            .isTested(true)
            .build();

    public static final CourseResponse VALID_COURSE_RESPONSE_1 = CourseResponse.builder()
            .id(1L)
            .name(VALID_NAME_1)
            .generation(VALID_GENERATION_1)
            .title(VALID_TITLE_1)
            .url(VALID_URL_1)
            .location(VALID_LOCATION_1)
            .onOffline(VALID_ONOFFLINE_1.name())
            .categories(VALID_CATEGORY_1)
            .stacks(VALID_STACK_1)
            .prerequisites(VALID_PREREQUISITES_1.name())
            .cost(VALID_COST_1)
            .costType(VALID_CostType_1.name())
            .period(VALID_PERIOD_1)
            .dates(VALID_DATES_1)
            .company(VALID_COMPANY_RESPONSE_1)
            .isRecommended(true)
            .isTested(true)
            .isRegisterOpen(false)
            .build();

    public static final CourseResponse VALID_COURSE_RESPONSE_2 = CourseResponse.builder()
            .id(2L)
            .name(VALID_NAME_2)
            .generation(VALID_GENERATION_2)
            .title(VALID_TITLE_2)
            .url(VALID_URL_2)
            .location(VALID_LOCATION_2)
            .onOffline(VALID_ONOFFLINE_2.name())
            .categories(VALID_CATEGORY_2)
            .stacks(VALID_STACK_2)
            .prerequisites(VALID_PREREQUISITES_2.name())
            .cost(VALID_COST_2)
            .costType(VALID_CostType_2.name())
            .period(VALID_PERIOD_2)
            .dates(VALID_DATES_2)
            .company(VALID_COMPANY_RESPONSE_2)
            .isRecommended(false)
            .isTested(false)
            .isRegisterOpen(false)
            .build();

    public static final CourseResponse VALID_COURSE_RESPONSE_3 = CourseResponse.builder()
            .id(3L)
            .name(VALID_NAME_3)
            .generation(VALID_GENERATION_3)
            .title(VALID_TITLE_3)
            .url(VALID_URL_3)
            .location(VALID_LOCATION_3)
            .onOffline(VALID_ONOFFLINE_3.name())
            .categories(VALID_CATEGORY_3)
            .stacks(VALID_STACK_3)
            .prerequisites(VALID_PREREQUISITES_3.name())
            .cost(VALID_COST_3)
            .costType(VALID_CostType_3.name())
            .period(VALID_PERIOD_3)
            .dates(VALID_DATES_3)
            .company(VALID_COMPANY_RESPONSE_3)
            .isRecommended(true)
            .isTested(true)
            .isRegisterOpen(false)
            .build();

    private static Map<String, List<String>> categoryToMap(Category category){
        Map<String, List<String>> categories = new HashMap<>();
        categories.put("super", category.getSuperCategory());
        categories.put("sub", category.getSubCategory());
        return categories;
    }

    public static Map<String, List<String>> stackToMap(Stack stack){
        Map<String, List<String>> stacks = new HashMap<>();
        stacks.put("super", stack.getLanguages());
        stacks.put("sub", stack.getFrameworks());
        return stacks;
    }

}
