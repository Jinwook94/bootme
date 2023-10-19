package com.bootme.bookmark.controller;

import com.bootme.util.ControllerTest;
import com.bootme.post.dto.PostResponse;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static com.bootme.util.fixture.PostFixture.getPostResponse;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostBookmarkController.class)
@DisplayName("PostBookmarkController 클래스의")
class PostBookmarkControllerTest extends ControllerTest {

    @Test
    @DisplayName("addPostBookmark()는 정상 요청시 북마크를 추가하고 상태코드 201을 반환한다.")
    void addPostBookmark() throws Exception {
        //given
        given(postBookmarkService.addPostBookmark(any(), any())).willReturn(1L);

        //when
        ResultActions perform = mockMvc.perform(post("/bookmarks/{memberId}/posts/{postId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isCreated());

        //docs
        perform.andDo(print())
                .andDo(document("postBookmarks/add-post/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("북마크 게시글 추가")
                                .description("북마크 게시글 추가")
                                .pathParameters(
                                        parameterWithName("memberId").description("memberId"),
                                        parameterWithName("postId").description("북마크 추가할 게시글 ID")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("deletePostBookmark()는 정상 요청시 북마크를 삭제하고 상태코드 204를 반환한다.")
    void deletePostBookmark() throws Exception {
        //when
        ResultActions perform = mockMvc.perform(delete("/bookmarks/{memberId}/posts/{postId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isNoContent());

        //docs
        perform.andDo(print())
                .andDo(document("postBookmarks/delete-post/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("북마크 게시글 삭제")
                                .description("북마크 게시글 삭제")
                                .pathParameters(
                                        parameterWithName("memberId").description("memberId"),
                                        parameterWithName("postId").description("북마크 삭제할 게시글 ID")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("getBookmarkedPosts()는 정상 요청시 상태코드 200을 반환한다.")
    void getBookmarkedPosts() throws Exception {
        //given
        List<PostResponse> postResponses = new ArrayList<>();
        postResponses.add(getPostResponse(1));
        postResponses.add(getPostResponse(2));
        Page<PostResponse> postPage = new PageImpl<>(postResponses);

        given(postBookmarkService.getBookmarkedPosts(any(), any())).willReturn(postPage);

        //when
        ResultActions perform = mockMvc.perform(get("/bookmarks/{memberId}/posts", 1)
                .param("page", "1")
                .param("size", "10")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("postBookmarks/find-all-posts/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("북마크한 코스 전체 조회")
                                .description("해당 회원이 북마크한 코스 전체를 반환한다.")
                                .pathParameters(
                                        parameterWithName("memberId").description("memberId")
                                )
                                .queryParameters(
                                        parameterWithName("page").description("요청할 페이지 번호 (1부터 시작)"),
                                        parameterWithName("size").description("한 페이지에 표시할 항목 수")
                                )
                                .responseFields(
                                        fieldWithPath("content[].id").description("게시글 ID"),
                                        fieldWithPath("content[].writerId").description("작성자 ID"),
                                        fieldWithPath("content[].writerNickname").description("작성자 닉네임"),
                                        fieldWithPath("content[].writerProfileImage").description("작성자 프로필 이미지 URL"),
                                        fieldWithPath("content[].topic").description("주제"),
                                        fieldWithPath("content[].title").description("제목"),
                                        fieldWithPath("content[].contentExcerpt").description("내용 일부"),
                                        fieldWithPath("content[].likes").description("좋아요 수"),
                                        fieldWithPath("content[].clicks").description("클릭 수"),
                                        fieldWithPath("content[].bookmarks").description("북마크 수"),
                                        fieldWithPath("content[].status").description("게시글 디스플레이 상태"),
                                        fieldWithPath("content[].createdAt").description("생성 시간"),
                                        fieldWithPath("content[].modifiedAt").description("수정 시간"),
                                        fieldWithPath("content[].commentCount").description("댓글 수"),
                                        fieldWithPath("content[].voted").description("투표 상태"),
                                        fieldWithPath("content[].bookmarked").description("북마크 여부"),
                                        fieldWithPath("content[].viewed").description("조회 여부"),
                                        fieldWithPath("pageable").description("pageable"),
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
                                .build()
                        )
                ));
    }

}
