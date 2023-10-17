package com.bootme.vote.controller;

import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.bootme.util.fixture.CommentFixture.getCommentResponse;
import static com.bootme.util.fixture.VoteFixture.*; // You may need to create this fixture
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(VoteController.class)
@DisplayName("VoteController 클래스의")
class VoteControllerTest extends ControllerTest {

    @Test
    @DisplayName("vote()는 정상 요청시 상태코드 200을 반환한다.")
    void vote() throws Exception {
        //given
        given(voteService.vote(any(), any())).willReturn(getCommentResponse(1));
        String content = objectMapper.writeValueAsString(getPostUpvoteRequest());

        //when
        ResultActions perform = mockMvc.perform(post("/vote")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("votes/vote/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
