package com.bootme.bookmark.controller;

import com.bootme.course.dto.CourseResponse;
import com.bootme.util.ControllerTest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.bootme.util.fixture.CourseFixture.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseBookmarkController.class)
@DisplayName("CourseBookmarkController 클래스의")
class CourseBookmarkControllerTest extends ControllerTest {

    @Test
    @DisplayName("addCourseBookmark()는 정상 요청시 북마크를 추가하고 상태코드 201을 반환한다.")
    void addCourseBookmark() throws Exception {
        //given
        given(courseBookmarkService.addCourseBookmark(any(), any())).willReturn(1L);

        //when
        ResultActions perform = mockMvc.perform(post("/bookmarks/{memberId}/courses/{courseId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isCreated());

        //docs
        perform.andDo(print())
                .andDo(document("courseBookmarks/add/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("북마크 코스 추가")
                                .description("북마크 코스 추가")
                                .pathParameters(
                                        parameterWithName("memberId").description("memberId"),
                                        parameterWithName("courseId").description("북마크 추가할 코스 ID")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("deleteCourseBookmark()는 정상 요청시 상태코드 204를 반환한다.")
    void deleteCourseBookmark() throws Exception {
        //given
        willDoNothing().given(courseBookmarkService).deleteCourseBookmark(any(), any());

        //when
        ResultActions perform = mockMvc.perform(delete("/bookmarks/{memberId}/courses/{courseId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("courseBookmarks/delete/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("북마크 코스 삭제")
                                .description("북마크 코스 삭제")
                                .pathParameters(
                                        parameterWithName("memberId").description("memberId"),
                                        parameterWithName("courseId").description("북마크 삭제할 코스 ID")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("getBookmarkedCourses()는 정상 요청시 상태코드 200을 반환한다.")
    void getBookmarkedCourses() throws Exception {
        //given
        List<CourseResponse> courseResponses = new ArrayList<>();
        courseResponses.add(getCourseResponse(1));
        courseResponses.add(getCourseResponse(2));
        Page<CourseResponse> coursePage = new PageImpl<>(courseResponses);

        given(courseBookmarkService.getBookmarkedCourses(any(), any())).willReturn(coursePage);

        //when
        ResultActions perform = mockMvc.perform(get("/bookmarks/{memberId}/courses", 1)
                .param("page", "1")
                .param("size", "10")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("courseBookmarks/findAll/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("북마크한 코스 전체 조회")
                                .description("해당 회원이 북마크한 코스 전체를 반환한다.")
                                .pathParameters(
                                        parameterWithName("memberId").description("memberId")
                                )
                                .queryParameters(
                                        parameterWithName("page").description("요청할 페이지 번호 (1부터 시작)"),
                                        parameterWithName("size").description("한 페이지에 표시할 항목 수")
                                )
                                .responseFields(
                                        fieldWithPath("content[].id").description("코스 ID"),
                                        fieldWithPath("content[].title").description("코스 이름 (연도, 기수 포함)"),
                                        fieldWithPath("content[].name").description("코스 이름"),
                                        fieldWithPath("content[].generation").description("코스 기수"),
                                        fieldWithPath("content[].url").description("코스 URL"),
                                        fieldWithPath("content[].location").description("위치"),
                                        fieldWithPath("content[].superCategories[]").description("슈퍼 카테고리"),
                                        fieldWithPath("content[].subCategories[]").description("서브 카테고리"),
                                        fieldWithPath("content[].languages[]").description("프로그래밍 언어"),
                                        fieldWithPath("content[].frameworks[]").description("프레임워크"),
                                        fieldWithPath("content[].cost").description("코스 가격"),
                                        fieldWithPath("content[].period").description("수강 기간"),
                                        fieldWithPath("content[].dates.registrationStartDate").description("등록 시작 날짜"),
                                        fieldWithPath("content[].dates.registrationEndDate").description("등록 종료 날짜"),
                                        fieldWithPath("content[].dates.courseStartDate").description("코스 시작 날짜"),
                                        fieldWithPath("content[].dates.courseEndDate").description("코스 종료 날짜"),
                                        fieldWithPath("content[].company.id").description("회사 ID"),
                                        fieldWithPath("content[].company.name").description("회사 이름"),
                                        fieldWithPath("content[].company.serviceName").description("회사 운영 서비스 이름"),
                                        fieldWithPath("content[].company.url").description("회사 URL"),
                                        fieldWithPath("content[].company.serviceUrl").description("회사 운영 서비스 URL"),
                                        fieldWithPath("content[].company.logoUrl").description("회사 로고 URL"),
                                        fieldWithPath("content[].company.courses[]").description("회사 운영 코스"),
                                        fieldWithPath("content[].clicks").description("클릭수"),
                                        fieldWithPath("content[].bookmarks").description("북마크 수"),
                                        fieldWithPath("content[].createdAt").description("생성 시간"),
                                        fieldWithPath("content[].modifiedAt").description("수정 시간"),
                                        fieldWithPath("content[].bookmarked").description("해당 회원의 북마크 여부"),
                                        fieldWithPath("content[].recommended").description("추천 코스 여부"),
                                        fieldWithPath("content[].free").description("무료 여부"),
                                        fieldWithPath("content[].kdt").description("KDT 여부"),
                                        fieldWithPath("content[].online").description("온라인 여부"),
                                        fieldWithPath("content[].tested").description("코딩 테스트 여부"),
                                        fieldWithPath("content[].prerequisiteRequired").description("사전 요구 사항 필요 여부"),
                                        fieldWithPath("content[].registerOpen").description("등록 접수중 여부"),
                                        fieldWithPath("pageable").description("pageable"),
                                        fieldWithPath("last").description("마지막 페이지"),
                                        fieldWithPath("totalPages").description("총 페이지 수"),
                                        fieldWithPath("totalElements").description("총 아이템 수"),
                                        fieldWithPath("size").description("크기"),
                                        fieldWithPath("number").description("번호"),
                                        fieldWithPath("sort.empty").description("정렬 비어 있음"),
                                        fieldWithPath("sort.sorted").description("정렬 됨"),
                                        fieldWithPath("sort.unsorted").description("미정렬"),
                                        fieldWithPath("first").description("첫 페이지"),
                                        fieldWithPath("numberOfElements").description("아이템 수"),
                                        fieldWithPath("empty").description("비어 있음")
                                )
                                .build()
                        )
                ));
    }

}
