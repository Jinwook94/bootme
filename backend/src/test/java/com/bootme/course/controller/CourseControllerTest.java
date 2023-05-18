package com.bootme.course.controller;

import com.bootme.auth.token.TokenProvider;
import com.bootme.course.dto.CourseResponse;
import com.bootme.course.service.CourseService;
import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.bootme.util.fixture.CourseFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CourseController.class)
@DisplayName("CourseController 클래스의")
class CourseControllerTest extends ControllerTest {

    @MockBean
    private CourseService courseService;

    @MockBean
    private TokenProvider tokenProvider;

    @Test
    @DisplayName("addCourse()는 정상 요청시 코스를 추가하고 상태코드 201을 반환한다.")
    void addCourse() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(getCourseRequest(1));
        given(courseService.addCourse(any())).willReturn(1L);
        given(courseService.findById(1L)).willReturn(getCourseResponse(1));

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
    @DisplayName("findCourse()는 정상 요청시 상태코드 200을 반환한다.")
    void findCourse() throws Exception {
        //given
        given(courseService.findById(any())).willReturn(getCourseResponse(1));

        //when
        ResultActions perform = mockMvc.perform(get("/courses/1")
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
    @DisplayName("findCourses()는 정상 요청시 상태코드 200을 반환한다.")
    void findCourses() throws Exception {
        //given
        List<CourseResponse> courseResponses = new ArrayList<>();
        courseResponses.add(getCourseResponse(1));
        courseResponses.add(getCourseResponse(2));
        courseResponses.add(getCourseResponse(3));
        given(courseService.findAll()).willReturn(courseResponses);

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
    void modifyCourse() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(getCourseRequest(1));
        willDoNothing().given(courseService).modifyCourse(any(), any());

        //when
        ResultActions perform = mockMvc.perform(put("/courses/1")
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
    @DisplayName("deleteCourse()는 정상 요청시 상태코드 204를 반환한다.")
    void deleteCourse() throws Exception {
        //given
        willDoNothing().given(courseService).deleteCourse(any());

        //when
        ResultActions perform = mockMvc.perform(delete("/courses/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("courses/delete/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
