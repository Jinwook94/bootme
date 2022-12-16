package com.bootme.util.fixture;

import com.bootme.course.domain.*;
import com.bootme.course.dto.CompanyRequest;
import com.bootme.course.dto.CompanyResponse;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bootme.course.domain.CostType.*;
import static com.bootme.course.domain.OnOffline.*;
import static com.bootme.course.domain.Prerequisites.*;
import static com.bootme.course.domain.Tag.*;

public class CourseFixture {

    public static final String VALID_TITLE_1 = "네이버 서버 개발자 부트캠프";
    public static final String VALID_TITLE_2 = "카카오 서버 개발자 부트캠프";
    public static final String VALID_TITLE_3 = "라인 서버 개발자 부트캠프";
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
    public static final Dates VALID_DATES_1 = Dates.builder()
            .registrationStartDate(LocalDate.of(2021, 1, 1))
            .registrationEndDate(LocalDate.of(2021, 1, 2))
            .courseStartDate(LocalDate.of(2021, 1, 3))
            .courseEndDate(LocalDate.of(2021, 7, 4))
            .build();
    public static final Dates VALID_DATES_2 = Dates.builder()
            .registrationStartDate(LocalDate.of(2022, 1, 1))
            .registrationEndDate(LocalDate.of(2022, 2, 1))
            .courseStartDate(LocalDate.of(2022, 2, 10))
            .courseEndDate(LocalDate.of(2022, 8, 31))
            .build();
    public static final Dates VALID_DATES_3 = Dates.builder()
            .registrationStartDate(LocalDate.of(2023, 1, 1))
            .registrationEndDate(LocalDate.of(2023, 2, 1))
            .courseStartDate(LocalDate.of(2023, 3, 1))
            .courseEndDate(LocalDate.of(2023, 4, 1))
            .build();
    public static final OnOffline VALID_ONOFFLINE_1 = 온라인;
    public static final OnOffline VALID_ONOFFLINE_2 = 오프라인;
    public static final OnOffline VALID_ONOFFLINE_3 = 온오프라인혼합;
    public static final List<Tag> VALID_TAGS_1 = new ArrayList<>(Arrays.asList(백엔드, Java, Spring));
    public static final List<Tag> VALID_TAGS_2 = new ArrayList<>(Arrays.asList(프론트엔드, Javascript, React));
    public static final List<Tag> VALID_TAGS_3 = new ArrayList<>(Arrays.asList(백엔드, Javascript, Nodejs));
    public static final Prerequisites VALID_PREREQUISITES_1 = 노베이스;
    public static final Prerequisites VALID_PREREQUISITES_2 = 프로그래밍언어기초;
    public static final Prerequisites VALID_PREREQUISITES_3 = 코딩테스트풀이가능;
    public static final boolean VALID_ISRECOMMENDED_1 = true;
    public static final boolean VALID_ISRECOMMENDED_2 = false;
    public static final boolean VALID_ISRECOMMENDED_3 = false;
    public static final boolean VALID_ISTESTED_1 = false;
    public static final boolean VALID_ISTESTED_2 = true;
    public static final boolean VALID_ISTESTED_3 = true;
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

    public static final Course VALID_COURSE_1 = Course.builder()
            .title(VALID_TITLE_1)
            .url(VALID_URL_1)
            .company(VALID_COMPANY_1)
            .location(VALID_LOCATION_1)
            .cost(VALID_COST_1)
            .costType(VALID_CostType_1)
            .dates(VALID_DATES_1)
            .onoffline(VALID_ONOFFLINE_1)
            .tags(VALID_TAGS_1)
            .prerequisites(VALID_PREREQUISITES_1)
            .isRecommended(VALID_ISRECOMMENDED_1)
            .isTested(VALID_ISTESTED_1)
            .build();

    public static final Course VALID_COURSE_2 = Course.builder()
            .title(VALID_TITLE_2)
            .url(VALID_URL_2)
            .company(VALID_COMPANY_2)
            .location(VALID_LOCATION_2)
            .cost(VALID_COST_2)
            .costType(VALID_CostType_2)
            .dates(VALID_DATES_2)
            .onoffline(VALID_ONOFFLINE_2)
            .tags(VALID_TAGS_2)
            .prerequisites(VALID_PREREQUISITES_2)
            .isRecommended(VALID_ISRECOMMENDED_2)
            .isTested(VALID_ISTESTED_2)
            .build();

