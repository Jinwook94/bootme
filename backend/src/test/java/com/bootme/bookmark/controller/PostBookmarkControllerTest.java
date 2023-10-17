package com.bootme.bookmark.controller;

import com.bootme.util.ControllerTest;
import com.bootme.post.dto.PostResponse;
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
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(PostBookmarkController.class)
@DisplayName("PostBookmarkController 클래스의")
class PostBookmarkControllerTest extends ControllerTest {

    @Test
    @DisplayName("addPostBookmark()는 정상 요청시 북마크를 추가하고 상태코드 201을 반환한다.")
    void addPostBookmark() throws Exception {
        //given
        given(postBookmarkService.addPostBookmark(anyLong(), anyLong())).willReturn(1L);

        //when
        ResultActions perform = mockMvc.perform(post("/bookmarks/{memberId}/posts/{postId}", 1, 1)
                .contentType(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isCreated());

        //docs
        perform.andDo(print())
                .andDo(document("postBookmarks/add-post/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
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
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("getBookmarkedPosts()는 정상 요청시 상태코드 200을 반환한다.")
    void getBookmarkedPosts() throws Exception {
        //given
        List<PostResponse> postResponses = new ArrayList<>();
        postResponses.add(getPostResponse(1));
        postResponses.add(getPostResponse(2));
        Page<PostResponse> postPage = new PageImpl<>(postResponses);

        given(postBookmarkService.getBookmarkedPosts(anyLong(), any())).willReturn(postPage);

        //when
        ResultActions perform = mockMvc.perform(get("/bookmarks/{memberId}/posts", 1)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("postBookmarks/find-all-posts/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}
