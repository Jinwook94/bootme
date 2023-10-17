package com.bootme.member.controller;

import com.bootme.util.ControllerTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.bootme.util.fixture.MemberFixture.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.MockMvcRestDocumentation.document;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@DisplayName("MemberController 클래스의")
class MemberControllerTest extends ControllerTest {

    @Test
    @DisplayName("findMemberProfile()는 정상 요청시 회원 프로필을 반환하고 상태코드 200을 반환한다.")
    void findMemberProfile() throws Exception {
        //given
        given(memberService.findMemberProfile(anyLong())).willReturn(getProfileResponse(1));

        //when
        ResultActions perform = mockMvc.perform(get("/member/1/profile")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("members/find-profile/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("findMyProfile()는 정상 요청시 회원 자신의 프로필을 반환하고 상태코드 200을 반환한다.")
    void findMyProfile() throws Exception {
        //given
        given(memberService.findMyProfile(any())).willReturn(getMyProfileResponse(1));

        //when
        ResultActions perform = mockMvc.perform(get("/member/me")
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("members/find-my-profile/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("modifyProfile()는 정상 요청시 회원의 프로필을 수정하고 상태코드 200을 반환한다.")
    void modifyProfile() throws Exception {
        //given
        willDoNothing().given(memberService).modifyProfile(any(), any(), any());
        String content = objectMapper.writeValueAsString(getUpdateProfileRequest(1));

        //when
        ResultActions perform = mockMvc.perform(put("/member/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("members/modify-profile/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("modifyProfileImage()는 정상 요청시 회원의 프로필 이미지를 수정하고 상태코드 200을 반환한다.")
    void modifyProfileImage() throws Exception {
        //given
        willDoNothing().given(memberService).modifyProfileImage(any(), any(), any());
        String content = objectMapper.writeValueAsString(getUpdateImageRequest(1));

        //when
        ResultActions perform = mockMvc.perform(patch("/member/1/profile_image")
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("members/modify-profile-image/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

    @Test
    @DisplayName("checkNicknameDuplicate()는 중복되지 않은 닉네임 요청시 상태코드 200을 반환한다.")
    void checkNicknameDuplicate() throws Exception {
        //given
        given(memberService.isNicknameDuplicate(anyString())).willReturn(false);

        //when
        ResultActions perform = mockMvc.perform(get("/member/nickname/user_nickname/exists"));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("members/check-nickname/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint())));
    }

}