package com.bootme.common.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ErrorType {

    // Authentication errors
    NOT_AUTHENTICATED       (1001, "인증에 실패했습니다."),
    ALREADY_AUTHENTICATED   (1002, "이미 인증정보가 존재합니다."),
    NOT_LOGGED_IN           (1003, "로그인 후 이용하실 수 있습니다."),
    TOKEN_PARSING_FAIL      (1004, "토큰 파싱에 실패했습니다."),
    INVALID_ISSUER          (1005, "유효하지 않은 토큰 발급자입니다."),
    INVALID_AUDIENCE        (1006, "토큰의 Audience 값이 유효하지 않습니다."),
    INVALID_ISSUED_AT       (1007, "토큰의 발행시간이 올바르지 않습니다."),
    TOKEN_EXPIRED           (1008, "토큰이 만료되었습니다."),
    INVALID_SIGNATURE       (1009, "토큰의 서명이 올바르지 않습니다."),
    GOOGLE_LOGIN_FAIL       (1010, "구글 로그인에 실패했습니다."),
    NAVER_LOGIN_FAIL        (1011, "네이버 로그인에 실패했습니다."),
    NOT_WRITER              (1012, "게시글 작성자가 아닙니다."),
    INVALID_EMAIL_NULL      (1013, "이메일은 필수 입력값입니다."),
    MEMBER_ID_MISMATCH      (1014, "엑세스 토큰의 id와 요청 경로의 id가 다릅니다."),

    // Authorization
    FORBIDDEN_REQUEST       (2001, "권한이 없습니다."),

    // Resource not found errors
    NOT_FOUND_COURSE        (3001, "존재하지 않는 코스입니다."),
    NOT_FOUND_COMPANY       (3002, "존재하지 않는 회사입니다."),
    NOT_FOUND_MEMBER        (3003, "존재하지 않는 회원입니다."),
    NOT_FOUND_BOOKMARK      (3004, "해당 북마크 코스가 존재하지 않습니다."),
    NOT_FOUND_EVENT         (3005, "존재하지 않는 알림 이벤트입니다."),
    NOT_FOUND_STACK         (3006, "존재하지 않는 기술 스택입니다."),
    NOT_FOUND_POST          (3007, "존재하지 않는 게시글입니다."),
    NOT_FOUND_COMMENT       (3008, "존재하지 않는 댓글입니다."),
    NOT_FOUND_VOTE          (3009, "존재하지 않는 투표입니다."),

    // Resource already exists errors
    ALREADY_SAVED_STACK     (4001, "이미 저장된 기술 스택입니다."),
    ALREADY_SAVED_COMPANY   (4002, "이미 저장된 회사입니다."),
    ALREADY_SAVED_COURSE    (4003, "이미 저장된 코스입니다."),
    ALREADY_BOOKMARKED      (4004, "이미 북마크 저장된 코스입니다."),
    ALREADY_VOTED           (4005, "이미 투표했습니다."),

    // Invalid input errors
    INVALID_METHOD_ARGUMENT (5001, ""), // 인자에 따라 다른 에러 메시지 출력되도록 설정함
    INVALID_EVENT           (5002, "유효하지 않은 webhook 이벤트입니다."),
    INVALID_SEARCH_QUERY    (5003, "검색 입력값이 유효하지 않습니다."),
    FILE_CONVERSION_FAIL    (5004, "파일 변환에 실패했습니다."),
    FILE_DELETE_FAIL        (5005, "임시 저장된 이미지 파일의 삭제에 실패했습니다."),
    POST_TITLE_EMPTY        (5006, "게시글 제목이 입력되지 않았습니다."),
    POST_TITLE_MAX_LENGTH   (5007, "게시글 제목은 최대 100자까지 작성 가능합니다."),
    POST_CONTENT_MAX_LENGTH (5008, "게시글은 최대 50,000자까지 작성 가능합니다."),
    POST_COMMENT_MAX_LENGTH (5009, "댓글은 최대 2,000자까지 작성 가능합니다."),
    COMMENT_EMPTY           (5010, "빈 댓글을 작성할 수 없습니다."),
    COMMENT_MAX_LENGTH      (5011, "댓글은 최대 300자까지 작성 가능합니다."),
    INVALID_IMAGE_TYPE      (5012, "유효하지 않은 이미지 아이템 타입입니다."),
    INVALID_VOTE_TYPE       (5013, "유효하지 않은 voteType 입니다. 유효한 값: upvote || downvote"),
    INVALID_VOTABLE_TYPE    (5014, "유효하지 않은 votableType 입니다."),

    // System errors
    JSON_PROCESSING_FAIL    (6001, "JSON 처리에 실패했습니다."),
    SSE_CONNECT_FAIL        (6002, "SSE 연결에 실패했습니다."),

    // External Service errors
    S3_UPLOAD_FAIL          (7001, "이미지 파일을 S3 업로드 실패했습니다."),
    OPEN_AI_API_FAIL         (7002, "OpenAI API 에서 문제가 발생 했습니다."),

    // Internal server errors
    RUNTIME_EXCEPTION       (9001, "서버에 알 수 없는 문제가 발생했습니다.");

    final int errorCode;
    final String message;

    public String getMessage(String invalidInput) {
        return String.format(getMessage() +" : " + invalidInput);
    }

}
