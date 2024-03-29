package com.bootme.course.controller;

import com.bootme.common.exception.ResourceNotFoundException;
import com.bootme.course.dto.CompanyResponse;
import com.bootme.util.ControllerTest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.bootme.common.exception.ErrorType.NOT_FOUND_COMPANY;
import static com.bootme.util.fixture.CourseFixture.getCompanyRequest;
import static com.bootme.util.fixture.CourseFixture.getCompanyResponse;
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

@WebMvcTest(CompanyController.class)
@DisplayName("CompanyController 클래스의")
class CompanyControllerTest extends ControllerTest {

    @Test
    @DisplayName("addCompany()는 정상 요청시 회사를 추가하고 상태코드 201을 반환한다.")
    void addCompany_success() throws Exception {
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
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("회사 추가")
                                .description("회사 추가")
                                .requestFields(
                                        fieldWithPath("name").description("회사 이름"),
                                        fieldWithPath("serviceName").description("회사 운영 서비스 이름"),
                                        fieldWithPath("url").description("회사 URL"),
                                        fieldWithPath("serviceUrl").description("회사 운영 서비스 URL"),
                                        fieldWithPath("logoUrl").description("회사 로고 URL")
                                )
                                .responseFields(
                                        fieldWithPath("id").description("회사 ID"),
                                        fieldWithPath("name").description("회사 이름"),
                                        fieldWithPath("serviceName").description("회사 운영 서비스 이름"),
                                        fieldWithPath("url").description("회사 URL"),
                                        fieldWithPath("serviceUrl").description("회사 운영 서비스 URL"),
                                        fieldWithPath("logoUrl").description("회사 로고 URL"),
                                        fieldWithPath("courses").description("회사 운영 코스")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("findCompany()는 정상 요청시 상태코드 200을 반환한다.")
    void findCompany() throws Exception {
        //given
        given(companyService.findById(any())).willReturn(getCompanyResponse(1));

        //when
        ResultActions perform = mockMvc.perform(get("/companies/{id}", 1)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("companies/find/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("회사 조회")
                                .description("회사 조회")
                                .pathParameters(
                                        parameterWithName("id").description("회사 ID")
                                )
                                .responseFields(
                                        fieldWithPath("id").description("회사 ID"),
                                        fieldWithPath("name").description("회사 이름"),
                                        fieldWithPath("serviceName").description("회사 운영 서비스 이름"),
                                        fieldWithPath("url").description("회사 URL"),
                                        fieldWithPath("serviceUrl").description("회사 운영 서비스 URL"),
                                        fieldWithPath("logoUrl").description("회사 로고 URL"),
                                        fieldWithPath("courses").description("회사 운영 코스")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("findCompany()는 해당 id의 회사가 존재하지 않는 경우 400 Bad Request 를 반환한다.")
    void findCompany_NOT_FOUND_COMPANY() throws Exception {
        //given
        given(companyService.findById(any()))
                .willThrow(new ResourceNotFoundException(NOT_FOUND_COMPANY, "2"));

        //when
        ResultActions perform = mockMvc.perform(get("/companies/{id}", 2)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(NOT_FOUND_COMPANY.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("companies/find/fail/not-found-company",
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
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("회사 전체 조회")
                                .description("회사 전체 조회")
                                .responseFields(
                                        fieldWithPath("[].id").description("회사 ID"),
                                        fieldWithPath("[].name").description("회사 이름"),
                                        fieldWithPath("[].serviceName").description("회사 운영 서비스 이름"),
                                        fieldWithPath("[].url").description("회사 URL"),
                                        fieldWithPath("[].serviceUrl").description("회사 운영 서비스 URL"),
                                        fieldWithPath("[].logoUrl").description("회사 로고 URL"),
                                        fieldWithPath("[].courses[]").description("회사 운영 코스")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("modifyCompany()는 정상 요청시 상태코드 204를 반환한다.")
    void modifyCompany() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(getCompanyRequest(1));
        willDoNothing().given(companyService).modifyCompany(any(), any());

        //when
        ResultActions perform = mockMvc.perform(put("/companies/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("companies/modify/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("회사 수정")
                                .description("회사 수정")
                                .pathParameters(
                                        parameterWithName("id").description("회사 ID")
                                )
                                .requestFields(
                                        fieldWithPath("name").description("회사 이름"),
                                        fieldWithPath("serviceName").description("회사 운영 서비스 이름"),
                                        fieldWithPath("url").description("회사 URL"),
                                        fieldWithPath("serviceUrl").description("회사 운영 서비스 URL"),
                                        fieldWithPath("logoUrl").description("회사 로고 URL")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("modifyCompany()는 해당 id의 회사가 존재하지 않는 경우 400 Bad Request 를 반환한다.")
    void modifyCompany_NOT_FOUND_COMPANY() throws Exception {
        //given
        String content = objectMapper.writeValueAsString(getCompanyRequest(2));
        willThrow(new ResourceNotFoundException(NOT_FOUND_COMPANY, "2"))
                .given(companyService).modifyCompany(any(), any());

        //when
        ResultActions perform = mockMvc.perform(put("/companies/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(NOT_FOUND_COMPANY.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("companies/modify/fail/not-found-company",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("deleteCompany()는 정상 요청시 상태코드 204를 반환한다.")
    void deleteCompany() throws Exception {
        //given
        willDoNothing().given(companyService).deleteCompany(any());

        //when
        ResultActions perform = mockMvc.perform(delete("/companies/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("companies/delete/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("회사 삭제")
                                .description("회사 삭제")
                                .pathParameters(
                                        parameterWithName("id").description("회사 ID")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("deleteCompany()는 해당 id의 회사가 존재하지 않는 경우 400 Bad Request 를 반환한다.")
    void deleteCompany_NOT_FOUND_COMPANY() throws Exception {
        //given
        willThrow(new ResourceNotFoundException(NOT_FOUND_COMPANY, "2"))
                .given(companyService).deleteCompany(any());

        //when
        ResultActions perform = mockMvc.perform(delete("/companies/{id}", 2)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpectAll(
                status().isBadRequest(),
                jsonPath("message").value(NOT_FOUND_COMPANY.getMessage())
        );

        //docs
        perform.andDo(print())
                .andDo(document("companies/delete/fail/not-found-company",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
