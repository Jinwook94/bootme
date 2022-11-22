package com.bootme.util.fixture;

import com.bootme.course.domain.Company;
import com.bootme.course.domain.Course;
import com.bootme.course.domain.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bootme.course.domain.Tag.*;

public class CourseFixture {

    public static final String VALID_URL_1 = "www.naver.com";
    public static final String VALID_URL_2 = "www.kakao.com";
    public static final String VALID_TITLE_1 = "네이버 서버 개발자 부트캠프";
    public static final String VALID_TITLE_2 = "카카오 서버 개발자 부트캠프";
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
    public static final List<Tag> VALID_TAGS_1 = new ArrayList<>(Arrays.asList(백엔드, Java, Spring));
    public static final List<Tag> VALID_TAGS_2 = new ArrayList<>(Arrays.asList(프론트엔드, Javascript, React));

}
