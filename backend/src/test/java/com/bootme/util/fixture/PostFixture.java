package com.bootme.util.fixture;

import com.bootme.course.domain.Dates;
import com.bootme.post.dto.PostDetailResponse;
import com.bootme.post.dto.PostRequest;
import com.bootme.post.dto.PostResponse;

import java.time.LocalDate;

import static com.bootme.post.domain.PostStatus.*;

public class PostFixture {
    public static final String VALID_WRITER_NICKNAME_1 = "철수";
    public static final String VALID_WRITER_NICKNAME_2 = "영희";
    public static final String VALID_WRITER_NICKNAME_3 = "지수";

    public static final String VALID_PROFILE_IMAGE_1 = "https://bootme-images.s3.ap-northeast-2.amazonaws.com/logos/stack/java.png";
    public static final String VALID_PROFILE_IMAGE_2 = "https://bootme-images.s3.ap-northeast-2.amazonaws.com/logos/stack/javascript.png";
    public static final String VALID_PROFILE_IMAGE_3 = "https://bootme-images.s3.ap-northeast-2.amazonaws.com/logos/stack/kotlin.png";

    public static final String VALID_TOPIC_1 = "자유";
    public static final String VALID_TOPIC_2 = "부트캠프 질문";
    public static final String VALID_TOPIC_3 = "개발 질문";

    public static final String VALID_POST_TITLE_1 = "안녕하세요";
    public static final String VALID_POST_TITLE_2 = "반가워요";
    public static final String VALID_POST_TITLE_3 = "좋은 하루";

    public static final String VALID_CONTENT_EXCERPT_1 = "국가는 균형있는 국민경제의 성장 및 안정과 적정한 소득의 분배를 유지하고, 시장의 지배와 경제력의 남용을 방지하며";
    public static final String VALID_CONTENT_EXCERPT_2 = "경제주체간의 조화를 통한 경제의 민주화를 위하여 경제에 관한 규제와 조정을 할 수 있다.";
    public static final String VALID_CONTENT_EXCERPT_3 = "국가는 평생교육을 진흥하여야 한다. 제안된 헌법개정안은 대통령이 20일 이상의 기간 이를 공고하여야 한다.";

    public static final String VALID_STATUS_1 = DISPLAY.toString();
    public static final String VALID_STATUS_2 = DELETED.toString();
    public static final String VALID_STATUS_3 = HIDDEN.toString();

    public static final String VALID_VOTED_1 = "UPVOTE";
    public static final String VALID_VOTED_2 = "DOWNVOTE";
    public static final String VALID_VOTED_3 = "NONE";

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

    public static PostRequest getPostRequest(int index) {
        index--;
        String[] topics = {VALID_TOPIC_1, VALID_TOPIC_2, VALID_TOPIC_3};
        String[] titles = {VALID_POST_TITLE_1, VALID_POST_TITLE_2, VALID_POST_TITLE_3};
        String[] contents = {VALID_CONTENT_EXCERPT_1, VALID_CONTENT_EXCERPT_2, VALID_CONTENT_EXCERPT_3};

        return PostRequest.builder()
                .topic(topics[index])
                .title(titles[index])
                .content(contents[index])
                .build();
    }

    public static PostDetailResponse getPostDetailResponse(int index) {
        index--;
        String[] nickNames = {VALID_WRITER_NICKNAME_1, VALID_WRITER_NICKNAME_2, VALID_WRITER_NICKNAME_3};
        String[] profileImages = {VALID_PROFILE_IMAGE_1, VALID_PROFILE_IMAGE_2, VALID_PROFILE_IMAGE_3};
        String[] topics = {VALID_TOPIC_1, VALID_TOPIC_2, VALID_TOPIC_3};
        String[] titles = {VALID_POST_TITLE_1, VALID_POST_TITLE_2, VALID_POST_TITLE_3};
        String[] contentExcerpts = {VALID_CONTENT_EXCERPT_1, VALID_CONTENT_EXCERPT_2, VALID_CONTENT_EXCERPT_3};
        String[] statuses = {VALID_STATUS_1, VALID_STATUS_2, VALID_STATUS_3};
        String[] votes = {VALID_VOTED_1, VALID_VOTED_2, VALID_VOTED_3};

        return PostDetailResponse.builder()
                .id((long) index + 1)
                .writerId((long) index + 1)
                .writerNickname(nickNames[index])
                .writerProfileImage(profileImages[index])
                .topic(topics[index])
                .title(titles[index])
                .content(contentExcerpts[index])
                .likes(index + 10)
                .clicks(index + 20)
                .bookmarks(index + 30)
                .status(statuses[index])
                .createdAt(1L)
                .modifiedAt(1L)
                .commentCount(index + 3)
                .voted(votes[index])
                .isBookmarked(false)
                .build();
    }

    public static PostResponse getPostResponse(int index) {
        index--;
        String[] nickNames = {VALID_WRITER_NICKNAME_1, VALID_WRITER_NICKNAME_2, VALID_WRITER_NICKNAME_3};
        String[] profileImages = {VALID_PROFILE_IMAGE_1, VALID_PROFILE_IMAGE_2, VALID_PROFILE_IMAGE_3};
        String[] topics = {VALID_TOPIC_1, VALID_TOPIC_2, VALID_TOPIC_3};
        String[] titles = {VALID_POST_TITLE_1, VALID_POST_TITLE_2, VALID_POST_TITLE_3};
        String[] contentExcerpts = {VALID_CONTENT_EXCERPT_1, VALID_CONTENT_EXCERPT_2, VALID_CONTENT_EXCERPT_3};
        String[] statuses = {VALID_STATUS_1, VALID_STATUS_2, VALID_STATUS_3};
        String[] votes = {VALID_VOTED_1, VALID_VOTED_2, VALID_VOTED_3};

        return PostResponse.builder()
                .id((long) index + 1)
                .writerId((long) index + 1)
                .writerNickname(nickNames[index])
                .writerProfileImage(profileImages[index])
                .topic(topics[index])
                .title(titles[index])
                .contentExcerpt(contentExcerpts[index])
                .likes(index + 10)
                .clicks(index + 20)
                .bookmarks(index + 30)
                .status(statuses[index])
                .createdAt(1L)
                .modifiedAt(1L)
                .commentCount(index + 1)
                .voted(votes[index])
                .isBookmarked(false)
                .isViewed(false)
                .build();
    }

}

