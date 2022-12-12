package com.bootme.admin.controller;

import com.bootme.admin.dto.AdminLoginRequest;
import com.bootme.admin.service.AdminService;
import com.bootme.auth.dto.TokenResponse;
import com.bootme.course.domain.Dates;
import com.bootme.course.dto.CourseRequest;
import com.bootme.course.dto.CourseResponse;
import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.bootme.util.fixture.CourseFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;

@WebMvcTest(AdminController.class)
@DisplayName("AdminController 클래스의")
class AdminControllerTest extends ControllerTest {

    @MockBean
    private AdminService adminService;

    private static final String BASE_SNIPPET_PATH = "admin/";

    CourseRequest courseRequest = CourseRequest.builder()
            .title(VALID_TITLE_1)
            .url(VALID_URL_1)
            .companyName(VALID_COMPANY_1.getName())
            .location(VALID_LOCATION_1)
            .cost(VALID_COST_1)
            .costType(VALID_CostType_1.name())
            .dates(Dates.builder()
                    .registrationStartDate(LocalDate.of(2022, 12, 5))
                    .registrationEndDate(LocalDate.of(2023, 1, 10))
                    .courseStartDate(LocalDate.of(2023, 1, 20))
                    .courseEndDate(LocalDate.of(2023, 7, 31))
                    .build())
            .onOffline(VALID_ONOFFLINE_1.name())
            .tags(VALID_TAGS_1)
            .prerequisites(VALID_PREREQUISITES_1.name())
            .recommended(VALID_ISRECOMMENDED_1)
            .tested(VALID_ISTESTED_1)
            .build();
    CourseResponse courseResponse = CourseResponse.builder()
            .id(1L)
            .title(VALID_TITLE_1)
            .url(VALID_URL_1)
            .companyName(VALID_COMPANY_1.getName())
            .location(VALID_LOCATION_1)
            .cost(VALID_COST_1)
            .costType(VALID_CostType_1.name())
            .dates(Dates.builder()
                    .registrationStartDate(LocalDate.of(2022, 12, 5))
                    .registrationEndDate(LocalDate.of(2023, 1, 10))
                    .courseStartDate(LocalDate.of(2023, 1, 20))
                    .courseEndDate(LocalDate.of(2023, 7, 31))
                    .build())
            .onOffline(VALID_ONOFFLINE_1.name())
            .tags(VALID_TAGS_1)
            .prerequisites(VALID_PREREQUISITES_1.name())
            .recommended(VALID_ISRECOMMENDED_1)
            .tested(VALID_ISTESTED_1)
            .build();

    @Test
    @DisplayName("login()은 정상 요청시 HTTP Status Code 200을 반환한다")
    void login() throws Exception {
        //given
        TokenResponse tokenResponse = new TokenResponse("tokenValue");
        String content = objectMapper.writeValueAsString(new AdminLoginRequest("id", "password"));
        given(adminService.login(any())).willReturn(tokenResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/admin/login")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document(BASE_SNIPPET_PATH + "login/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("addCourse()는 정상 요청시 코스를 추가하고 상태코드 201을 반환한다.")
    void addCourse() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(courseRequest);
        given(adminService.addCourse(any())).willReturn(1L);
        given(adminService.findCourseById(1L)).willReturn(courseResponse);

        //when
        ResultActions perform = mockMvc.perform(post("/admin/courses")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isCreated());

        //docs
        perform.andDo(print())
                .andDo(document(BASE_SNIPPET_PATH + "courses/add/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("findCourse()는 정상 요청시 상태코드 200을 반환한다.")
    void findCourse() throws Exception {
        //given
        given(adminService.findCourseById(any())).willReturn(courseResponse);

        //when
        ResultActions perform = mockMvc.perform(get("/admin/courses/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document(BASE_SNIPPET_PATH + "courses/find/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("findCourses()는 정상 요청시 상태코드 200을 반환한다.")
    public void findCourses() throws Exception {
        //given
        CourseResponse courseResponse2 = CourseResponse.builder()
                .id(1L)
                .title(VALID_TITLE_1)
                .url(VALID_URL_1)
                .companyName(VALID_COMPANY_1.getName())
                .location(VALID_LOCATION_1)
                .cost(VALID_COST_1)
                .costType(VALID_CostType_1.name())
                .dates(Dates.builder()
                        .registrationStartDate(LocalDate.of(2022, 12, 5))
                        .registrationEndDate(LocalDate.of(2023, 1, 10))
                        .courseStartDate(LocalDate.of(2023, 1, 20))
                        .courseEndDate(LocalDate.of(2023, 7, 31))
                        .build())
                .onOffline(VALID_ONOFFLINE_1.name())
                .tags(VALID_TAGS_1)
                .prerequisites(VALID_PREREQUISITES_1.name())
                .recommended(VALID_ISRECOMMENDED_1)
                .tested(VALID_ISTESTED_1)
                .build();

        List<CourseResponse> courseResponses = new ArrayList<CourseResponse>();
        courseResponses.add(courseResponse);
        courseResponses.add(courseResponse2);
        given(adminService.findAllCourses()).willReturn(courseResponses);

        //when
        ResultActions perform = mockMvc.perform(get("/admin/courses")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document(BASE_SNIPPET_PATH + "courses/findAll/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));

    }

    @Test
    @DisplayName("modifyCourse()는 정상 요청시 상태코드 204를 반환한다.")
    public void modifyCourse() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(courseRequest);
        willDoNothing().given(adminService).modifyCourse(any(), any());

        //when
        ResultActions perform = mockMvc.perform(put("/admin/courses/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document(BASE_SNIPPET_PATH + "courses/modify/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("deleteCourse()는 정상 요청시 상태코드 204를 반환한다.")
    public void deleteCourse() throws Exception {
        //given
        willDoNothing().given(adminService).deleteCourse(any());

        //when
        ResultActions perform = mockMvc.perform(delete("/admin/courses/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document(BASE_SNIPPET_PATH + "courses/delete/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}
