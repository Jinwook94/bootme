package com.bootme.util.fixture;

import com.bootme.comment.dto.CommentRequest;
import com.bootme.comment.dto.CommentResponse;

import static com.bootme.post.domain.PostStatus.*;

public class CommentFixture {

    public static final String VALID_WRITER_NICKNAME_1 = "철수";
    public static final String VALID_WRITER_NICKNAME_2 = "영희";
    public static final String VALID_WRITER_NICKNAME_3 = "지수";

    public static final String VALID_PROFILE_IMAGE_1 = "https://bootme-images.s3.ap-northeast-2.amazonaws.com/logos/stack/java.png";
    public static final String VALID_PROFILE_IMAGE_2 = "https://bootme-images.s3.ap-northeast-2.amazonaws.com/logos/stack/javascript.png";
    public static final String VALID_PROFILE_IMAGE_3 = "https://bootme-images.s3.ap-northeast-2.amazonaws.com/logos/stack/kotlin.png";

    public static final String VALID_CONTENT_1 = "Lorem ipsum dolor sit amet, consectetur adipiscing elit";
    public static final String VALID_CONTENT_2 = "Fusce et pulvinar velit, in porta ligula.";
    public static final String VALID_CONTENT_3 = "Ut imperdiet ligula quis orci gravida, at iaculis libero condimentum";

    public static final String VALID_VOTED_1 = "UPVOTE";
    public static final String VALID_VOTED_2 = "DOWNVOTE";
    public static final String VALID_VOTED_3 = "NONE";

    public static final String VALID_STATUS_1 = DISPLAY.toString();
    public static final String VALID_STATUS_2 = DELETED.toString();
    public static final String VALID_STATUS_3 = HIDDEN.toString();

    public static CommentRequest getCommentRequest(int index) {
        index--;
        String[] contents = {VALID_CONTENT_1, VALID_CONTENT_2, VALID_CONTENT_3};
        return new CommentRequest((long) index+1, contents[index]);
    }

    public static CommentResponse getCommentResponse(int index) {
        index--;
        String[] nickNames = {VALID_WRITER_NICKNAME_1, VALID_WRITER_NICKNAME_2, VALID_WRITER_NICKNAME_3};
        String[] profileImages = {VALID_PROFILE_IMAGE_1, VALID_PROFILE_IMAGE_2, VALID_PROFILE_IMAGE_3};
        String[] contents = {VALID_CONTENT_1, VALID_CONTENT_2, VALID_CONTENT_3};
        String[] votes = {VALID_VOTED_1, VALID_VOTED_2, VALID_VOTED_3};
        String[] statuses = {VALID_STATUS_1, VALID_STATUS_2, VALID_STATUS_3};


        return CommentResponse.builder()
                .id((long)index+1)
                .postId((long)index+1)
                .writerId((long)index+1)
                .writerNickname(nickNames[index])
                .writerProfileImage(profileImages[index])
                .parentId((long)index+1)
                .content(contents[index])
                .groupNum(index+1)
                .levelNum(index+1)
                .orderNum(index+1)
                .likes(index+10)
                .voted(votes[index])
                .status(statuses[index])
                .createdAt(1L)
                .modifiedAt(1L)
                .build();
    }

}
