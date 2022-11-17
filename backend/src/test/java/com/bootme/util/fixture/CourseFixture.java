package com.bootme.util.fixture;

import com.bootme.course.domain.Company;
import com.bootme.course.domain.Course;
import com.bootme.course.domain.Tag;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bootme.course.domain.Tag.*;

public class CourseFixture {

    public static final String VALID_URL = "www.naver.com";
    public static final String VALID_TITLE = "네이버 서버 개발자 부트캠프";
    public static final Company VALID_COMPANY = Company.builder()
                                                    .url("www.naver.com")
                                                    .name("네이버")
                                                    .build();
    public static final String VALID_LOCATION = "서울시 강남구";
    public static final List<Tag> VALID_TAGS = new ArrayList<>(Arrays.asList(백엔드, Java, Spring));

}
