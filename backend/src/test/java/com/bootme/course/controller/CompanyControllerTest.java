package com.bootme.course.controller;

import com.bootme.course.dto.CompanyResponse;
import com.bootme.course.service.CompanyService;
import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.bootme.util.fixture.CourseFixture.getCompanyRequest;
import static com.bootme.util.fixture.CourseFixture.getCompanyResponse;
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

@WebMvcTest(CompanyController.class)
@DisplayName("CompanyController 클래스의")
class CompanyControllerTest extends ControllerTest {

    @MockBean
    private CompanyService companyService;

    @Test
    @DisplayName("addCompany()는 정상 요청시 회사를 추가하고 상태코드 201을 반환한다.")
    void addCompany() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(getCompanyRequest(1));
        given(companyService.addCompany(any())).willReturn(1L);
        given(companyService.findById(1L)).willReturn(getCompanyResponse(1));

        //when
        ResultActions perform = mockMvc.perform(post("/companies")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isCreated());

        //docs
        perform.andDo(print())
                .andDo(document("companies/add/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("findCompany()는 정상 요청시 상태코드 200을 반환한다.")
    void findCompany() throws Exception {
        //given
        given(companyService.findById(any())).willReturn(getCompanyResponse(1));

        //when
        ResultActions perform = mockMvc.perform(get("/companies/1")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("companies/find/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("findCompanies()는 정상 요청시 상태코드 200을 반환한다.")
    void findCompanies() throws Exception {
        //given
        List<CompanyResponse> companyResponses = new ArrayList<>();
        companyResponses.add(getCompanyResponse(1));
        companyResponses.add(getCompanyResponse(2));
        given(companyService.findAll()).willReturn(companyResponses);

        //when
        ResultActions perform = mockMvc.perform(get("/companies")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("companies/findAll/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("modifyCompany()는 정상 요청시 상태코드 204를 반환한다.")
    void modifyCompany() throws Exception{
        //given
        String content = objectMapper.writeValueAsString(getCompanyRequest(1));
        willDoNothing().given(companyService).modifyCompany(any(), any());

        //when
        ResultActions perform = mockMvc.perform(put("/companies/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("companies/modify/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("deleteCompany()는 정상 요청시 상태코드 204를 반환한다.")
    void deleteCompany() throws Exception {
        //given
        willDoNothing().given(companyService).deleteCompany(any());

        //when
        ResultActions perform = mockMvc.perform(delete("/companies/1")
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("companies/delete/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }
}