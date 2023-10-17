package com.bootme.stack.controller;

import com.bootme.stack.dto.StackRequest;
import com.bootme.stack.dto.StackResponse;
import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static com.bootme.util.fixture.MemberFixture.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
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
                        preprocessResponse(prettyPrint())));
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
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("addLanguage()는 정상 요청시 상태코드 200을 반환한다.")
    void addLanguage() throws Exception {
        // when
        ResultActions perform = mockMvc.perform(post("/stacks/language")
                .param("name", VALID_STACK_1)
                .param("icon", VALID_STACK_ICON_1));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("stacks/addLanguage/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("removeLanguage()는 정상 요청시 상태코드 200을 반환한다.")
    void removeLanguage() throws Exception {
        // when
        ResultActions perform = mockMvc.perform(delete("/stacks/language")
                .param("name", VALID_STACK_2));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("stacks/removeLanguage/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("addFramework()는 정상 요청시 상태코드 200을 반환한다.")
    void addFramework() throws Exception {
        // when
        ResultActions perform = mockMvc.perform(post("/stacks/framework")
                .param("name", VALID_STACK_3)
                .param("icon", VALID_STACK_ICON_3));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("stacks/addFramework/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("removeFramework()는 정상 요청시 상태코드 200을 반환한다.")
    void removeFramework() throws Exception {
        // when
        ResultActions perform = mockMvc.perform(delete("/stacks/framework")
                .param("name", VALID_STACK_4));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("stacks/removeFramework/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