    public static final Course VALID_COURSE_3 = Course.builder()
            .title(VALID_TITLE_3)
            .url(VALID_URL_3)
            .company(VALID_COMPANY_3)
            .location(VALID_LOCATION_3)
            .cost(VALID_COST_3)
            .costType(VALID_CostType_3)
            .dates(VALID_DATES_3)
            .onoffline(VALID_ONOFFLINE_3)
            .tags(VALID_TAGS_3)
            .prerequisites(VALID_PREREQUISITES_3)
            .isRecommended(VALID_ISRECOMMENDED_3)
            .isTested(VALID_ISTESTED_3)
            .build();

    public static final CourseRequest VALID_COURSE_REQUEST_1 = CourseRequest.builder()
            .title(VALID_TITLE_1)
            .url(VALID_URL_1)
            .companyName(VALID_COMPANY_1.getName())
            .location(VALID_LOCATION_1)
            .cost(VALID_COST_1)
            .costType(VALID_CostType_1.name())
            .dates(VALID_DATES_1)
            .onOffline(VALID_ONOFFLINE_1.name())
            .tags(VALID_TAGS_1)
            .prerequisites(VALID_PREREQUISITES_1.name())
            .recommended(VALID_ISRECOMMENDED_1)
            .tested(VALID_ISTESTED_1)
            .build();

    public static final CourseRequest VALID_COURSE_REQUEST_2 = CourseRequest.builder()
            .title(VALID_TITLE_2)
            .url(VALID_URL_2)
            .companyName(VALID_COMPANY_2.getName())
            .location(VALID_LOCATION_2)
            .cost(VALID_COST_2)
            .costType(VALID_CostType_2.name())
            .dates(VALID_DATES_2)
            .onOffline(VALID_ONOFFLINE_2.name())
            .tags(VALID_TAGS_2)
            .prerequisites(VALID_PREREQUISITES_2.name())
            .recommended(VALID_ISRECOMMENDED_2)
            .tested(VALID_ISTESTED_2)
            .build();

    public static final CourseRequest VALID_COURSE_REQUEST_3 = CourseRequest.builder()
            .title(VALID_TITLE_3)
            .url(VALID_URL_3)
            .companyName(VALID_COMPANY_3.getName())
            .location(VALID_LOCATION_3)
            .cost(VALID_COST_3)
            .costType(VALID_CostType_3.name())
            .dates(VALID_DATES_3)
            .onOffline(VALID_ONOFFLINE_3.name())
            .tags(VALID_TAGS_3)
            .prerequisites(VALID_PREREQUISITES_3.name())
            .recommended(VALID_ISRECOMMENDED_3)
            .tested(VALID_ISTESTED_3)
            .build();

    public static final CourseResponse VALID_COURSE_RESPONSE_1 = CourseResponse.builder()
            .id(1L)
            .title(VALID_TITLE_1)
            .url(VALID_URL_1)
            .companyId(1L)
            .companyName(VALID_COMPANY_1.getName())
            .location(VALID_LOCATION_1)
            .cost(VALID_COST_1)
            .costType(VALID_CostType_1.name())
            .dates(VALID_DATES_1)
            .onOffline(VALID_ONOFFLINE_1.name())
            .tags(VALID_TAGS_1)
            .prerequisites(VALID_PREREQUISITES_1.name())
            .recommended(VALID_ISRECOMMENDED_1)
            .tested(VALID_ISTESTED_1)
            .build();

    public static final CourseResponse VALID_COURSE_RESPONSE_2 = CourseResponse.builder()
            .id(2L)
            .title(VALID_TITLE_2)
            .url(VALID_URL_2)
            .companyId(2L)
            .companyName(VALID_COMPANY_2.getName())
            .location(VALID_LOCATION_2)
            .cost(VALID_COST_2)
            .costType(VALID_CostType_2.name())
            .dates(VALID_DATES_2)
            .onOffline(VALID_ONOFFLINE_2.name())
            .tags(VALID_TAGS_2)
            .prerequisites(VALID_PREREQUISITES_2.name())
            .recommended(VALID_ISRECOMMENDED_2)
            .tested(VALID_ISTESTED_2)
            .build();

    public static final CourseResponse VALID_COURSE_RESPONSE_3 = CourseResponse.builder()
            .id(3L)
            .title(VALID_TITLE_3)
            .url(VALID_URL_3)
            .companyId(3L)
            .companyName(VALID_COMPANY_3.getName())
            .location(VALID_LOCATION_3)
            .cost(VALID_COST_3)
            .costType(VALID_CostType_3.name())
            .dates(VALID_DATES_3)
            .onOffline(VALID_ONOFFLINE_3.name())
            .tags(VALID_TAGS_3)
            .prerequisites(VALID_PREREQUISITES_3.name())
            .recommended(VALID_ISRECOMMENDED_3)
            .tested(VALID_ISTESTED_3)
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

}
