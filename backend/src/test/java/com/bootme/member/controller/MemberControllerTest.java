package com.bootme.member.controller;

import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.dto.CourseResponse;
import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.bootme.common.exception.ErrorType.*;
import static com.bootme.util.fixture.CourseFixture.getCourseResponse;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@DisplayName("MemberController 클래스의")
class MemberControllerTest extends ControllerTest {

    @Test
    @DisplayName("addBookmarkCourse()는 정상 요청시 상태코드 201을 반환한다.")
    void addBookmarkCourse() throws Exception {
        //given
        given(memberService.addBookmarkCourse(anyLong(), anyLong())).willReturn(1L);

        //when
        ResultActions perform = mockMvc.perform(post("/member/1/bookmarks/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isCreated());

        //docs
        perform.andDo(print())
                .andDo(document("member/addBookmarkCourse/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("addBookmarkCourse()는 해당 id의 멤버가 존재하지 않는 경우 400 Bad Request 를 반환한다.")
    void addBookmarkCourse_NOT_FOUND_MEMBER() throws Exception {
        //given
        willThrow(new ResourceNotFoundException(NOT_FOUND_MEMBER, "2"))
                .given(memberService).addBookmarkCourse(any(), any());

        //when
        ResultActions perform = mockMvc.perform(post("/member/2/bookmarks/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(NOT_FOUND_MEMBER.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("member/addBookmarkCourse/fail/not-found-member",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("addBookmarkCourse()는 해당 id의 멤버가 존재하지 않는 경우 400 Bad Request 를 반환한다.")
    void addBookmarkCourse_NOT_FOUND_COURSE() throws Exception {
        //given
        willThrow(new ResourceNotFoundException(NOT_FOUND_COURSE, "2"))
                .given(memberService).addBookmarkCourse(any(), any());

        //when
        ResultActions perform = mockMvc.perform(post("/member/1/bookmarks/2")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(NOT_FOUND_COURSE.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("member/addBookmarkCourse/fail/not-found-course",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("findBookmarkCourses()는 정상 요청시 상태코드 200을 반환한다.")
    void findBookmarkCourses() throws Exception {
        //given
        List<CourseResponse> courseResponses = new ArrayList<>();
        courseResponses.add(getCourseResponse(1));
        courseResponses.add(getCourseResponse(2));
        courseResponses.add(getCourseResponse(3));
        Page<CourseResponse> coursePage = new PageImpl<>(courseResponses);
        given(memberService.findBookmarkCourses(anyLong(), anyInt(), anyInt())).willReturn(coursePage);

        //when
        ResultActions perform = mockMvc.perform(get("/member/1/bookmarks")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("member/findBookmarkCourses/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }


    @Test
    @DisplayName("findBookmarkCourseIds()는 정상 요청시 상태코드 200을 반환한다.")
    void findBookmarkCourseIds() throws Exception {
        //given
        List<Long> bookmarkCourses = Arrays.asList(1L, 2L, 3L);
        given(memberService.findBookmarkCourseIds(anyLong())).willReturn(bookmarkCourses);

        //when
        ResultActions perform = mockMvc.perform(get("/member/1/bookmarks/courseIds")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("member/findBookmarkCourseIds/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("deleteBookmarkCourse()는 정상 요청시 상태코드 204를 반환한다.")
    void deleteBookmarkCourse() throws Exception {
        //given
        willDoNothing().given(memberService).deleteBookmarkCourse(anyLong(), anyLong());

        //when
        ResultActions perform = mockMvc.perform(delete("/member/1/bookmarks/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("member/deleteBookmarkCourse/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("deleteBookmarkCourse()는 해당 id의 북마크가 존재하지 않는 경우 400 Bad Request 를 반환한다.")
    void deleteBookmarkCourse_NOT_FOUND_BOOKMARK() throws Exception {
        //given
        willThrow(new ResourceNotFoundException(NOT_FOUND_BOOKMARK, "memberId=1, courseId=2"))
                .given(memberService).deleteBookmarkCourse(1L, 2L);

        //when
        ResultActions perform = mockMvc.perform(delete("/member/1/bookmarks/2")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(NOT_FOUND_BOOKMARK.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("member/deleteBookmarkCourse/fail/not-found-bookmark",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}