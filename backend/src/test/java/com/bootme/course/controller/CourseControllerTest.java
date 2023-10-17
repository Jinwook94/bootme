package com.bootme.course.controller;

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
import java.util.List;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_COMPANY;
import static com.bootme.common.exception.ErrorType.NOT_FOUND_COURSE;
import static com.bootme.util.fixture.CourseFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
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
        given(courseService.findById(anyLong(), anyLong())).willReturn(getCourseDetailResponse(1));

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
                        preprocessResponse(prettyPrint())));
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
        given(courseService.findById(anyLong(), anyLong())).willReturn(getCourseDetailResponse(1));

        //when
        ResultActions perform = mockMvc.perform(get("/courses/{id}", 1)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("courses/find/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
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
        List<CourseResponse> courseResponses = new ArrayList<>();
        courseResponses.add(getCourseResponse(1));
        courseResponses.add(getCourseResponse(2));
        courseResponses.add(getCourseResponse(3));
        Page<CourseResponse> coursePage = new PageImpl<>(courseResponses);

        given(courseService.findAll(anyLong(), anyInt(), anyInt(), anyString(), any())).willReturn(coursePage);

        //when
        ResultActions perform = mockMvc.perform(get("/courses")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("courses/findAll/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));

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
                        preprocessResponse(prettyPrint())));
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
                        preprocessResponse(prettyPrint())));
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
                        preprocessResponse(prettyPrint())));
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
