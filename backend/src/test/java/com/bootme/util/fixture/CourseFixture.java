package com.bootme.util.fixture;

import com.bootme.course.domain.*;
import org.hibernate.type.TrueFalseType;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bootme.course.domain.CostType.*;
import static com.bootme.course.domain.OnOffline.오프라인;
import static com.bootme.course.domain.OnOffline.온라인;
import static com.bootme.course.domain.Prerequisites.*;
import static com.bootme.course.domain.Tag.*;

public class CourseFixture {

    public static final String VALID_TITLE_1 = "네이버 서버 개발자 부트캠프";
    public static final String VALID_TITLE_2 = "카카오 서버 개발자 부트캠프";
    public static final String VALID_URL_1 = "www.naver.com";
    public static final String VALID_URL_2 = "www.kakao.com";
    public static final Company VALID_COMPANY_1 = Company.builder()
                                                .url("www.naver.com")
                                                .name("네이버")
                                                .courses(new ArrayList<>())
                                                .build();
    public static final Company VALID_COMPANY_2 = Company.builder()
                                                .url("www.kakao.com")
                                                .name("카카오")
                                                .courses(new ArrayList<>())
                                                .build();
    public static final String VALID_LOCATION_1 = "서울시 강남구";
    public static final String VALID_LOCATION_2 = "서울시 관악구";
    public static final int VALID_COST_1 = 1000000;
    public static final int VALID_COST_2 = 2000000;
    public static final CostType VALID_CostType_1 = 무료;
    public static final CostType VALID_CostType_2 = 유료;
    public static final Dates VALID_DATES_1 = Dates.builder()
                                                .registrationStartDate(LocalDate.of(2022, 12, 5))
                                                .registrationEndDate(LocalDate.of(2023, 1, 10))
                                                .courseStartDate(LocalDate.of(2023, 1, 20))
                                                .courseEndDate(LocalDate.of(2023, 7, 31))
                                                .build();
    public static final Dates VALID_DATES_2 = Dates.builder()
                                                .registrationStartDate(LocalDate.of(2022, 1, 1))
                                                .registrationEndDate(LocalDate.of(2022, 2, 1))
                                                .courseStartDate(LocalDate.of(2022, 2, 10))
                                                .courseEndDate(LocalDate.of(2022, 8, 31))
                                                .build();
    public static final OnOffline VALID_ONOFFLINE_1 = 온라인;
    public static final OnOffline VALID_ONOFFLINE_2 = 오프라인;
    public static final List<Tag> VALID_TAGS_1 = new ArrayList<>(Arrays.asList(백엔드, Java, Spring));
    public static final List<Tag> VALID_TAGS_2 = new ArrayList<>(Arrays.asList(프론트엔드, Javascript, React));
    public static final Prerequisites VALID_PREREQUISITES_1 = 노베이스;
    public static final Prerequisites VALID_PREREQUISITES_2 = 코딩테스트풀이가능;
    public static final boolean VALID_ISRECOMMENDED_1 = true;
    public static final boolean VALID_ISRECOMMENDED_2 = false;
    public static final boolean VALID_ISTESTED_1 = false;
    public static final boolean VALID_ISTESTED_2 = true;

}
