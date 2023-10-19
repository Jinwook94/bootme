package com.bootme.post.controller;

import com.bootme.comment.dto.CommentResponse;
import com.bootme.common.util.CustomPageImpl;
import com.bootme.post.dto.PostResponse;
import com.bootme.util.ControllerTest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static com.bootme.util.fixture.CommentFixture.getCommentResponse;
import static com.bootme.util.fixture.PostFixture.*;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostController.class)
@DisplayName("PostController 클래스의")
class PostControllerTest extends ControllerTest {

    @Test
    @DisplayName("addPost()는 정상 요청시 상태코드 201을 반환한다.")
    void addPost() throws Exception {
        //given
        given(postService.addPost(any(), any())).willReturn(1L);
        given(postService.findPost(anyLong(), anyLong())).willReturn(getPostDetailResponse(1));
        String content = objectMapper.writeValueAsString(getPostRequest(1));

        //when
        ResultActions perform = mockMvc.perform(post("/posts")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isCreated());

        //docs
        perform.andDo(print())
                .andDo(document("posts/add/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("게시글 작성")
                                .description("커뮤니티 페이지에 게시글을 작성한다.")
                                .requestFields(
                                        fieldWithPath("topic").description("게시글 토픽"),
                                        fieldWithPath("title").description("게시글 제목"),
                                        fieldWithPath("content").description("게시글 내용")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("findPost()는 정상 요청시 상태코드 200을 반환한다.")
    void findPost() throws Exception {
        //given
        given(postService.findPost(any(), any())).willReturn(getPostDetailResponse(1));

        //when
        ResultActions perform = mockMvc.perform(get("/posts/{id}", 1)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("posts/find/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("게시글 조회")
                                .description("게시글 상세 페이지에서 호출되어 해당 게시글 정보를 가져온다.")
                                .pathParameters(
                                        parameterWithName("id").description("게시글 ID")
                                )
                                .responseFields(
                                        fieldWithPath("id").description("게시글 ID"),
                                        fieldWithPath("writerId").description("작성자 ID"),
                                        fieldWithPath("writerNickname").description("작성자 닉네임"),
                                        fieldWithPath("writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("topic").description("게시글 토픽"),
                                        fieldWithPath("title").description("게시글 제목"),
                                        fieldWithPath("content").description("게시글 내용"),
                                        fieldWithPath("likes").description("게시글 vote"),
                                        fieldWithPath("clicks").description("게시글 클릭 수"),
                                        fieldWithPath("bookmarks").description("게시글 북마크 수"),
                                        fieldWithPath("status").description("게시글 디스플레이 상태"),
                                        fieldWithPath("createdAt").description("게시글 생성 시간"),
                                        fieldWithPath("modifiedAt").description("게시글 수정 시간"),
                                        fieldWithPath("commentCount").description("게시글의 댓글 수"),
                                        fieldWithPath("voted").description("로그인 회원의 해당 게시물에 대한 vote 상태"),
                                        fieldWithPath("bookmarked").description("로그인 회원의 해당 게시글 북마크 여부")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("findAllPosts()는 정상 요청시 상태코드 200을 반환한다.")
    void findAllPosts() throws Exception {
        //given
        List<PostResponse> postResponses = Arrays.asList(
                getPostResponse(1),
                getPostResponse(2),
                getPostResponse(3)
        );
        Page<PostResponse> postPage = new PageImpl<>(postResponses);
        CustomPageImpl<PostResponse> customPage = new CustomPageImpl<>(postPage);

        given(postService.findAllPosts(any(), anyInt(), anyInt(), any(), any())).willReturn(customPage);

        //when
        ResultActions perform = mockMvc.perform(get("/posts")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("posts/findAll/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("게시글 전체 조회")
                                .description("커뮤니티 페이지에서 디스플레이할 게시글 전체 정보를 가져온다.")
                                .queryParameters(
                                        parameterWithName("page").description("페이지").optional(),
                                        parameterWithName("size").description("한 번에 가져올 게시글 수").optional(),
                                        parameterWithName("sort").description("정렬").optional(),
                                        parameterWithName("topic").description("게시글 토픽").optional(),
                                        parameterWithName("search").description("검색 쿼리").optional()
                                )
                                .responseFields(
                                        fieldWithPath("content[].id").description("게시글 ID"),
                                        fieldWithPath("content[].writerId").description("작성자 ID"),
                                        fieldWithPath("content[].writerNickname").description("작성자 닉네임"),
                                        fieldWithPath("content[].writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("content[].topic").description("게시글 토픽"),
                                        fieldWithPath("content[].title").description("게시글 제목"),
                                        fieldWithPath("content[].contentExcerpt").description("게시글 내용"),
                                        fieldWithPath("content[].likes").description("게시글 vote"),
                                        fieldWithPath("content[].clicks").description("게시글 클릭 수"),
                                        fieldWithPath("content[].bookmarks").description("게시글 북마크 수"),
                                        fieldWithPath("content[].status").description("게시글 디스플레이 상태"),
                                        fieldWithPath("content[].createdAt").description("게시글 생성 시간"),
                                        fieldWithPath("content[].modifiedAt").description("게시글 수정 시간"),
                                        fieldWithPath("content[].commentCount").description("게시글의 댓글 수"),
                                        fieldWithPath("content[].voted").description("로그인 회원의 해당 게시물에 대한 vote 상태"),
                                        fieldWithPath("content[].bookmarked").description("로그인 회원의 해당 게시글 북마크 여부"),
                                        fieldWithPath("content[].viewed").description("로그인 회원의 해당 게시글 조회 여부"),
                                        fieldWithPath("last").description("마지막 페이지"),
                                        fieldWithPath("totalPages").description("총 페이지 수"),
                                        fieldWithPath("totalElements").description("총 아이템 수"),
                                        fieldWithPath("size").description("크기"),
                                        fieldWithPath("number").description("번호"),
                                        fieldWithPath("sort.empty").description("정렬 비어 있음"),
                                        fieldWithPath("sort.sorted").description("정렬 됨"),
                                        fieldWithPath("sort.unsorted").description("미정렬"),
                                        fieldWithPath("first").description("첫 페이지"),
                                        fieldWithPath("numberOfElements").description("아이템 수"),
                                        fieldWithPath("empty").description("비어 있음")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("findCommentsByPostId()는 정상 요청시 상태코드 200을 반환한다.")
    void findCommentsByPostId() throws Exception {
        //given
        List<CommentResponse> commentResponses = Arrays.asList(
                getCommentResponse(1),
                getCommentResponse(2),
                getCommentResponse(3)
        );
        given(postService.findCommentsByPostId(any(), any())).willReturn(commentResponses);

        //when
        ResultActions perform = mockMvc.perform(get("/posts/{id}/comments", 1)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("posts/findCommentsByPostId/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("게시글 댓글 조회")
                                .description("게시글 상세 페이지에서 호출되어 해당 게시글의 댓글들을 가져온다.")
                                .pathParameters(
                                        parameterWithName("id").description("게시글 ID")
                                )
                                .responseFields(
                                        fieldWithPath("[].id").description("댓글 ID"),
                                        fieldWithPath("[].postId").description("게시글 ID"),
                                        fieldWithPath("[].writerId").description("작성자 ID"),
                                        fieldWithPath("[].writerNickname").description("작성자 닉네임"),
                                        fieldWithPath("[].writerProfileImage").description("작성자 프로필 이미지"),
                                        fieldWithPath("[].parentId").description("부모 댓글 ID"),
                                        fieldWithPath("[].content").description("댓글 내용"),
                                        fieldWithPath("[].groupNum").description("댓글 그룹 번호"),
                                        fieldWithPath("[].levelNum").description("댓글 레벨 번호"),
                                        fieldWithPath("[].orderNum").description("댓글 순서 번호"),
                                        fieldWithPath("[].likes").description("댓글 좋아요 수"),
                                        fieldWithPath("[].voted").description("로그인 회원의 해당 댓글에 대한 vote 상태"),
                                        fieldWithPath("[].status").description("댓글 디스플레이 상태"),
                                        fieldWithPath("[].createdAt").description("댓글 생성 시간"),
                                        fieldWithPath("[].modifiedAt").description("댓글 수정 시간")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("modifyPost()는 정상 요청시 상태코드 204를 반환한다.")
    void modifyPost() throws Exception{
        //given
        willDoNothing().given(postService).modifyPost(any(), any(), any());
        String content = objectMapper.writeValueAsString(getPostRequest(1));

        //when
        ResultActions perform = mockMvc.perform(put("/posts/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("posts/modify/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("게시글 수정")
                                .description("게시글 수정")
                                .pathParameters(
                                        parameterWithName("id").description("게시글 ID")
                                )
                                .requestFields(
                                        fieldWithPath("topic").description("게시글 토픽"),
                                        fieldWithPath("title").description("게시글 제목"),
                                        fieldWithPath("content").description("게시글 내용")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("deletePost()는 정상 요청시 상태코드 204를 반환한다.")
    void deletePost() throws Exception {
        //given
        willDoNothing().given(postService).deletePost(any(), any());

        //when
        ResultActions perform = mockMvc.perform(delete("/posts/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("posts/delete/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("게시글 삭제")
                                .description("해당 게시글의 status 칼럼 값을 DELETED로 변경한다.")
                                .pathParameters(
                                        parameterWithName("id").description("게시글 ID")
                                )
                                .build())
                ));
    }

}
