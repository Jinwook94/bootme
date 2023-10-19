package com.bootme.course.controller;

import com.bootme.common.exception.ResourceNotFoundException;
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

import java.util.Arrays;
import java.util.List;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_COMPANY;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_COURSE;
import static com.bootme.util.fixture.CourseFixture.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
@DisplayName("CourseController 클래스의")
class CourseControllerTest extends ControllerTest {

    @Test
    @DisplayName("addCourse()는 정상 요청시 코스를 추가하고 상태코드 201을 반환한다.")
    void addCourse() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(getCourseRequest(1));
        given(courseService.addCourse(any())).willReturn(1L);
        given(courseService.findById(any(), any())).willReturn(getCourseDetailResponse(1));

        //when
        ResultActions perform = mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isCreated());

        //docs
        perform.andDo(print())
                .andDo(document("courses/add/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("코스 추가")
                                .description("코스 추가")
                                .requestFields(
                                        fieldWithPath("title").description("코스 이름 (연도, 기수 포함)"),
                                        fieldWithPath("name").description("코스 이름"),
                                        fieldWithPath("generation").description("코스 기수"),
                                        fieldWithPath("url").description("코스 URL"),
                                        fieldWithPath("companyName").description("코스 운영 회사 이"),
                                        fieldWithPath("location").description("위치"),
                                        fieldWithPath("superCategories[]").description("슈퍼 카테고리"),
                                        fieldWithPath("subCategories[]").description("서브 카테고리"),
                                        fieldWithPath("languages[]").description("프로그래밍 언어"),
                                        fieldWithPath("frameworks[]").description("프레임워크"),
                                        fieldWithPath("cost").description("코스 가격"),
                                        fieldWithPath("period").description("수강 기간"),
                                        fieldWithPath("dates.registrationStartDate").description("등록 시작 날짜"),
                                        fieldWithPath("dates.registrationEndDate").description("등록 종료 날짜"),
                                        fieldWithPath("dates.courseStartDate").description("코스 시작 날짜"),
                                        fieldWithPath("dates.courseEndDate").description("코스 종료 날짜"),
                                        fieldWithPath("recommended").description("추천 코스 여부"),
                                        fieldWithPath("free").description("무료 여부"),
                                        fieldWithPath("kdt").description("KDT 여부"),
                                        fieldWithPath("online").description("온라인 여부"),
                                        fieldWithPath("tested").description("코딩 테스트 여부"),
                                        fieldWithPath("prerequisiteRequired").description("사전 요구 사항 필요 여부")
                                )
                                .responseFields(
                                        fieldWithPath("id").description("코스 ID"),
                                        fieldWithPath("title").description("코스 이름 (연도, 기수 포함)"),
                                        fieldWithPath("name").description("코스 이름"),
                                        fieldWithPath("generation").description("코스 기수"),
                                        fieldWithPath("url").description("코스 URL"),
                                        fieldWithPath("location").description("위치"),
                                        fieldWithPath("superCategories[]").description("슈퍼 카테고리"),
                                        fieldWithPath("subCategories[]").description("서브 카테고리"),
                                        fieldWithPath("languages[]").description("프로그래밍 언어"),
                                        fieldWithPath("frameworks[]").description("프레임워크"),
                                        fieldWithPath("cost").description("코스 가격"),
                                        fieldWithPath("period").description("수강 기간"),
                                        fieldWithPath("dates.registrationStartDate").description("등록 시작 날짜"),
                                        fieldWithPath("dates.registrationEndDate").description("등록 종료 날짜"),
                                        fieldWithPath("dates.courseStartDate").description("코스 시작 날짜"),
                                        fieldWithPath("dates.courseEndDate").description("코스 종료 날짜"),
                                        fieldWithPath("detail").description("코스 상세 내용"),
                                        fieldWithPath("company.id").description("회사 ID"),
                                        fieldWithPath("company.name").description("회사 이름"),
                                        fieldWithPath("company.serviceName").description("회사 운영 서비스 이름"),
                                        fieldWithPath("company.url").description("회사 URL"),
                                        fieldWithPath("company.serviceUrl").description("회사 운영 서비스 URL"),
                                        fieldWithPath("company.logoUrl").description("회사 로고 URL"),
                                        fieldWithPath("company.courses[]").description("회사 운영 코스"),
                                        fieldWithPath("recommended").description("추천 코스 여부"),
                                        fieldWithPath("free").description("무료 여부"),
                                        fieldWithPath("kdt").description("KDT 여부"),
                                        fieldWithPath("online").description("온라인 여부"),
                                        fieldWithPath("tested").description("코딩 테스트 여부"),
                                        fieldWithPath("prerequisiteRequired").description("사전 요구 사항 필요 여부"),
                                        fieldWithPath("registerOpen").description("등록 접수중 여부"),
                                        fieldWithPath("clicks").description("클릭수"),
                                        fieldWithPath("bookmarks").description("북마크 수"),
                                        fieldWithPath("createdAt").description("생성 시간"),
                                        fieldWithPath("modifiedAt").description("수정 시간"),
                                        fieldWithPath("bookmarked").description("북마크 여부")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("addCourse()는 등록되지 않은 회사의 코스인 경우 400 Bad Request 를 반환한다.")
    void addCourse_NOT_FOUND_COMPANY() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(getCourseRequest(2));
        willThrow(new ResourceNotFoundException(NOT_FOUND_COMPANY, "2"))
                .given(courseService).addCourse(any());

        //when
        ResultActions perform = mockMvc.perform(post("/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(NOT_FOUND_COMPANY.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("courses/add/fail/not-found-company",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("findCourse()는 정상 요청시 상태코드 200을 반환한다.")
    void findCourse() throws Exception {
        //given
        given(courseService.findById(any(), any())).willReturn(getCourseDetailResponse(1));

        //when
        ResultActions perform = mockMvc.perform(get("/courses/{id}", 1)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("courses/find/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("코스 조회")
                                .description("코스 조회")
                                .pathParameters(
                                        parameterWithName("id").description("코스 Id")
                                )
                                .responseFields(
                                        fieldWithPath("id").description("코스 ID"),
                                        fieldWithPath("title").description("코스 이름 (연도, 기수 포함)"),
                                        fieldWithPath("name").description("코스 이름"),
                                        fieldWithPath("generation").description("코스 기수"),
                                        fieldWithPath("url").description("코스 URL"),
                                        fieldWithPath("location").description("위치"),
                                        fieldWithPath("superCategories[]").description("슈퍼 카테고리"),
                                        fieldWithPath("subCategories[]").description("서브 카테고리"),
                                        fieldWithPath("languages[]").description("프로그래밍 언어"),
                                        fieldWithPath("frameworks[]").description("프레임워크"),
                                        fieldWithPath("cost").description("코스 가격"),
                                        fieldWithPath("period").description("수강 기간"),
                                        fieldWithPath("dates.registrationStartDate").description("등록 시작 날짜"),
                                        fieldWithPath("dates.registrationEndDate").description("등록 종료 날짜"),
                                        fieldWithPath("dates.courseStartDate").description("코스 시작 날짜"),
                                        fieldWithPath("dates.courseEndDate").description("코스 종료 날짜"),
                                        fieldWithPath("detail").description("코스 상세 내용"),
                                        fieldWithPath("company.id").description("회사 ID"),
                                        fieldWithPath("company.name").description("회사 이름"),
                                        fieldWithPath("company.serviceName").description("회사 운영 서비스 이름"),
                                        fieldWithPath("company.url").description("회사 URL"),
                                        fieldWithPath("company.serviceUrl").description("회사 운영 서비스 URL"),
                                        fieldWithPath("company.logoUrl").description("회사 로고 URL"),
                                        fieldWithPath("company.courses[]").description("회사 운영 코스"),
                                        fieldWithPath("recommended").description("추천 코스 여부"),
                                        fieldWithPath("free").description("무료 여부"),
                                        fieldWithPath("kdt").description("KDT 여부"),
                                        fieldWithPath("online").description("온라인 여부"),
                                        fieldWithPath("tested").description("코딩 테스트 여부"),
                                        fieldWithPath("prerequisiteRequired").description("사전 요구 사항 필요 여부"),
                                        fieldWithPath("registerOpen").description("등록 접수중 여부"),
                                        fieldWithPath("clicks").description("클릭수"),
                                        fieldWithPath("bookmarks").description("북마크 수"),
                                        fieldWithPath("createdAt").description("생성 시간"),
                                        fieldWithPath("modifiedAt").description("수정 시간"),
                                        fieldWithPath("bookmarked").description("북마크 여부")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("findCourse()는 해당 id의 코스가 존재하지 않는 경우 400 Bad Request 를 반환한다.")
    void findCourse_NOT_FOUND_COURSE() throws Exception {
        //given
        willThrow(new ResourceNotFoundException(NOT_FOUND_COURSE, "2"))
                .given(courseService).findById(any(), any());

        //when
        ResultActions perform = mockMvc.perform(get("/courses/{id}", 2)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(NOT_FOUND_COURSE.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("courses/find/fail/not-found-course",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("findCourses()는 정상 요청시 상태코드 200을 반환한다.")
    void findCourses() throws Exception {
        //given
        List<CourseResponse> courseResponses = Arrays.asList(
                getCourseResponse(1),
                getCourseResponse(2),
                getCourseResponse(3)
        );
        Page<CourseResponse> coursePage = new PageImpl<>(courseResponses);

        given(courseService.findAll(any(), anyInt(), anyInt(), any(), any())).willReturn(coursePage);

        //when
        ResultActions perform = mockMvc.perform(get("/courses")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("courses/findAll/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("코스 전체 조회")
                                .description("코스 전체 조회")
                                .queryParameters(
                                        parameterWithName("page").description("요청할 페이지 번호 (1부터 시작)").optional(),
                                        parameterWithName("size").description("한 페이지에 표시할 항목 수").optional(),
                                        parameterWithName("sort").description("정렬 기준").optional(),
                                        parameterWithName("superCategories").description("슈퍼 카테고리").optional(),
                                        parameterWithName("subCategories").description("서브 카테고리").optional(),
                                        parameterWithName("languages").description("프로그래밍 언어").optional(),
                                        parameterWithName("frameworks").description("프레임워크").optional(),
                                        parameterWithName("costInput").description("수강 비용 필터 입력값").optional(),
                                        parameterWithName("periodInput").description("수강 기간 필터 입력값").optional(),
                                        parameterWithName("isFree").description("무료 여부").optional(),
                                        parameterWithName("isKdt").description("KDT 여부").optional(),
                                        parameterWithName("isTested").description("코딩 테스트 여부").optional()
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
                                .build())
                ));

    }

    @Test
    @DisplayName("modifyCourse()는 정상 요청시 상태코드 204를 반환한다.")
    void modifyCourse() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(getCourseRequest(1));
        willDoNothing().given(courseService).modifyCourse(any(), any());

        //when
        ResultActions perform = mockMvc.perform(put("/courses/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("courses/modify/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("코스 수정")
                                .description("코스 수정")
                                .pathParameters(
                                        parameterWithName("id").description("memberId")
                                )
                                .requestFields(
                                        fieldWithPath("title").description("코스 이름 (연도, 기수 포함)"),
                                        fieldWithPath("name").description("코스 이름"),
                                        fieldWithPath("generation").description("코스 기수"),
                                        fieldWithPath("url").description("코스 URL"),
                                        fieldWithPath("companyName").description("코스 운영 회사 이"),
                                        fieldWithPath("location").description("위치"),
                                        fieldWithPath("superCategories[]").description("슈퍼 카테고리"),
                                        fieldWithPath("subCategories[]").description("서브 카테고리"),
                                        fieldWithPath("languages[]").description("프로그래밍 언어"),
                                        fieldWithPath("frameworks[]").description("프레임워크"),
                                        fieldWithPath("cost").description("코스 가격"),
                                        fieldWithPath("period").description("수강 기간"),
                                        fieldWithPath("dates.registrationStartDate").description("등록 시작 날짜"),
                                        fieldWithPath("dates.registrationEndDate").description("등록 종료 날짜"),
                                        fieldWithPath("dates.courseStartDate").description("코스 시작 날짜"),
                                        fieldWithPath("dates.courseEndDate").description("코스 종료 날짜"),
                                        fieldWithPath("recommended").description("추천 코스 여부"),
                                        fieldWithPath("free").description("무료 여부"),
                                        fieldWithPath("kdt").description("KDT 여부"),
                                        fieldWithPath("online").description("온라인 여부"),
                                        fieldWithPath("tested").description("코딩 테스트 여부"),
                                        fieldWithPath("prerequisiteRequired").description("사전 요구 사항 필요 여부")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("modifyCourse()는 해당 id의 코스가 존재하지 않는 경우 400 Bad Request 를 반환한다.")
    void modifyCourse_NOT_FOUND_COURSE() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(getCourseRequest(2));
        willThrow(new ResourceNotFoundException(NOT_FOUND_COURSE, "2"))
                .given(courseService).modifyCourse(any(), any());

        //when
        ResultActions perform = mockMvc.perform(put("/courses/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(NOT_FOUND_COURSE.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("courses/modify/fail/not-found-course",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("modifyCourseDetail()는 정상 요청시 상태코드 204를 반환한다.")
    void modifyCourseDetail() throws Exception {
        //given
        willDoNothing().given(courseService).modifyCourseDetail(anyLong(), any());
        String content = objectMapper.writeValueAsString(getCourseDetailRequest(1));

        //when
        ResultActions perform = mockMvc.perform(patch("/courses/{id}/detail", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("courses/modify-detail/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("코스 상세 페이지 수정")
                                .description("코스 상세 페이지 수정")
                                .pathParameters(
                                        parameterWithName("id").description("코스 ID")
                                )
                                .requestFields(
                                        fieldWithPath("detail").description("코스 상세 페이지 내용")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("modifyCourseDetail()는 해당 id의 코스가 존재하지 않는 경우 400 Bad Request 를 반환한다.")
    void modifyCourseDetail_NOT_FOUND_COURSE() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(getCourseDetailRequest(2));
        willThrow(new ResourceNotFoundException(NOT_FOUND_COURSE, "2"))
                .given(courseService).modifyCourseDetail(anyLong(), any());

        //when
        ResultActions perform = mockMvc.perform(patch("/courses/{id}/detail", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(NOT_FOUND_COURSE.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("courses/modify-detail/fail/not-found-course",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("deleteCourse()는 정상 요청시 상태코드 204를 반환한다.")
    void deleteCourse() throws Exception {
        //given
        willDoNothing().given(courseService).deleteCourse(any());

        //when
        ResultActions perform = mockMvc.perform(delete("/courses/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("courses/delete/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("코스 삭제")
                                .description("코스 삭제")
                                .pathParameters(
                                        parameterWithName("id").description("코스 ID")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("deleteCourse()는 해당 id의 코스가 존재하지 않는 경우 400 Bad Request 를 반환한다.")
    void deleteCourse_NOT_FOUND_COURSE() throws Exception {
        //given
        willThrow(new ResourceNotFoundException(NOT_FOUND_COURSE, "2"))
                .given(courseService).deleteCourse(any());

        //when
        ResultActions perform = mockMvc.perform(delete("/courses/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(NOT_FOUND_COURSE.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("courses/delete/fail/not-found-course",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
