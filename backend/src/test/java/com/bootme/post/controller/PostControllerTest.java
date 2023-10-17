package com.bootme.post.controller;

import com.bootme.common.util.CustomPageImpl;
import com.bootme.post.dto.PostResponse;
import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;
import java.util.List;

import static com.bootme.util.fixture.PostFixture.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.delete;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.prettyPrint;
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
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("findPost()는 정상 요청시 상태코드 200을 반환한다.")
    void findPost() throws Exception {
        //given
        given(postService.findPost(anyLong(), anyLong())).willReturn(getPostDetailResponse(1));

        //when
        ResultActions perform = mockMvc.perform(get("/posts/{id}", 1)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("posts/find/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
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

        given(postService.findAllPosts(anyLong(), anyInt(), anyInt(), anyString(), any())).willReturn(customPage);

        //when
        ResultActions perform = mockMvc.perform(get("/posts")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("posts/findAll/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
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
                        preprocessResponse(prettyPrint())));
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
                        preprocessResponse(prettyPrint())));
    }

}
