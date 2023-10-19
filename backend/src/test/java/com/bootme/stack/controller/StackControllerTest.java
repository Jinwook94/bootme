package com.bootme.stack.controller;

import com.bootme.stack.dto.StackRequest;
import com.bootme.stack.dto.StackResponse;
import com.bootme.util.ControllerTest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.bootme.util.fixture.MemberFixture.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.given;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(StackController.class)
@DisplayName("StackController 클래스의")
class StackControllerTest extends ControllerTest {

    @Test
    @DisplayName("findAllStacks()는 정상 요청시 상태코드 200을 반환한다.")
    void findAllStacks() throws Exception {
        // given
        List<StackResponse> stackResponses = List.of(getStackResponse(1), getStackResponse(2),
                                                     getStackResponse(3), getStackResponse(4));
        given(stackService.findAllStacks()).willReturn(stackResponses);

        // when
        ResultActions perform = mockMvc.perform(get("/stacks")
                .accept(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("stacks/findAll/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("기술 스택 전체 조회")
                                .description("프로필 관리 페이지에서 기술 스택 설정을 위해 전체 기술 스택을 가져온다.")
                                .responseFields(
                                        fieldWithPath("[].name").description("기술 스택 이름"),
                                        fieldWithPath("[].type").description("기술 스택 유형"),
                                        fieldWithPath("[].icon").description("기술 스택 아이콘")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("addStacks()는 정상 요청시 상태코드 200을 반환한다.")
    void addStacks() throws Exception {
        //given
        List<StackRequest> stackRequests = List.of(getStackRequest(1), getStackRequest(2),
                                                   getStackRequest(3), getStackRequest(4));
        String content = objectMapper.writeValueAsString(stackRequests);

        // when
        ResultActions perform = mockMvc.perform(post("/stacks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("stacks/add/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("기술 스택 여러 개 추가")
                                .description("기술 스택 여러 개 추가")
                                .requestFields(
                                        fieldWithPath("[].name").description("기술 스택 이름"),
                                        fieldWithPath("[].type").description("기술 스택 유형"),
                                        fieldWithPath("[].icon").description("기술 스택 아이콘")
                                )
                                .build())
                ));
    }

}
