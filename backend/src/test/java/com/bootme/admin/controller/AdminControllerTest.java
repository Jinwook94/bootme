package com.bootme.admin.controller;

import com.bootme.admin.dto.AdminLoginRequest;
import com.bootme.admin.service.AdminService;
import com.bootme.auth.dto.TokenResponse;
import com.bootme.course.dto.CompanyResponse;
import com.bootme.course.dto.CourseResponse;
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
        String content = objectMapper.writeValueAsString(getCourseRequest(1));
        given(adminService.addCourse(any())).willReturn(1L);
        given(adminService.findCourseById(1L)).willReturn(getCourseResponse(1));

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
        given(adminService.findCourseById(any())).willReturn(getCourseResponse(1));

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
        List<CourseResponse> courseResponses = new ArrayList<>();
        courseResponses.add(getCourseResponse(1));
        courseResponses.add(getCourseResponse(2));
        courseResponses.add(getCourseResponse(3));
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
        String content = objectMapper.writeValueAsString(getCourseRequest(1));
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

    @Test
    @DisplayName("addCompany()는 정상 요청시 회사를 추가하고 상태코드 201을 반환한다.")
    void addCompany() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(getCompanyRequest(1));
        given(adminService.addCompany(any())).willReturn(1L);
        given(adminService.findCompanyById(1L)).willReturn(getCompanyResponse(1));

        //when
        ResultActions perform = mockMvc.perform(post("/admin/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isCreated());

        //docs
        perform.andDo(print())
                .andDo(document(BASE_SNIPPET_PATH + "companies/add/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("findCompany()는 정상 요청시 상태코드 200을 반환한다.")
    void findCompany() throws Exception {
        //given
        given(adminService.findCompanyById(any())).willReturn(getCompanyResponse(1));

        //when
        ResultActions perform = mockMvc.perform(get("/admin/companies/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document(BASE_SNIPPET_PATH + "companies/find/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("findCompanies()는 정상 요청시 상태코드 200을 반환한다.")
    public void findCompanies() throws Exception {
        //given
        List<CompanyResponse> companyResponses = new ArrayList<>();
        companyResponses.add(getCompanyResponse(1));
        companyResponses.add(getCompanyResponse(2));
        given(adminService.findAllCompanies()).willReturn(companyResponses);

        //when
        ResultActions perform = mockMvc.perform(get("/admin/companies")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document(BASE_SNIPPET_PATH + "companies/findAll/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("modifyCompany()는 정상 요청시 상태코드 204를 반환한다.")
    public void modifyCompany() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(getCompanyRequest(1));
        willDoNothing().given(adminService).modifyCompany(any(), any());

        //when
        ResultActions perform = mockMvc.perform(put("/admin/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document(BASE_SNIPPET_PATH + "companies/modify/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("deleteCompany()는 정상 요청시 상태코드 204를 반환한다.")
    public void deleteCompany() throws Exception {
        //given
        willDoNothing().given(adminService).deleteCompany(any());

        //when
        ResultActions perform = mockMvc.perform(delete("/admin/companies/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document(BASE_SNIPPET_PATH + "companies/delete/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
