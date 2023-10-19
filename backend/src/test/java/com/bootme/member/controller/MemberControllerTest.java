package com.bootme.member.controller;

import com.bootme.util.ControllerTest;
import com.epages.restdocs.apispec.ResourceSnippetParameters;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import static com.bootme.util.fixture.MemberFixture.*;
import static com.epages.restdocs.apispec.MockMvcRestDocumentationWrapper.document;
import static com.epages.restdocs.apispec.ResourceDocumentation.parameterWithName;
import static com.epages.restdocs.apispec.ResourceDocumentation.resource;
import static org.mockito.BDDMockito.*;
import static org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders.*;
import static org.springframework.restdocs.operation.preprocess.Preprocessors.*;
import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(MemberController.class)
@DisplayName("MemberController 클래스의")
class MemberControllerTest extends ControllerTest {

    @Test
    @DisplayName("findMemberProfile()는 정상 요청시 회원 프로필을 반환하고 상태코드 200을 반환한다.")
    void findMemberProfile() throws Exception {
        //given
        given(memberService.findMemberProfile(any())).willReturn(getProfileResponse(1));

        //when
        ResultActions perform = mockMvc.perform(get("/member/{memberId}/profile", 1)
                .accept(MediaType.APPLICATION_JSON));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("members/find-profile/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("회원 프로필 조회")
                                .description("커뮤니티 페이지에서 회원 프로필 사진을 클릭했을 때 해당 회원의 프로필 정보를 가져온다.")
                                .pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                )
                                .responseFields(
                                        fieldWithPath("profileImage").description("프로필 이미지"),
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("job").description("직업"),
                                        fieldWithPath("stacks[].name").description("기술 스택 이름"),
                                        fieldWithPath("stacks[].type").description("기술 스택 유형"),
                                        fieldWithPath("stacks[].icon").description("기술 스택 아이콘")
                                )
                                .build())
                ));
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
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("내 프로필 조회")
                                .description("프로필 관리 페이지 진입시 자신의 프로필 정보를 불러온다.")
                                .responseFields(
                                        fieldWithPath("profileImage").description("프로필 이미지"),
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("job").description("직업"),
                                        fieldWithPath("stacks[]").description("기술 스택")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("modifyProfile()는 정상 요청시 회원의 프로필을 수정하고 상태코드 200을 반환한다.")
    void modifyProfile() throws Exception {
        //given
        willDoNothing().given(memberService).modifyProfile(any(), any(), any());
        String content = objectMapper.writeValueAsString(getUpdateProfileRequest(1));

        //when
        ResultActions perform = mockMvc.perform(put("/member/{memberId}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("members/modify-profile/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("프로필 수정")
                                .description("프로필 관리 페이지에서 정보 수정 후 저장하기 버튼 클릭시 해당 회원의 정보를 수정한다.")
                                .pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                )
                                .requestFields(
                                        fieldWithPath("email").description("이메일"),
                                        fieldWithPath("nickname").description("닉네임"),
                                        fieldWithPath("job").description("직업"),
                                        fieldWithPath("stackNames[]").description("기술 스택")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("modifyProfileImage()는 정상 요청시 회원의 프로필 이미지를 수정하고 상태코드 200을 반환한다.")
    void modifyProfileImage() throws Exception {
        //given
        willDoNothing().given(memberService).modifyProfileImage(any(), any(), any());
        String content = objectMapper.writeValueAsString(getUpdateImageRequest(1));

        //when
        ResultActions perform = mockMvc.perform(patch("/member/{memberId}/profile_image", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(content));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("members/modify-profile-image/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("프로필 이미지 수정")
                                .description("프로필 관리 페이지에서 프로필 이미지 변경시 호출된다.")
                                .pathParameters(
                                        parameterWithName("memberId").description("회원 ID")
                                )
                                .requestFields(
                                        fieldWithPath("profileImage").description("변경할 이미지 URL")
                                )
                                .build())
                ));
    }

    @Test
    @DisplayName("checkNicknameDuplicate()는 중복되지 않은 닉네임 요청시 상태코드 200을 반환한다.")
    void checkNicknameDuplicate() throws Exception {
        //given
        given(memberService.isNicknameDuplicate(anyString())).willReturn(false);

        //when
        ResultActions perform = mockMvc.perform(
                get("/member/nickname/{nickname}/exists", "철수"));

        //then
        perform.andExpect(status().isOk());

        //docs
        perform.andDo(print())
                .andDo(document("members/check-nickname/success",
                        preprocessRequest(prettyPrint()),
                        preprocessResponse(prettyPrint()),
                        resource(ResourceSnippetParameters.builder()
                                .summary("닉네임 중복 검사")
                                .description("프로필 관리 페이지에서 닉네임 중복 여부를 검사한다.")
                                .pathParameters(
                                        parameterWithName("nickname").description("닉네임")
                                )
                                .build())
                ));
    }

}