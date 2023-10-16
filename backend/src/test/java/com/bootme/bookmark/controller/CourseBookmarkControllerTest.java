package com.bootme.bookmark.controller;

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

import static com.bootme.util.fixture.CourseFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
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
        ResultActions perform = mockMvc.perform(post("/bookmarks/1/courses/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isCreated());

        //docs
        perform.andDo(print())
                .andDo(document("courseBookmarks/add/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("deleteCourseBookmark()는 정상 요청시 상태코드 204를 반환한다.")
    void deleteCourseBookmark() throws Exception {
        //given
        willDoNothing().given(courseBookmarkService).deleteCourseBookmark(any(), any());

        //when
        ResultActions perform = mockMvc.perform(delete("/bookmarks/1/courses/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("courseBookmarks/delete/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("getBookmarkedCourses()는 정상 요청시 상태코드 200을 반환한다.")
    void getBookmarkedCourses() throws Exception {
        //given
        List<CourseResponse> courseResponses = new ArrayList<>();
        courseResponses.add(getCourseResponse(1));
        courseResponses.add(getCourseResponse(2));
        Page<CourseResponse> coursePage = new PageImpl<>(courseResponses);

        given(courseBookmarkService.getBookmarkedCourses(anyLong(), any())).willReturn(coursePage);

        //when
        ResultActions perform = mockMvc.perform(get("/bookmarks/1/courses")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("courseBookmarks/findAll/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
