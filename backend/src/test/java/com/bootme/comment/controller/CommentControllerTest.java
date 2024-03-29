package com.bootme.comment.controller;

import com.bootme.util.ControllerTest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.bootme.util.fixture.CommentFixture.getCommentRequest;
import static com.bootme.util.fixture.CommentFixture.getCommentResponse;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.*;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(CommentController.class)
@DisplayName("CommentController 클래스의")
class CommentControllerTest extends ControllerTest {

    @Test
    @DisplayName("addComment()는 정상 요청시 댓글을 추가하고 상태코드 201을 반환한다.")
    void addComment() throws Exception {
        // given
        given(commentService.addComment(any(), any(), any())).willReturn(1L);
        String content = objectMapper.writeValueAsString(getCommentRequest(1));

        // when
        ResultActions perform = mockMvc.perform(post("/posts/{id}/comments", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        perform.andExpect(status().isCreated());

        // docs
        perform.andDo(print())
                .andDo(document("comments/add/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("댓글 추가")
                                .description("게시글에 댓글 추가")
                                .pathParameters(
                                        parameterWithName("id").description("댓글 추가할 게시글 ID")
                                )
                                .requestFields(
                                        fieldWithPath("parentId").description("부모 댓글 ID").optional(),
                                        fieldWithPath("content").description("댓글 내용"))
                                .build())
                ));
    }

    @Test
    @DisplayName("findComment()는 정상 요청시 상태코드 200을 반환한다.")
    void findComment() throws Exception {
        // given
        given(commentService.findComment(any(), any())).willReturn(getCommentResponse(1));

        // when
        ResultActions perform = mockMvc.perform(get("/comments/{id}", 1));

        // then
        perform.andExpect(status().isOk());

        // docs
        perform.andDo(print())
                .andDo(document("comments/find/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("댓글 조회")
                                .description("댓글 조회")
                                .pathParameters(
                                        parameterWithName("id").description("댓글 ID")
                                )
                                .responseFields(
                                        fieldWithPath("id").description("댓글 ID"),
                                        fieldWithPath("postId").description("게시글 ID"),
                                        fieldWithPath("writerId").description("작성자 ID"),
                                        fieldWithPath("writerNickname").description("작성자 닉네임"),
                                        fieldWithPath("writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("parentId").description("부모 댓글 ID").optional(),
                                        fieldWithPath("content").description("댓글 내용"),
                                        fieldWithPath("groupNum").description("groupNum"),
                                        fieldWithPath("levelNum").description("levelNum"),
                                        fieldWithPath("orderNum").description("orderNum"),
                                        fieldWithPath("likes").description("좋아요 수"),
                                        fieldWithPath("voted").description("투표 상태"),
                                        fieldWithPath("status").description("댓글 디스플레이 상태"),
                                        fieldWithPath("createdAt").description("생성 시간"),
                                        fieldWithPath("modifiedAt").description("수정 시간")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("modifyComment()는 정상 요청시 상태코드 204를 반환한다.")
    void modifyComment() throws Exception {
        // given
        willDoNothing().given(commentService).modifyComment(any(), any(), any());
        String content = objectMapper.writeValueAsString(getCommentRequest(1));

        // when
        ResultActions perform = mockMvc.perform(put("/comments/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(print())
                .andDo(document("comments/modify/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("댓글 수정")
                                .description("댓글 수정")
                                .pathParameters(
                                        parameterWithName("id").description("댓글 ID")
                                )
                                .requestFields(
                                        fieldWithPath("parentId").description("부모 댓글 ID").optional(),
                                        fieldWithPath("content").description("댓글 내용"))
                                .build())
                ));
    }

    @Test
    @DisplayName("deleteComment()는 정상 요청시 상태코드 204를 반환한다.")
    void deleteComment() throws Exception {
        // given
        willDoNothing().given(commentService).deleteComment(any(), any());

        // when
        ResultActions perform = mockMvc.perform(delete("/comments/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        // then
        perform.andExpect(status().isNoContent());

        // docs
        perform.andDo(print())
                .andDo(document("comments/delete/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("댓글 삭제")
                                .description("댓글 삭제")
                                .pathParameters(
                                        parameterWithName("id").description("댓글 ID")
                                )
                                .build())
                ));
    }

}
